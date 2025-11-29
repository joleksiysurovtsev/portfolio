package com.oleksii.surovtsev.portfolio.service

import com.oleksii.surovtsev.portfolio.service.domain.CvData
import com.oleksii.surovtsev.portfolio.util.JsonResourceLoader
import com.oleksii.surovtsev.portfolio.util.load
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * Service for managing CV data.
 *
 * Loads CV data from JSON configuration file.
 */
@Service
class CvDataService(private val jsonLoader: JsonResourceLoader) {

    private val logger = LoggerFactory.getLogger(CvDataService::class.java)

    /**
     * Retrieves CV data from JSON file.
     *
     * @return CvData object containing full CV information
     * @throws IllegalArgumentException if CV data file cannot be loaded
     */
    fun getCvData(): CvData {
        return try {
            val cvDataList: List<CvData> = jsonLoader.load("cv-data.json")
            require(cvDataList.isNotEmpty()) { "CV data file is empty" }

            val cvData = cvDataList.first()
            logger.debug("Loaded CV data for: {}", cvData.fullName)
            cvData
        } catch (e: Exception) {
            logger.error("Failed to load CV data from JSON file", e)
            throw IllegalArgumentException("Failed to load CV data", e)
        }
    }
}
