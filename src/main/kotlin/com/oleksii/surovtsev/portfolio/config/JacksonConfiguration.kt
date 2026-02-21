package com.oleksii.surovtsev.portfolio.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

/**
 * Centralized Jackson ObjectMapper configuration.
 * Provides a single, optimized ObjectMapper instance for the entire application.
 */
@Configuration
class JacksonConfiguration {

    /**
     * Primary ObjectMapper bean used throughout the application.
     * This ensures consistent JSON serialization/deserialization behavior.
     */
    @Bean
    @Primary
    fun objectMapper(builder: Jackson2ObjectMapperBuilder): ObjectMapper {
        return builder
            .modules(
                JavaTimeModule(),      // Support for Java 8 time types
                KotlinModule.Builder() // Kotlin-specific support
                    .withReflectionCacheSize(512)
                    .build()
            )
            .featuresToEnable(
                // Deserialization features
                DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
                DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL
            )
            .featuresToDisable(
                // Serialization features
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                SerializationFeature.FAIL_ON_EMPTY_BEANS,

                // Deserialization features
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES
            )
            .build()
    }

    /**
     * Specialized ObjectMapper for resource loading.
     * This can have different configuration if needed.
     */
    @Bean(name = ["resourceObjectMapper"])
    fun resourceObjectMapper(): ObjectMapper {
        return ObjectMapper().apply {
            registerModule(JavaTimeModule())
            registerModule(KotlinModule.Builder()
                .withReflectionCacheSize(256)
                .build())
            configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)
        }
    }
}