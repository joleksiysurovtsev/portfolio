package com.oleksii.surovtsev.portfolio.view

import com.oleksii.surovtsev.portfolio.layout.MainLayout
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.component.tabs.Tabs
import com.vaadin.flow.component.tabs.TabsVariant
import com.vaadin.flow.router.Route


@Route(value = "/about", layout = MainLayout::class)
class AboutView : VerticalLayout() {

    init {
        setWidthFull()
        justifyContentMode = FlexComponent.JustifyContentMode.START
        alignItems = Alignment.CENTER
        style.set("padding", "20px")

        add(
            AboutMyselfBlock(),
            StrengthsAndKeySkillsBlock(),
            EducationAndCertificationBlock(),
            CareerGoalsAndPhilosophyBlock()
        )
    }

}

class AboutMyselfBlock : HorizontalLayout() {


    init {
        val photoLayout = Div().apply {
            val photo = Image("img/my-photo-r.png", "My Photo")
            add(photo)
            className = "about-photo"
        }
        add(photoLayout)

        val textLayout = VerticalLayout().apply {
            add(H2("About Me"))
            add(
                Paragraph("Hello! My name is Oleksii Surovtsev, and I am an experienced Kotlin developer with deep expertise in creating microservices and integrating complex systems. My work combines creativity, analytics, and innovation. I strive to develop high-quality, optimized, and scalable software.")
            )
        }

        add(textLayout)
    }
}

class StrengthsAndKeySkillsBlock : Div() {
    init {

        val details: Tab = Tab("Technical Skills")
        val payment: Tab = Tab("Personal Qualities")

        val tabs: Tabs = Tabs(details, payment)
        tabs.addThemeVariants(TabsVariant.LUMO_CENTERED)

        add(tabs)



        val title = H2("Strengths and Key Skills")
        add(title)
        add(H3("Technical Skills:"))
        add(
            Paragraph("• Programming languages: Kotlin, Java, JavaScript."),
            Paragraph("• Frameworks: Spring Boot, Vaadin, Hibernate."),
            Paragraph("• Tools: Gradle, Docker, Kafka, Git."),
            Paragraph("• Databases: PostgreSQL, MySQL, MongoDB."),
            Paragraph("• Architecture: Microservices, REST API, integration with external systems.")
        )
        add(H3("Personal Qualities:"))
        add(
            Paragraph("• Responsibility and result-oriented approach."),
            Paragraph("• Excellent teamwork skills."),
            Paragraph("• Ability to solve complex problems and propose innovative solutions."),
            Paragraph("• Constant desire to learn and improve.")
        )
    }
}

class EducationAndCertificationBlock : VerticalLayout() {
    init {
        add(H2("Education and Certifications"))
        add(H3("Higher Education:"))
        add(
            Paragraph("Kyiv National University of Technologies and Design"),
            Paragraph("Specialization: System Programming"),
            Paragraph("Degree: Specialist"),
            Paragraph("Study Period: 2002–2007")
        )
        add(H3("Courses and Professional Development:"))
        add(
            Paragraph("• Java for Web Development (SPD-Ukraine, 2020) — learned the basics of Java for web development, including Spring Framework, databases, and REST API."),
            Paragraph("• Basics of Docker and Kubernetes (Udemy, 2021)."),
            Paragraph("• Basics of Apache Kafka (Confluent Academy, 2022)."),
            Paragraph("• Advanced Kotlin Development (JetBrains Academy, 2023)."),
            Paragraph("• Microservices Architecture (Coursera, 2023).")
        )
    }
}

class CareerGoalsAndPhilosophyBlock : VerticalLayout() {
    init {
        add(H2("Career Goals and Philosophy"))
        add(H3("My Goals:"))
        add(
            Paragraph("• Master new professional certifications, including AWS and other cloud technologies."),
            Paragraph("• Develop public speaking skills and improve English communication."),
            Paragraph("• Continue creating solutions that simplify users' lives and help businesses grow.")
        )
        add(H3("Philosophy:"))
        add(
            Paragraph("I believe that a successful developer is not just a master of technical solutions but also someone who can work in a team, understand the needs of businesses and users. My goal is to seamlessly connect technology and business, creating elegant and reliable products.")
        )
    }
}