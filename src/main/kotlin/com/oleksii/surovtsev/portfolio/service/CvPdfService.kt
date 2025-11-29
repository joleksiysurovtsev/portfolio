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

    /**
     * Generates a PDF CV from provided data.
     *
     * @param cvData The CV data to convert to PDF
     * @return ByteArray containing the PDF content
     * @throws JRException if template is not found or PDF generation fails
     */
    fun generateCvPdf(cvData: CvData): ByteArray {
        logger.debug("Starting CV PDF generation for: {}", cvData.fullName)

        val templatePath = "templates/cv_template.jrxml"
        val templateStream = this::class.java.classLoader.getResourceAsStream(templatePath)
            ?: throw JRException("Jasper template not found: $templatePath")

        return templateStream.use { stream ->
            val jasperReport = JasperCompileManager.compileReport(stream)

            val experienceDataSources = cvData.experience.map { experience ->
                mapOf(
                    "companyName" to experience.companyName,
                    "position" to experience.position,
                    "period" to experience.period,
                    "description" to experience.description,
                    "technologiesList" to experience.technologies
                )
            }

            val parameters = mapOf(
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

            val jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, JREmptyDataSource())
            val outputStream = ByteArrayOutputStream()

            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream)
            val pdfBytes = outputStream.toByteArray()

            logger.info("CV PDF generated successfully for: {}, size: {} bytes", cvData.fullName, pdfBytes.size)
            pdfBytes
        }
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

        val templatePath = "templates/cv_template.jrxml"
        val templateStream = this::class.java.classLoader.getResourceAsStream(templatePath)
            ?: throw JRException("Jasper template not found: $templatePath")

        templateStream.use { stream ->
            val jasperReport = JasperCompileManager.compileReport(stream)

            val experienceDataSources = cvData.experience.map { experience ->
                mapOf(
                    "companyName" to experience.companyName,
                    "position" to experience.position,
                    "period" to experience.period,
                    "description" to experience.description,
                    "technologiesList" to experience.technologies
                )
            }

            val parameters = mapOf(
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

            val jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, JREmptyDataSource())
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream)

            logger.info("CV PDF streamed successfully for: {}", cvData.fullName)
        }
    }
}

