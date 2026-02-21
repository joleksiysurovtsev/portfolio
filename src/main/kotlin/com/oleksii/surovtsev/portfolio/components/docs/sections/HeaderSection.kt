package com.oleksii.surovtsev.portfolio.components.docs.sections

import com.oleksii.surovtsev.portfolio.components.docs.BadgeCollection
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.orderedlayout.VerticalLayout

/**
 * Header section component for plugin documentation
 */
class HeaderSection : VerticalLayout() {
    init {
        isPadding = true
        isSpacing = true
        addClassName("docs-header")
        setWidthFull()

        // Title
        val title = H1("Claude AI Code Review Plugin").apply {
            addClassName("docs-title")
        }

        // Badge collection
        val badges = BadgeCollection()

        // Description
        val description = Paragraph(
            "A Gradle plugin that automates code review using Claude AI. " +
            "The plugin analyzes Git diffs and provides structured feedback about " +
            "code quality, potential issues, and improvements in seconds."
        ).apply {
            addClassName("docs-description")
        }

        add(title, badges, description)
    }
}
