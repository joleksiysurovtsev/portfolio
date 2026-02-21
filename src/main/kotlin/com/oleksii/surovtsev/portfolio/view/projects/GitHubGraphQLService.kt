package com.oleksii.surovtsev.portfolio.view.projects

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import com.oleksii.surovtsev.portfolio.config.GitCredentials
import com.oleksii.surovtsev.portfolio.exception.*
import com.oleksii.surovtsev.portfolio.view.projects.domain.GitHubRepoInfo
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit


/**
 * Service for interacting with the GitHub GraphQL API.
 *
 * This service provides a method to fetch GitHub repository information using caching
 * with retry logic and circuit breaker pattern for resilience.
 */
@Service
class GitHubGraphQLService(
    @Autowired private val objectMapper: ObjectMapper,
    @Autowired @Qualifier("githubHttpClient") private val client: OkHttpClient
) {

    private val logger = LoggerFactory.getLogger(GitHubGraphQLService::class.java)

    companion object {
        private const val GRAPHQL_URL = "https://api.github.com/graphql"
    }

    // Repository name validation pattern
    private val repoNamePattern = Regex("^[a-zA-Z0-9._-]+$")

    private val cache: Cache<String, GitHubRepoInfo> = Caffeine.newBuilder()
        .expireAfterWrite(30, TimeUnit.MINUTES)
        .maximumSize(50)
        .recordStats()
        .build()

    /**
     * Returns the cache instance for monitoring and statistics.
     * Used by monitoring endpoints to expose cache metrics.
     */
    fun getCache(): Cache<String, GitHubRepoInfo> = cache

    /**
     * Retrieves information about a GitHub repository.
     *
     * Checks the cache for the requested repository data. If the data is not
     * present in the cache, it sends a request to the GitHub GraphQL API using the provided
     * properties and repository name.
     *
     * This method includes retry logic and circuit breaker pattern for resilience
     * against transient network failures.
     *
     * @param properties Contains authentication details, including the GitHub owner and token.
     * @param repo The name of the repository.
     * @return A [GitHubRepoInfo] object containing the repository details.
     * @throws GitHubException for GitHub API-specific errors
     * @throws IllegalArgumentException if repository name or owner contains invalid characters.
     */
    @Retry(name = "github-api", fallbackMethod = "getRepoInfoFallback")
    @CircuitBreaker(name = "github-api", fallbackMethod = "getRepoInfoFallback")
    fun getRepoInfo(properties: GitCredentials, repo: String): GitHubRepoInfo =
        cache.get(repo) { _ ->
            // Validate inputs to prevent GraphQL injection
            require(repo.matches(repoNamePattern)) {
                "Invalid repository name: $repo. Only alphanumeric characters, dots, hyphens, and underscores are allowed."
            }
            require(properties.githubOwner.matches(repoNamePattern)) {
                "Invalid GitHub owner: ${properties.githubOwner}. Only alphanumeric characters, dots, hyphens, and underscores are allowed."
            }

            val query = """
                query GetRepository(${'$'}owner: String!, ${'$'}name: String!) {
                  repository(owner: ${'$'}owner, name: ${'$'}name) {
                    id
                    name
                    description
                    url
                    openGraphImageUrl
                    stargazerCount
                    repositoryTopics(first: 10) {
                      nodes {
                        topic {
                          name
                        }
                      }
                    }
                  }
                }
            """.trimIndent()

            val queryJson = objectMapper.writeValueAsString(
                mapOf(
                    "query" to query,
                    "variables" to mapOf(
                        "owner" to properties.githubOwner,
                        "name" to repo
                    )
                )
            )

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = queryJson.toRequestBody(mediaType)


            val request = Request.Builder()
                .url(GRAPHQL_URL)
                .header("Authorization", "Bearer ${properties.githubToken}")
                .post(requestBody)
                .build()


            logger.debug("Fetching repository info for: {}/{}", properties.githubOwner, repo)

            try {
                client.newCall(request).execute().use { response ->
                    val responseBody = response.body?.string()

                    when {
                        response.code == 401 -> {
                            logger.error("GitHub authentication failed")
                            throw GitHubAuthenticationException("GitHub authentication failed. Check your token.")
                        }
                        response.code == 403 -> {
                            val remaining = response.header("X-RateLimit-Remaining")?.toIntOrNull() ?: 0
                            if (remaining == 0) {
                                val resetAt = response.header("X-RateLimit-Reset")?.toLongOrNull()
                                logger.warn("GitHub rate limit exceeded. Reset at: {}", resetAt)
                                throw GitHubRateLimitException(remaining, resetAt)
                            } else {
                                logger.error("GitHub API access forbidden")
                                throw GitHubAuthenticationException("GitHub API access forbidden")
                            }
                        }
                        response.code == 404 -> {
                            logger.warn("Repository not found: {}/{}", properties.githubOwner, repo)
                            throw GitHubApiException(404, responseBody, "Repository not found: $repo")
                        }
                        !response.isSuccessful -> {
                            logger.error("GitHub API returned error for {}/{}: {}", properties.githubOwner, repo, response.code)
                            throw GitHubApiException(response.code, responseBody)
                        }
                    }

                    if (responseBody == null) {
                        throw GitHubApiException(message = "Empty response from GitHub API")
                    }

                    val jsonNode = objectMapper.readTree(responseBody)

                    // Check for GraphQL errors
                    val errors = jsonNode.path("errors")
                    if (!errors.isMissingNode && errors.isArray && errors.size() > 0) {
                        val errorMessage = errors.map { it.path("message").asText() }.joinToString("; ")
                        logger.error("GraphQL errors: {}", errorMessage)
                        throw GitHubApiException(message = "GraphQL error: $errorMessage")
                    }

                    val repositoryNode = jsonNode
                        .path("data")
                        .path("repository")

                    if (repositoryNode.isMissingNode || repositoryNode.isNull) {
                        logger.warn("Repository not found in response: {}/{}", properties.githubOwner, repo)
                        throw GitHubApiException(404, responseBody, "Repository not found: $repo")
                    }

                    logger.info("Successfully fetched repository info for: {}/{}", properties.githubOwner, repo)
                    objectMapper.treeToValue(repositoryNode, GitHubRepoInfo::class.java)
                }
            } catch (e: SocketTimeoutException) {
                logger.error("Timeout while fetching GitHub repository: {}/{}", properties.githubOwner, repo, e)
                throw GitHubNetworkException("Request timeout while fetching repository: $repo", e)
            } catch (e: UnknownHostException) {
                logger.error("Cannot resolve GitHub host", e)
                throw GitHubNetworkException("Cannot connect to GitHub API", e)
            } catch (e: IOException) {
                logger.error("Network error while fetching repository: {}/{}", properties.githubOwner, repo, e)
                throw GitHubNetworkException("Network error while fetching repository: $repo", e)
            } catch (e: GitHubException) {
                // Re-throw our custom exceptions
                throw e
            } catch (e: Exception) {
                logger.error("Unexpected error fetching repository: {}/{}", properties.githubOwner, repo, e)
                throw GitHubApiException(message = "Unexpected error: ${e.message}")
            }
        }

    /**
     * Fallback method for getRepoInfo when retry attempts are exhausted or circuit breaker is open.
     * Returns cached data if available or a default response with error information.
     */
    fun getRepoInfoFallback(properties: GitCredentials, repo: String, exception: Exception): GitHubRepoInfo {
        logger.warn("Falling back for repository {}/{} due to: {}", properties.githubOwner, repo, exception.message)

        // Try to return cached data if available
        cache.getIfPresent(repo)?.let { cachedInfo ->
            logger.info("Returning cached data for {}/{}", properties.githubOwner, repo)
            return cachedInfo
        }

        // Return a default response with error information
        return GitHubRepoInfo(
            id = "unavailable",
            name = repo,
            description = "Repository information temporarily unavailable. Please try again later.",
            url = "https://github.com/${properties.githubOwner}/$repo",
            openGraphImageUrl = null,
            stargazerCount = 0,
            repositoryTopics = null
        )
    }

}
