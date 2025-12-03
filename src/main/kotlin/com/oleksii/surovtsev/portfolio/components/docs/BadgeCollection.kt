package com.oleksii.surovtsev.portfolio.components.docs

import com.vaadin.flow.component.html.Anchor
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

/**
 * Collection of badges for plugin documentation header
 */
class BadgeCollection : HorizontalLayout() {

    init {
        isPadding = false
        isSpacing = true
        addClassName("badge-collection")
        defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER

        // Gradle Plugin Portal badge
        val gradleBadge = createBadge(
            "https://img.shields.io/gradle-plugin-portal/v/dev.surovtsev.claude-review?label=Gradle%20Plugin%20Portal&logo=gradle",
            "Gradle Plugin Portal",
            "https://plugins.gradle.org/plugin/dev.surovtsev.claude-review"
        )

        // License badge
        val licenseBadge = createBadge(
            "https://img.shields.io/badge/License-Apache%202.0-blue.svg",
            "License",
            "https://opensource.org/licenses/Apache-2.0"
        )

        // CI badge
        val ciBadge = createBadge(
            "https://github.com/joleksiysurovtsev/claude-review-plugin/workflows/CI/badge.svg",
            "CI",
            "https://github.com/joleksiysurovtsev/claude-review-plugin/actions/workflows/ci.yml"
        )

        // Gradle version badge
        val gradleVersionBadge = createBadge(
            "https://img.shields.io/badge/Gradle-7.0+-06A0CE?logo=gradle",
            "Gradle",
            "https://gradle.org"
        )

        // Java version badge
        val javaBadge = createBadge(
            "https://img.shields.io/badge/Java-11%20%7C%2017%20%7C%2021-007396?logo=java",
            "Java",
            "https://www.java.com/"
        )

        add(gradleBadge, licenseBadge, ciBadge, gradleVersionBadge, javaBadge)
    }

    private fun createBadge(imageUrl: String, alt: String, linkUrl: String): Anchor {
        val badge = Image(imageUrl, alt).apply {
            addClassName("doc-badge")
            element.setAttribute("loading", "lazy")
        }

        return Anchor(linkUrl, badge).apply {
            element.setAttribute("target", "_blank")
            element.setAttribute("rel", "noopener noreferrer")
            addClassName("doc-badge-link")
        }
    }
}