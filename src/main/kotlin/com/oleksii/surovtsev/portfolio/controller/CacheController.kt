package com.oleksii.surovtsev.portfolio.controller

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.stats.CacheStats
import com.oleksii.surovtsev.portfolio.view.projects.GitHubGraphQLService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * REST controller for exposing cache statistics and metrics.
 */
@RestController
@RequestMapping("/api/cache")
class CacheController(
    private val gitHubService: GitHubGraphQLService
) {

    /**
     * Returns cache statistics for GitHub API cache.
     */
    @GetMapping("/github/stats")
    fun getGitHubCacheStats(): CacheStatisticsResponse {
        val cache = gitHubService.getCache()
        val stats = cache.stats()

        return CacheStatisticsResponse(
            name = "GitHub API Cache",
            size = cache.estimatedSize(),
            stats = if (stats.requestCount() > 0) CacheStatisticsData(
                hitCount = stats.hitCount(),
                missCount = stats.missCount(),
                loadSuccessCount = stats.loadSuccessCount(),
                loadFailureCount = stats.loadFailureCount(),
                totalLoadTime = stats.totalLoadTime(),
                evictionCount = stats.evictionCount(),
                hitRate = stats.hitRate(),
                missRate = stats.missRate(),
                requestCount = stats.requestCount(),
                averageLoadPenalty = stats.averageLoadPenalty()
            ) else null,
            entries = cache.asMap().entries.map { (key, value) ->
                CacheEntry(
                    key = key.toString(),
                    value = value.toString().take(200), // Limit value size for display
                    size = value.toString().length
                )
            },
            configuration = CacheConfiguration(
                maximumSize = 100, // From GitHubGraphQLService configuration
                expireAfterWriteMinutes = 60,
                recordStats = true
            )
        )
    }

    /**
     * Clears the GitHub API cache.
     */
    @GetMapping("/github/clear")
    fun clearGitHubCache(): Map<String, Any> {
        val cache = gitHubService.getCache()
        val sizeBefore = cache.estimatedSize()
        cache.invalidateAll()

        return mapOf(
            "status" to "success",
            "message" to "GitHub API cache cleared",
            "entriesCleared" to sizeBefore,
            "timestamp" to LocalDateTime.now()
        )
    }

    /**
     * Returns all cache statistics for the application.
     */
    @GetMapping("/all")
    fun getAllCacheStats(): Map<String, CacheStatisticsResponse> {
        return mapOf(
            "github" to getGitHubCacheStats()
            // Add other caches here as needed
        )
    }
}

/**
 * Response model for cache statistics.
 */
data class CacheStatisticsResponse(
    val name: String,
    val size: Long,
    val stats: CacheStatisticsData?,
    val entries: List<CacheEntry>,
    val configuration: CacheConfiguration,
    val timestamp: LocalDateTime = LocalDateTime.now()
)

/**
 * Detailed cache statistics data.
 */
data class CacheStatisticsData(
    val hitCount: Long,
    val missCount: Long,
    val loadSuccessCount: Long,
    val loadFailureCount: Long,
    val totalLoadTime: Long,
    val evictionCount: Long,
    val hitRate: Double,
    val missRate: Double,
    val requestCount: Long,
    val averageLoadPenalty: Double
)

/**
 * Individual cache entry representation.
 */
data class CacheEntry(
    val key: String,
    val value: String,
    val size: Int
)

/**
 * Cache configuration details.
 */
data class CacheConfiguration(
    val maximumSize: Long,
    val expireAfterWriteMinutes: Long,
    val recordStats: Boolean
)