package com.oleksii.surovtsev.portfolio.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

object UtilFileManager {

    val objectMapper: ObjectMapper = ObjectMapper().apply {
        registerModule(JavaTimeModule())
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    }

    fun getTextFromFile(fileName: String): List<String> {
        val resource = javaClass.getResource("/static/text/$fileName")
            ?: throw IllegalArgumentException("File not found: $fileName")
        val lines = resource.readText().lines()
        if (lines.isEmpty()) throw IllegalArgumentException("File $fileName is empty")
        return lines
    }

    inline fun <reified T> getDataFromJson(fileName: String): List<T> {
        val resource = UtilFileManager::class.java.getResource("/static/$fileName")
            ?: throw IllegalArgumentException("File not found: $fileName")
        return objectMapper.readValue(resource.readText(), object : TypeReference<List<T>>() {})
    }

    fun loadSvg(fileName: String): String {
        val resource = javaClass.getResource("/static/icons/$fileName")
            ?: throw IllegalArgumentException("Icon file not found: $fileName")
        return resource.readText()
    }
}