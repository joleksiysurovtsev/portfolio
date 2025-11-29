package com.oleksii.surovtsev.portfolio.util

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

/**
 * Loader for SVG icon resources with built-in caching.
 *
 * SVG files are cached in memory after first load to improve performance
 * for frequently accessed icons (e.g., theme toggle, menu icons).
 */
@Component
class SvgResourceLoader : ResourceLoader {

    private val logger = LoggerFactory.getLogger(SvgResourceLoader::class.java)

    // Thread-safe cache for SVG content
    private val cache = ConcurrentHashMap<String, String>()

    /**
     * Loads an SVG icon file with caching.
     *
     * @param fileName Name of the SVG file in /static/icons/ directory
     * @return SVG content as string
     * @throws IllegalArgumentException if icon file is not found
     */
    fun loadSvg(fileName: String): String {
        return cache.getOrPut(fileName) {
            logger.debug("Loading SVG icon from file (cache miss): {}", fileName)
            val content = loadResource(fileName)
            logger.debug("Cached SVG icon: {} ({} characters)", fileName, content.length)
            content
        }
    }

    override fun loadResource(path: String): String {
        val resource = javaClass.getResource("/static/icons/$path")
            ?: throw IllegalArgumentException("SVG icon file not found: $path")
        return resource.readText()
    }

    /**
     * Returns current cache statistics.
     *
     * @return Map with cache size and cached file names
     */
    fun getCacheStats(): Map<String, Any> {
        return mapOf(
            "size" to cache.size,
            "cachedFiles" to cache.keys.toList()
        )
    }

    /**
     * Clears the SVG cache.
     * Useful for testing or if SVG files are updated at runtime.
     */
    fun clearCache() {
        val sizeBefore = cache.size
        cache.clear()
        logger.info("Cleared SVG cache ({} entries removed)", sizeBefore)
    }
}
