package com.oleksii.surovtsev.portfolio.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.oleksii.surovtsev.portfolio.entity.TechCardData

object UtilFileManager {
    fun getTextFromFile(fileName: String): Pair<String, String> {
        val resource = javaClass.getResource("/static/text/$fileName")
            ?: throw IllegalArgumentException("File not found: $fileName")
        val lines = resource.readText().lines()
        if (lines.isEmpty()) throw IllegalArgumentException("File $fileName is empty")

        val title = lines[0]
        val description = lines.drop(1).joinToString("\n")
        return Pair(title, description)
    }

    fun getTechStackFromJson(fileName: String): List<TechCardData> {
        val resource = javaClass.getResource("/static/$fileName")
            ?: throw IllegalArgumentException("File not found: $fileName")
        val objectMapper = jacksonObjectMapper()
        return objectMapper.readValue(resource.readText())
    }

    fun loadSvg(fileName: String): String {
        val resource = javaClass.getResource("/static/icons/$fileName")
            ?: throw IllegalArgumentException("Icon file not found: $fileName")
        return resource.readText()
    }
}