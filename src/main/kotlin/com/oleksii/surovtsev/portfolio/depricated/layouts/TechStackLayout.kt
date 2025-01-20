package com.oleksii.surovtsev.portfolio.depricated.layouts

//import com.oleksii.surovtsev.portfolio.depricated.builders.FooterBuilder
import com.oleksii.surovtsev.portfolio.depricated.builders.HeaderBuilder
import com.vaadin.flow.component.AttachEvent
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.RouterLayout

class TechStackLayout : VerticalLayout(), RouterLayout {

    private val headerBuilder: HeaderBuilder by lazy { HeaderBuilder() }
//    private val footerBuilder: FooterBuilder by lazy { FooterBuilder() }

    // Skill list with descriptions
    private val skills = listOf(
        Skill("Kotlin", "kotlin.svg", "Kotlin is my primary programming language, which I use for developing backend services and creating robust, efficient, and scalable applications. Its modern features like null safety, coroutines, and concise syntax enhance productivity."),
        Skill("Java", "java.svg", "Java is a versatile language I use to maintain legacy applications, implement new features, and ensure cross-platform compatibility. It is essential for working with enterprise-level software."),
        Skill("Spring", "spring.svg", "Spring Framework is my go-to tool for building REST APIs and microservices. With its extensive ecosystem, I develop scalable, secure, and highly maintainable applications efficiently."),
        Skill("Docker", "docker.svg", "Docker is integral for containerizing applications to ensure seamless deployment and environment consistency. I use it to manage dependencies, isolate environments, and streamline CI/CD pipelines."),
        Skill("Kafka", "kafka.svg", "Apache Kafka is my choice for real-time message streaming between microservices. I design reliable and fault-tolerant data pipelines with it to handle high-throughput and low-latency requirements."),
        Skill("HTML", "html.svg", "HTML forms the backbone of web development, which I use to structure content and create interactive user interfaces that adhere to modern web standards."),
        Skill("CSS", "css.svg", "CSS enables me to design responsive and visually appealing web pages, implementing custom styles, animations, and layouts that enhance user experience."),
        Skill("JS", "js.svg", "JavaScript is essential for adding dynamic behavior to web applications. I use it for building interactive interfaces, client-side validation, and improving application usability."),
        Skill("BitBucket", "BitBucket.svg", "BitBucket is my preferred platform for source code management and collaboration. It helps me manage repositories, perform code reviews, and integrate CI/CD pipelines."),
        Skill("ElasticSearch", "elasticsearch.svg", "ElasticSearch powers the full-text search and analytics features in my applications. I use it to handle large-scale search queries and provide real-time insights."),
        Skill("GIT", "git.svg", "GIT is an indispensable version control tool for me. It helps me track changes, manage branches, and collaborate with team members efficiently."),
        Skill("GitLab", "gitlab.svg", "GitLab enhances my CI/CD workflows and repository management. I utilize its robust pipelines to automate testing, deployment, and delivery processes."),
        Skill("Gradle", "gradleLogo.svg", "Gradle automates my build processes, making it easier to manage dependencies, compile code, and generate artifacts for complex projects."),
        Skill("HELM", "helm.svg", "HELM is a Kubernetes package manager I use to define, install, and upgrade applications in cloud environments, ensuring simplified and consistent deployments."),
        Skill("JasperSoft", "jasper.svg", "JasperSoft facilitates creating and generating reports with dynamic data. I use it to build visually appealing, exportable reports for business analytics."),
        Skill("Jenkins", "jenkins.svg", "Jenkins automates CI/CD pipelines, enabling me to build, test, and deploy applications seamlessly. I rely on it to ensure faster and reliable delivery of software updates."),
        Skill("JiraIntegration", "Jira.svg", "Jira integration simplifies task management and project tracking. It allows me to link development work with project requirements and monitor progress effectively."),
        Skill("Kubernetes", "kubernetes.svg", "Kubernetes orchestrates containerized applications for me, offering automated scaling, deployment, and management of services in distributed environments."),
        Skill("Markdown", "Markdown.svg", "Markdown is my preferred tool for creating documentation, allowing me to write clean, readable text files with formatting suitable for technical and non-technical audiences."),
        Skill("PostgresSQL", "postgresSQL.svg", "PostgreSQL is a powerful relational database system I use for reliable data storage, advanced querying, and ensuring data integrity in large-scale applications."),
        Skill("Postman", "postman.svg", "Postman is my essential tool for testing and debugging APIs. It allows me to validate endpoints, simulate requests, and analyze responses during development.")
    )
    init {
        width = "100%"
        height = "100vh"
        defaultHorizontalComponentAlignment = Alignment.CENTER
        val header: VerticalLayout = headerBuilder.createHeader()
        val content: VerticalLayout = createContent()
//        val footer: VerticalLayout = footerBuilder.createFooter()
        add(header)
        addAndExpand(content)
//        add(footer)
    }

    private fun createContent(): VerticalLayout {
        val content = VerticalLayout().apply {
            defaultHorizontalComponentAlignment = Alignment.START
            isSpacing = false
            isPadding = true
            addClassName("skills-achievements")
        }

        // Creating sections for each skill
        skills.forEach { skill ->
            val skillSection = createSkillSection(skill)
            content.add(skillSection)
        }

        return content
    }

    private fun createSkillSection(skill: Skill): VerticalLayout {
        return VerticalLayout().apply {
            addClassName("skill-section")
            isSpacing = true
            isPadding = true

            // section header
            val titleWrapper = HorizontalLayout().apply {
                addClassName("title-wrapper")

                val svgContent = loadSvg(skill.iconName)
                val svgIcon = createSvgIcon(svgContent)

                val titleLabel = H3(skill.title)
                add(svgIcon, titleLabel)
                defaultVerticalComponentAlignment = Alignment.START
                alignItems = Alignment.START
            }
            add(titleWrapper)

            // Skill description
            val description = Paragraph(skill.description)
            description.addClassName("skill-description")
            addClassName("edu-ach-card")
            add(description)
        }
    }

    override fun onAttach(attachEvent: AttachEvent) {
        super.onAttach(attachEvent)
        super.addClassName("fade-in")
        attachEvent.ui.page.executeJs(
            """
            const savedTheme = localStorage.getItem('theme') || 'light';
            document.documentElement.setAttribute('theme', savedTheme);
            """
        )
    }

    private fun loadSvg(fileName: String): String {
        val resource = javaClass.getResourceAsStream("/static/icons/tech/$fileName")
            ?: throw IllegalArgumentException("Icon file not found: $fileName")
        return resource.bufferedReader().use { it.readText() }
    }

    private fun createSvgIcon(svgContent: String): Div {
        val div = Div()
        div.element.setProperty("innerHTML", svgContent)
        div.addClassName("svg-icon")
        return div
    }

    data class Skill(val title: String, val iconName: String, val description: String)
}
