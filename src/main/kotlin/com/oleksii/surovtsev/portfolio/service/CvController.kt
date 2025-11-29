package com.oleksii.surovtsev.portfolio.service

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller for CV generation endpoints.
 */
@RestController
class CvController(
    private val cvPdfService: CvPdfService,
    private val cvDataService: CvDataService
) {

    private val logger = LoggerFactory.getLogger(CvController::class.java)

    /**
     * Generates and returns a PDF version of the CV.
     *
     * @return ResponseEntity containing the PDF as a byte array
     */
    @GetMapping("/generate-cv")
    fun generateCv(): ResponseEntity<ByteArray> {
        return try {
            val cvData = cvDataService.getCvData()
            val pdfBytes = cvPdfService.generateCvPdf(cvData)

            logger.info("CV PDF generated successfully for download")

            ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cv.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes)
        } catch (e: Exception) {
            logger.error("Failed to generate CV PDF", e)
            ResponseEntity.internalServerError().build()
        }
    }
}