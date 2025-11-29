package com.oleksii.surovtsev.portfolio.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * Loader for JSON file resources.
 */
@Component
class JsonResourceLoader {

    private val logger = LoggerFactory.getLogger(JsonResourceLoader::class.java)

    val objectMapper: ObjectMapper = ObjectMapper().apply {
        registerModule(JavaTimeModule())
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)
    }

    /**
     * Loads and deserializes a JSON file into a list of objects.
     *
     * @param T Type of objects in the JSON array
     * @param fileName Name of the JSON file in /static/ directory
     * @return List of deserialized objects
     * @throws IllegalArgumentException if file is not found
     */
    fun <T> load(fileName: String, typeReference: TypeReference<List<T>>): List<T> {
        logger.debug("Loading JSON file: {}", fileName)

        val content = loadResource(fileName)
        val result: List<T> = objectMapper.readValue(content, typeReference)

        logger.debug("Loaded {} items from JSON file: {}", result.size, fileName)
        return result
    }

    /**
     * Loads and deserializes a JSON file into a single object.
     *
     * @param T Type of the object
     * @param fileName Name of the JSON file in /static/ directory
     * @param valueType Class type of the object
     * @return Deserialized object
     * @throws IllegalArgumentException if file is not found
     */
    fun <T> loadSingle(fileName: String, valueType: Class<T>): T {
        logger.debug("Loading single object from JSON file: {}", fileName)

        val content = loadResource(fileName)
        val result = objectMapper.readValue(content, valueType)

        logger.debug("Loaded object from JSON file: {}", fileName)
        return result
    }

    fun loadResource(path: String): String {
        val resource = javaClass.getResource("/static/$path")
            ?: throw IllegalArgumentException("JSON file not found: $path")
        return resource.readText()
    }
}

// Extension function for convenient inline reified usage
inline fun <reified T> JsonResourceLoader.load(fileName: String): List<T> {
    return load(fileName, object : TypeReference<List<T>>() {})
}

inline fun <reified T> JsonResourceLoader.loadSingle(fileName: String): T {
    return loadSingle(fileName, T::class.java)
}
