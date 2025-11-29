package com.oleksii.surovtsev.portfolio.view.projects

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import com.oleksii.surovtsev.portfolio.config.GitCredentials
import com.oleksii.surovtsev.portfolio.view.projects.domain.GitHubRepoInfo
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * Service for interacting with the GitHub GraphQL API.
 *
 * This service provides a method to fetch GitHub repository information using caching.
 */
object GitHubGraphQLService {

    private val logger = LoggerFactory.getLogger(GitHubGraphQLService::class.java)
    private val client = OkHttpClient()
    private val mapper = jacksonObjectMapper()
    private const val GRAPHQL_URL = "https://api.github.com/graphql"

    // Repository name validation pattern
    private val REPO_NAME_PATTERN = Regex("^[a-zA-Z0-9._-]+$")

    private val cache: Cache<String, GitHubRepoInfo> = Caffeine.newBuilder()
        .expireAfterWrite(30, TimeUnit.MINUTES)
        .maximumSize(50)
        .recordStats()
        .build()

    /**
     * Retrieves information about a GitHub repository.
     *
     * Checks the cache for the requested repository data. If the data is not
     * present in the cache, it sends a request to the GitHub GraphQL API using the provided
     * properties and repository name.
     *
     * @param properties Contains authentication details, including the GitHub owner and token.
     * @param repo The name of the repository.
     * @return A [GitHubRepoInfo] object containing the repository details.
     * @throws IOException if an HTTP error occurs or if the repository is not found in the response.
     * @throws IllegalArgumentException if repository name or owner contains invalid characters.
     */
    fun getRepoInfo(properties: GitCredentials, repo: String): GitHubRepoInfo =
        cache.get(repo) { _ ->
            // Validate inputs to prevent GraphQL injection
            require(repo.matches(REPO_NAME_PATTERN)) {
                "Invalid repository name: $repo. Only alphanumeric characters, dots, hyphens, and underscores are allowed."
            }
            require(properties.githubOwner.matches(REPO_NAME_PATTERN)) {
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

            val queryJson = mapper.writeValueAsString(
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

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    logger.error("GitHub API returned error for {}/{}: {}", properties.githubOwner, repo, response.code)
                    throw IOException("HTTP error: ${response.code}")
                }
                val responseBody = response.body?.string() ?: throw IOException("Empty response from server")

                val repositoryNode = mapper.readTree(responseBody)
                    .path("data")
                    .path("repository")

                if (repositoryNode.isMissingNode) {
                    logger.warn("Repository not found: {}/{}", properties.githubOwner, repo)
                    throw IOException("Repository not found in reply")
                }

                logger.info("Successfully fetched repository info for: {}/{}", properties.githubOwner, repo)
                mapper.treeToValue(repositoryNode, GitHubRepoInfo::class.java)
            }
        }

    /**
     * Returns cache statistics for monitoring and debugging purposes.
     */
    fun getCacheStats() = cache.stats()
}
