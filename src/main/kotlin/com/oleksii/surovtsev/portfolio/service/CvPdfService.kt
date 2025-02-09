package com.oleksii.surovtsev.portfolio.service

import com.oleksii.surovtsev.portfolio.service.domain.CvData
import net.sf.jasperreports.engine.*
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream

@Service
class CvPdfService {

    fun generateCvPdf(cvData: CvData): ByteArray {
        val templatePath = "templates/cv_template.jrxml"
        val templateStream = this::class.java.classLoader.getResourceAsStream(templatePath)
            ?: throw JRException("Jasper template not found: $templatePath")
        val jasperReport = JasperCompileManager.compileReport(templateStream)


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
        return outputStream.toByteArray()
    }
}

