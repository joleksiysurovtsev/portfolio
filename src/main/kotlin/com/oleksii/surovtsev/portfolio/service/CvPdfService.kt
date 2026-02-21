package com.oleksii.surovtsev.portfolio.service

import com.oleksii.surovtsev.portfolio.service.domain.CvData
import net.sf.jasperreports.engine.*
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.io.OutputStream

@Service
class CvPdfService {

    private val logger = LoggerFactory.getLogger(CvPdfService::class.java)

    companion object {
        private const val TEMPLATE_PATH = "templates/cv_template.jrxml"
    }

    /**
     * Generates a PDF CV from provided data.
     *
     * @param cvData The CV data to convert to PDF
     * @return ByteArray containing the PDF content
     * @throws JRException if template is not found or PDF generation fails
     */
    fun generateCvPdf(cvData: CvData): ByteArray {
        logger.debug("Starting CV PDF generation for: {}", cvData.fullName)

        val jasperPrint = prepareJasperPrint(cvData)
        val outputStream = ByteArrayOutputStream()

        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream)
        val pdfBytes = outputStream.toByteArray()

        logger.info("CV PDF generated successfully for: {}, size: {} bytes", cvData.fullName, pdfBytes.size)
        return pdfBytes
    }

    /**
     * Generates CV PDF and writes it directly to an OutputStream.
     * This method is more memory-efficient for large PDFs.
     *
     * @param cvData The CV data to convert to PDF
     * @param outputStream The output stream to write the PDF to
     * @throws JRException if template is not found or PDF generation fails
     */
    fun generateCvPdfStream(cvData: CvData, outputStream: OutputStream) {
        logger.debug("Starting streaming CV PDF generation for: {}", cvData.fullName)

        val jasperPrint = prepareJasperPrint(cvData)
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream)

        logger.info("CV PDF streamed successfully for: {}", cvData.fullName)
    }

    /**
     * Loads and compiles the Jasper report template.
     *
     * @return Compiled JasperReport
     * @throws JRException if template is not found or compilation fails
     */
    private fun loadAndCompileTemplate(): JasperReport {
        val templateStream = this::class.java.classLoader.getResourceAsStream(TEMPLATE_PATH)
            ?: throw JRException("Jasper template not found: $TEMPLATE_PATH")

        return templateStream.use { stream ->
            JasperCompileManager.compileReport(stream)
        }
    }

    /**
     * Prepares experience data for the Jasper report.
     *
     * @param cvData The CV data
     * @return List of experience maps for Jasper
     */
    private fun prepareExperienceData(cvData: CvData): List<Map<String, Any?>> {
        return cvData.experience.map { experience ->
            mapOf(
                "companyName" to experience.companyName,
                "position" to experience.position,
                "period" to experience.period,
                "description" to experience.description,
                "technologiesList" to experience.technologies
            )
        }
    }

    /**
     * Prepares parameters for the Jasper report.
     *
     * @param cvData The CV data
     * @return Map of parameters for Jasper
     */
    private fun prepareReportParameters(cvData: CvData): Map<String, Any?> {
        val experienceDataSources = prepareExperienceData(cvData)

        return mapOf(
            "fullName" to cvData.fullName,
            "position" to cvData.position,
            "location" to cvData.contactInfo.location,
            "phone" to cvData.contactInfo.phone,
            "email" to cvData.contactInfo.email,
            "linkedin" to cvData.contactInfo.linkedin,
            "gitHub" to cvData.contactInfo.gitHub,
            "site" to cvData.contactInfo.site,
            "experienceList" to JRBeanCollectionDataSource(experienceDataSources),
            "skillsList" to cvData.skills,
            "educationList" to JRBeanCollectionDataSource(cvData.education)
        )
    }

    /**
     * Prepares the JasperPrint object with filled report data.
     *
     * @param cvData The CV data
     * @return Filled JasperPrint object
     * @throws JRException if report preparation fails
     */
    private fun prepareJasperPrint(cvData: CvData): JasperPrint {
        val jasperReport = loadAndCompileTemplate()
        val parameters = prepareReportParameters(cvData)
        return JasperFillManager.fillReport(jasperReport, parameters, JREmptyDataSource())
    }
}

