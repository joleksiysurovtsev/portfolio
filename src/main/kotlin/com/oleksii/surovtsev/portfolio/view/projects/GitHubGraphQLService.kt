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
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * Service for interacting with the GitHub GraphQL API.
 *
 * This service provides a method to fetch GitHub repository information using caching.
 */
object GitHubGraphQLService {

    private val client = OkHttpClient()
    private val mapper = jacksonObjectMapper()
    private const val GRAPHQL_URL = "https://api.github.com/graphql"
    private var cache: Cache<String, GitHubRepoInfo> = Caffeine.newBuilder()
        .expireAfterWrite(120, TimeUnit.MINUTES)
        .maximumSize(100)
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
     */
    fun getRepoInfo(properties: GitCredentials, repo: String): GitHubRepoInfo =
        cache.get(repo) { _ ->

            val queryJson = """
                {
                  "query": "query { repository(owner: \"${properties.githubOwner}\", name: \"$repo\") { id name description url openGraphImageUrl stargazerCount repositoryTopics(first: 10) { nodes { topic { name } } } } }"
                }
            """.trimIndent()

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = queryJson.toRequestBody(mediaType)


            val request = Request.Builder()
                .url(GRAPHQL_URL)
                .header("Authorization", "Bearer ${properties.githubToken}")
                .post(requestBody)
                .build()


            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("HTTP error: ${response.code}")
                }
                val responseBody = response.body?.string() ?: throw IOException("Empty response from server")

                val repositoryNode = mapper.readTree(responseBody)
                    .path("data")
                    .path("repository")

                if (repositoryNode.isMissingNode) {
                    throw IOException("Repository not found in reply")
                }

                mapper.treeToValue(repositoryNode, GitHubRepoInfo::class.java)
            }
        }
}
