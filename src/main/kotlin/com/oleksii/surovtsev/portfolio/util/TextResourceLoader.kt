package com.oleksii.surovtsev.portfolio.util

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * Loader for text file resources.
 */
@Component
class TextResourceLoader : ResourceLoader {

    private val logger = LoggerFactory.getLogger(TextResourceLoader::class.java)

    /**
     * Loads a text file and returns it as a list of lines.
     *
     * @param fileName Name of the text file in /static/text/ directory
     * @return List of lines from the file
     * @throws IllegalArgumentException if file is not found or is empty
     */
    fun loadTextLines(fileName: String): List<String> {
        logger.debug("Loading text file: {}", fileName)

        val content = loadResource(fileName)
        val lines = content.lines()

        if (lines.isEmpty()) {
            logger.warn("Text file is empty: {}", fileName)
            throw IllegalArgumentException("File $fileName is empty")
        }

        logger.debug("Loaded {} lines from text file: {}", lines.size, fileName)
        return lines
    }

    override fun loadResource(path: String): String {
        val resource = javaClass.getResource("/static/text/$path")
            ?: throw IllegalArgumentException("Text file not found: $path")
        return resource.readText()
    }
}
