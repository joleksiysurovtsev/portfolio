package com.oleksii.surovtsev.portfolio.builders

import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout

class ContentMainPageBuilder {

    fun createContent(): VerticalLayout {
        val contentLayout = VerticalLayout().apply {
            addClassName("greeting-layout-style")
            defaultHorizontalComponentAlignment = Alignment.CENTER
            isSpacing = true
            isPadding = true
        }

        val greetingLayout = getGreetingLayout()
        val techStackLayout = getTechStackLayout()
        val myProjectLayout:VerticalLayout = getMyProjectLayout()
        contentLayout.add(greetingLayout, techStackLayout, myProjectLayout)

        return contentLayout
    }

    private fun getGreetingLayout(): HorizontalLayout {
        return HorizontalLayout().apply {
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            alignItems = Alignment.CENTER
            style.set("padding", "20px")
            element.setAttribute("id", "about") // Set ID for section Home

            val textLayout = VerticalLayout().apply {
                width = "65%"
                defaultHorizontalComponentAlignment = Alignment.START
                isSpacing = true

                val greetingText = Div().apply {
                    text = "Hi there 👋"
                    addClassName("greeting-text-header")
                }

                val descriptionText = Div().apply {
                    text =
                        """
                        A passionate Java/Kotlin Developer with over 2 years of experience in building scalable 
                        enterprise applications. I specialize in crafting efficient backend solutions, integrating 
                        microservices, and working with cloud technologies. I thrive on solving complex problems, 
                        writing clean code, and delivering impactful software solutions.
                        """.trimIndent()
                    style.set("font-size", "18px")
                    style.set("line-height", "1.5")
                }

                add(greetingText, descriptionText)
            }

            val photoLayout = VerticalLayout().apply {
                width = "25%"
                defaultHorizontalComponentAlignment = Alignment.START
                style.set("padding-right", "20px")

                val photo = Image("img/my-photo.png", "My Photo").apply {
                    style.set("width", "250px")
                    style.set("height", "250px")
                    style.set("border-radius", "50%")
                }

                add(photo)
            }

            add(textLayout, photoLayout)
        }
    }
    private fun getTechStackLayout(): VerticalLayout {
        return VerticalLayout().apply {
            width = "100%"
            defaultHorizontalComponentAlignment = Alignment.CENTER
            style.set("padding", "20px")
            element.setAttribute("id", "tech-stack") // Setting ID for Tech Stack section

            val title = Div().apply {
                text = "My Tech Stack"
                style.set("font-size", "24px")
                style.set("font-weight", "bold")
                style.set("margin-bottom", "10px")
            }

            val subtitle = Div().apply {
                text = "Technologies I’ve been working with recently"
                style.set("font-size", "16px")
                style.set("color", "#666")
                style.set("margin-bottom", "20px")
            }

            val techIconsLayout = HorizontalLayout().apply {
                justifyContentMode = FlexComponent.JustifyContentMode.CENTER
                alignItems = Alignment.CENTER
                style.set("flex-wrap", "wrap")
                isSpacing = true

                val kotlinIcon = createTechTile("icons/tech/kotlin.svg", "Kotlin")
                val javaIcon = createTechTile("icons/tech/java.svg", "Java")
                val springIcon = createTechTile("icons/tech/spring.svg", "Spring")
                val dockerIcon = createTechTile("icons/tech/docker.svg", "Docker")
                val kafkaIcon = createTechTile("icons/tech/kafka.svg", "Kafka")
                val htmlIcon = createTechTile("icons/tech/html.svg", "HTML")
                val cssIcon = createTechTile("icons/tech/css.svg", "CSS")
                val jsIcon = createTechTile("icons/tech/js.svg", "JS")
                val bitBucketIcon = createTechTile("icons/tech/BitBucket.svg", "BitBucket")
                val elasticsearchIcon = createTechTile("icons/tech/elasticsearch.svg", "ElasticSearch")
                val gitIcon = createTechTile("icons/tech/git.svg", "GIT")
                val gitlabIcon = createTechTile("icons/tech/gitlab.svg", "GitLab")
                val gradleIcon = createTechTile("icons/tech/gradleLogo.svg", "Gradle")
                val helmIcon = createTechTile("icons/tech/helm.svg", "HELM")
                val jaspersoftIcon = createTechTile("icons/tech/jasper.svg", "JasperSoft")
                val jenkinsIcon = createTechTile("icons/tech/jenkins.svg", "Jenkins")
                val jiraIcon = createTechTile("icons/tech/Jira.svg", "JiraIntegration")
                val kubernetesIcon = createTechTile("icons/tech/kubernetes.svg", "Kubernetes")
                val markdownIcon = createTechTile("icons/tech/Markdown.svg", "Markdown")
                val postgresSQLIcon = createTechTile("icons/tech/postgresSQL.svg", "PostgresSQL")
                val postmanIcon = createTechTile("icons/tech/postman.svg", "Postman")

                add(
                    kotlinIcon,
                    javaIcon,
                    springIcon,
                    dockerIcon,
                    kafkaIcon,
                    htmlIcon,
                    cssIcon,
                    jsIcon,
                    elasticsearchIcon,
                    gitIcon,
                    gitlabIcon,
                    bitBucketIcon,
                    gradleIcon,
                    helmIcon,
                    jaspersoftIcon,
                    jenkinsIcon,
                    jiraIcon,
                    kubernetesIcon,
                    markdownIcon,
                    postmanIcon,
                    postgresSQLIcon,
                    postmanIcon
                )
            }

            add(title, subtitle, techIconsLayout)
        }
    }
    private fun getMyProjectLayout(): VerticalLayout {
        return VerticalLayout().apply {
            width = "100%"
            defaultHorizontalComponentAlignment = Alignment.CENTER
            style.set("padding", "20px")
            element.setAttribute("id", "projects") // Set ID for Projects section

            // Заголовок секции
            val title = Div().apply {
                text = "Projects"
                style.set("font-size", "36px")
                style.set("font-weight", "bold")
                style.set("margin-bottom", "10px")
            }

            val subtitle = Div().apply {
                text = "Things I’ve built so far"
                style.set("font-size", "18px")
                style.set("color", "#666")
                style.set("margin-bottom", "20px")
            }

            val projectsLayout = HorizontalLayout().apply {
                justifyContentMode = FlexComponent.JustifyContentMode.CENTER
                alignItems = Alignment.CENTER
                style.set("flex-wrap", "wrap")
                isSpacing = true

                // Пример плиток проектов
                add(createProjectTile(
                    "img/gedleVer2.png",
                    "Project Tile 1",
                    "This is sample project description random things are here in description. This is sample project lorem ipsum generator for dummy content.",
                    listOf("HTML", "JavaScript", "SASS", "React"),
                    "https://livepreview.com",
                    "https://github.com/repo1"
                ))

                add(createProjectTile(
                    "img/project2.png",
                    "Project Tile 2",
                    "This is another project description with more lorem ipsum for testing purposes. It includes random text to demonstrate layout.",
                    listOf("Kotlin", "Spring", "Docker"),
                    "https://livepreview2.com",
                    "https://github.com/repo2"
                ))
            }

            add(title, subtitle, projectsLayout)
        }
    }

    private fun createProjectTile(
        imagePath: String,
        title: String,
        description: String,
        techStack: List<String>,
        livePreviewUrl: String,
        codeUrl: String
    ): VerticalLayout {
        return VerticalLayout().apply {
            width = "373px"
            height = "567px"
            style.set("border-radius", "12px")
            style.set("box-shadow", "0 4px 6px rgba(0, 0, 0, 0.1)")
            style.set("overflow", "hidden")
            style.set("margin", "10px")
            style.set("background", "#fff")
            style.set("padding", "0") // Remove indentations

            // Изображение в верхней части
            val image = Image(imagePath, title).apply {
                style.set("width", "100%") // The image is full width
//                style.set("height", "260px") // Fixed altitude
                style.set("object-fit", "cover") // Image adjustment
                style.set("margin", "0") // Remove indents
            }

            // the title of draft
            val projectTitle = Div().apply {
                text = title
                style.set("font-size", "20px")
                style.set("font-weight", "bold")
                style.set("margin", "10px 0")
                addClassName("greeting-layout-style")
            }

            // project description
            val projectDescription = Div().apply {
                text = description
                style.set("font-size", "14px")
                style.set("color", "#666")
                style.set("margin", "10px 0")
                addClassName("greeting-layout-style")
            }

            // technology stack
            val techStackDiv = Div().apply {
                text = "Tech stack: ${techStack.joinToString(", ")}"
                style.set("font-size", "14px")
                style.set("font-weight", "bold")
                style.set("color", "#333")
                style.set("margin", "10px 0")
            }

            // reference to draft
            val linksLayout = HorizontalLayout().apply {
                justifyContentMode = FlexComponent.JustifyContentMode.START
                alignItems = Alignment.CENTER
                style.set("margin-top", "10px")

                val livePreviewLink = Div().apply {
                    element.setProperty("innerHTML", """<a href="$livePreviewUrl" target="_blank" style="text-decoration:none; font-size:14px;">🔗 Live Preview</a>""")
                    style.set("margin-right", "10px")
                }

                val codeLink = Div().apply {
                    element.setProperty("innerHTML", """<a href="$codeUrl" target="_blank" style="text-decoration:none; font-size:14px;">💻 View Code</a>""")
                }

                add(livePreviewLink, codeLink)
            }

            add(image, projectTitle, projectDescription, techStackDiv, linksLayout)
        }
    }

    private fun createTechTile(iconPath: String, label: String): VerticalLayout {
        return VerticalLayout().apply {
            width = "100px"
            alignItems = Alignment.CENTER
            style.set("padding", "10px")
            style.set("border-radius", "8px")
            style.set("box-shadow", "0 4px 6px rgba(0, 0, 0, 0.1)")
            style.set("margin", "5px")

            val icon = Image(iconPath, label).apply {
                addClassName("my-tech-icon-style")
            }

            val iconLabel = Div().apply {
                text = label
                addClassName("my-tech-icon-label-style")
            }

            add(icon, iconLabel)
        }
    }
}