package com.oleksii.surovtsev.portfolio.view.experience

import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout

class SkillBar(skill: String, percentage: Int): VerticalLayout() {
    init {
        isSpacing = false
        isPadding = false
        style.set("margin-bottom", "8px")

        val skillHeader = HorizontalLayout().apply {
            setWidthFull()
            addClassName("skill-bar-header")
            justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN
            add(
                Span(skill).apply {
                    addClassName("progress-bar-text")
                },
                Span("$percentage%").apply {
                    addClassName("progress-bar-percentage")
                }
            )
        }

        val progressBar = Div().apply {
            className = "progress-bar-container"
            add(Div().apply {
                className = "progress-bar-fill"
                style.set("--progress", "$percentage%")
            })
        }

        add(skillHeader, progressBar)
    }
}