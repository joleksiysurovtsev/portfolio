package com.oleksii.surovtsev.portfolio.service

import com.oleksii.surovtsev.portfolio.service.domain.ContactInfo
import com.oleksii.surovtsev.portfolio.service.domain.CvData
import com.oleksii.surovtsev.portfolio.service.domain.Education
import com.oleksii.surovtsev.portfolio.service.domain.Experience
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CvController(private val cvPdfService: CvPdfService) {

    @GetMapping("/generate-cv")
    fun generateCv(): ResponseEntity<ByteArray> {
        val cvData = CvData(
            fullName = "Oleksii Surovtsev",
            position = "Middle Java/Kotlin Software Engineer",
            contactInfo = ContactInfo(
                location = "Ukraine, Chercasy",
                phone = "+380674708862",
                email = "joleksiysurovtsev@gmail.com",
                linkedin = "linkedin.com/in/oleksiy-surovtsev/",
                gitHub = "github.com/joleksiysurovtsev",
                site = "portfolio-production-1224.up.railway.app",
            ),
            experience = listOf(
                Experience(
                    companyName = "SPD Technology",
                    position = "Middle Java/Kotlin Software Engineer",
                    period = "2020-2024",
                    description = listOf(
                        "Develop and maintain scalable, high-performance Java-based applications using modern technologies.",
                        "Utilize a reactive programming approach with WebFlux to build efficient, non-blocking web services.",
                        "Leverage JPA for robust data persistence and management.",
                        "Design and implement custom libraries, including a Hibernate rollback SDK and a Gradle plugin, to streamline development processes.",
                        "Develop and integrate Jasper Reports and custom XML-based reports into a complex billing system.",
                        "Architect and implement microservices tailored to specific business needs, ensuring scalability and reliability.",
                        "Integrate seamlessly with third-party systems such as Jira API for automation and data synchronization.",
                        "Set up and manage Kubernetes environments, including the creation and configuration of Helm charts for deploying applications.",
                        "Conduct technical demos for client teams, showcasing progress, explaining features, and gathering feedback.",
                        "Collaborate with cross-functional teams to design and implement solutions aligned with business goals.",
                        "Optimize and refactor existing codebases to improve maintainability and performance.",
                        "Actively participate in code reviews, ensuring high-quality standards and best practices.",
                    ),
                    technologies = listOf(
                        "Kotlin",
                        "Spring Boot",
                        "Postgres",
                        "Hibernate",
                        "WebFlux",
                        "Gradle",
                        "Jasper Reports",
                        "Microservices",
                        "JPA",
                        "Jira API",
                        "Bitbucket",
                        "Reactive Programming",
                        "Kubernetes",
                        "Helm Charts"
                    )
                ),
            ),
            skills = listOf(
                "Kafka",
                "Spring Boot",
                "Kotlin",
                "Vaadin",
                "Gradle",
                "Hibernate",
                "PostgreSQL",
                "Docker",
                "AWS"
            ),
            education = listOf(
                Education("Master in Computer Science", "Cherkasy University", "2015-2020")
            )
        )

        val pdfBytes = cvPdfService.generateCvPdf(cvData)

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cv.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdfBytes)
    }
}