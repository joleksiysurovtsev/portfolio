package com.oleksii.surovtsev.portfolio.view.projects

import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexLayout

class TechnologiesLayout(technologies: List<String>) : FlexLayout() {

    init {
        addClassName("project-technologies-container")
        flexWrap = FlexWrap.WRAP
        justifyContentMode = FlexComponent.JustifyContentMode.START
        alignItems = FlexComponent.Alignment.CENTER
        setWidthFull()

        technologies.forEach { technology ->
            val link = Span(technology).apply {
                addClassName("project-technologies-link")
            }
            add(link)
        }
    }
}