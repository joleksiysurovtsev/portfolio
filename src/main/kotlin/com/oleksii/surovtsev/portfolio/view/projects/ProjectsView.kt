package com.oleksii.surovtsev.portfolio.view.projects

import com.oleksii.surovtsev.portfolio.components.CustomDividerH2
import com.oleksii.surovtsev.portfolio.layout.MainLayout
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.router.Route


@Route(value = "/projects", layout = MainLayout::class)
class ProjectsView : VerticalLayout() {
    init {
        setWidthFull()
        justifyContentMode = FlexComponent.JustifyContentMode.START
        alignItems = Alignment.CENTER
        addClassName("projects-view")
        add(
            CustomDividerH2("MY PROJECTS"),
            createProjectsList()
        )
    }

    private fun createProjectsList(): VerticalLayout {
        val projectsLayout = VerticalLayout().apply {
            setWidth("80%")
            isSpacing = true
            isPadding = true
        }

        val projects = listOf(
            Project(
                title = "Gradle version catalog example",
                description = "This project serves as a Gradle Version Catalog, making it easier to manage and share dependencies and plugins versions across your projects.",
                technologies = listOf("Kotlin", "Gradle"),
                repositoryUrl = "https://github.com/joleksiysurovtsev/gradle-version-catalog-example",
                demoUrl = "https://medium.com/@joleksiysurovtsev/gradle-version-catalog-simplifying-dependency-management-between-independent-projects-4a8e8dd2cbe0",
                imageUrl = "https://miro.medium.com/v2/resize:fit:720/format:webp/1*0jRG4hrnn18WSjxE8A5AHA.png"
            ),
            Project(
                title = "Portfolio Website",
                description = "My personal portfolio website built with Vaadin and Kotlin.",
                technologies = listOf("Kotlin", "Vaadin", "Spring Boot"),
                repositoryUrl = "https://github.com/yourusername/portfolio",
                demoUrl = "https://yourportfolio.com",
                imageUrl = "https://via.placeholder.com/400x200"
            ),
        )

        projects.forEach { project ->
            projectsLayout.add(ProjectCard(project))
        }

        return projectsLayout
    }
}

data class Project(
    val title: String,
    val description: String,
    val technologies: List<String>,
    val repositoryUrl: String? = null,
    val demoUrl: String? = null,
    val imageUrl: String? = null
)



class ProjectCard(private val project: Project) : VerticalLayout() {
    init {
        addClassName("project-card")
        setWidth("400px")
        isSpacing = true
        isPadding = true

        project.imageUrl?.let { url ->
            val image = Image(url, "Project Image").apply {
                width = "100%"
                height = "200px"
                style["object-fit"] = "cover"
            }
            add(image)
        }

        val title = Span(project.title).apply {
            addClassName("project-title")
            style["font-size"] = "1.5em"
            style["font-weight"] = "bold"
        }
        add(title)

        val description = TextArea().apply {
            value = project.description
            isReadOnly = true
            width = "100%"
            height = "100px"
            style["font-size"] = "1em"
        }
        add(description)

        val technologies = Span("Technologies: ${project.technologies.joinToString(", ")}").apply {
            addClassName("project-technologies")
            style["font-size"] = "0.9em"
            style["color"] = "var(--lumo-secondary-text-color)"
        }
        add(technologies)

        val buttonsLayout = HorizontalLayout().apply {
            isSpacing = true
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            setWidthFull()
        }

        project.repositoryUrl?.let { url ->
            val repoButton = Button("Repository", Icon(VaadinIcon.CODE)).apply {
                addClickListener {
                    getUI().ifPresent { ui -> ui.page.executeJs("window.open('$url', '_blank')") }
                }
            }
            buttonsLayout.add(repoButton)
        }

        project.demoUrl?.let { url ->
            val demoButton = Button("Demo", Icon(VaadinIcon.EXTERNAL_LINK)).apply {
                addClickListener {
                    getUI().ifPresent { ui -> ui.page.executeJs("window.open('$url', '_blank')") }
                }
            }
            buttonsLayout.add(demoButton)
        }

        add(buttonsLayout)
    }
}