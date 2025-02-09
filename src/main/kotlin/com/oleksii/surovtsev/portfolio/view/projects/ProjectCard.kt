package com.oleksii.surovtsev.portfolio.view.projects

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextArea

class ProjectCard(
    title: String,
    description: String?,
    technologies: List<String>,
    repositoryUrl: String? = null,
    demoUrl: String? = null,
    imageUrl: String? = null,
) : VerticalLayout() {
    init {
        addClassName("project-card")
        isSpacing = true
        isPadding = true

        imageUrl?.let { url ->
            val image = Image(url, "Project Image")
            add(image)
        }

        title.let { t ->
            val projectTitle = Span(t).apply { addClassName("project-title") }
            add(projectTitle)
        }

        description.let { d ->
            val projectDescription = TextArea().apply {
                value = d
                isReadOnly = true
                addClassName("project-description")
            }
            add(projectDescription)
        }

        val technologies = TechnologiesLayout(technologies)
        add(technologies)

        val buttonsLayout = HorizontalLayout().apply {
            isSpacing = true
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            setWidthFull()
        }

        repositoryUrl?.let { url ->
            val repoButton = Button("Repository", Icon(VaadinIcon.CODE)).apply {
                addClickListener {
                    getUI().ifPresent { ui -> ui.page.executeJs("window.open('$url', '_blank')") }
                }
            }
            buttonsLayout.add(repoButton)
        }

        demoUrl?.let { url ->
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