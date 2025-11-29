package com.oleksii.surovtsev.portfolio.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

/**
 * Legacy utility class for file management.
 *
 * @deprecated Use specialized loaders instead:
 * - [TextResourceLoader] for text files
 * - [JsonResourceLoader] for JSON files
 * - [SvgResourceLoader] for SVG icons
 *
 * This class is kept for backward compatibility and delegates to new implementations.
 */
@Deprecated(
    message = "Use specialized resource loaders (TextResourceLoader, JsonResourceLoader, SvgResourceLoader)",
    replaceWith = ReplaceWith("JsonResourceLoader, TextResourceLoader, SvgResourceLoader")
)
object UtilFileManager {

    val textLoader = TextResourceLoader()
    val jsonLoader = JsonResourceLoader()
    val svgLoader = SvgResourceLoader()

    val objectMapper: ObjectMapper = ObjectMapper().apply {
        registerModule(JavaTimeModule())
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)
    }

    /**
     * @deprecated Use [TextResourceLoader.loadTextLines] instead
     */
    @Deprecated("Use TextResourceLoader.loadTextLines instead")
    fun getTextFromFile(fileName: String): List<String> {
        return textLoader.loadTextLines(fileName)
    }

    /**
     * @deprecated Use [JsonResourceLoader.load] instead
     */
    @Deprecated("Use JsonResourceLoader.load instead")
    inline fun <reified T> getDataFromJson(fileName: String): List<T> {
        return jsonLoader.load(fileName)
    }

    /**
     * @deprecated Use [SvgResourceLoader.loadSvg] instead
     */
    @Deprecated("Use SvgResourceLoader.loadSvg instead")
    fun loadSvg(fileName: String): String {
        return svgLoader.loadSvg(fileName)
    }
}