package com.oleksii.surovtsev.portfolio.service

import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody

/**
 * REST controller for CV generation endpoints.
 */
@RestController
@RequestMapping("/api/cv")
class CvController(
    private val cvPdfService: CvPdfService,
    private val cvDataService: CvDataService
) {

    private val logger = LoggerFactory.getLogger(CvController::class.java)

    /**
     * Generates and streams a PDF version of the CV.
     * This method is memory-efficient as it streams the PDF directly to the response.
     *
     * @return StreamingResponseBody that writes PDF directly to output stream
     */
    @GetMapping("/download")
    fun downloadCv(): ResponseEntity<StreamingResponseBody> {
        return try {
            val cvData = cvDataService.getCvData()

            val stream = StreamingResponseBody { outputStream ->
                try {
                    cvPdfService.generateCvPdfStream(cvData, outputStream)
                    outputStream.flush()
                } catch (e: Exception) {
                    logger.error("Error while streaming CV PDF", e)
                    throw e
                }
            }

            logger.info("CV PDF streaming initiated")

            ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Oleksii_Surovtsev_CV.pdf\"")
                .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                .header(HttpHeaders.PRAGMA, "no-cache")
                .header(HttpHeaders.EXPIRES, "0")
                .contentType(MediaType.APPLICATION_PDF)
                .body(stream)
        } catch (e: Exception) {
            logger.error("Failed to initiate CV PDF streaming", e)
            ResponseEntity.internalServerError().build()
        }
    }

    /**
     * Legacy endpoint for backward compatibility.
     * Redirects to the new streaming endpoint.
     */
    @GetMapping("/generate-cv")
    fun generateCvLegacy(response: HttpServletResponse) {
        response.sendRedirect("/api/cv/download")
    }
}