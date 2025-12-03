package com.oleksii.surovtsev.portfolio.components.docs

import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H4
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout

/**
 * Feature card component for displaying plugin features
 */
class FeatureCard(
    private val icon: VaadinIcon,
    private val title: String,
    private val description: String,
    private val emoji: String? = null
) : VerticalLayout() {

    init {
        isPadding = true
        isSpacing = true
        addClassName("feature-card")
        setWidthFull()

        // Icon container with background
        val iconContainer = Div().apply {
            addClassName("feature-icon-container")

            if (emoji != null) {
                // Use emoji instead of icon
                val emojiSpan = Div(emoji).apply {
                    addClassName("feature-emoji")
                }
                add(emojiSpan)
            } else {
                val featureIcon = Icon(icon).apply {
                    addClassName("feature-icon")
                    setSize("24px")
                }
                add(featureIcon)
            }
        }

        // Title
        val featureTitle = H4(title).apply {
            addClassName("feature-title")
        }

        // Header layout (icon + title)
        val header = HorizontalLayout(iconContainer, featureTitle).apply {
            isPadding = false
            isSpacing = true
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
            addClassName("feature-header")
        }

        // Description
        val featureDescription = Paragraph(description).apply {
            addClassName("feature-description")
        }

        add(header, featureDescription)
    }

    companion object {
        /**
         * Create feature cards for plugin documentation
         */
        fun createPluginFeatures(): List<FeatureCard> {
            return listOf(
                FeatureCard(
                    VaadinIcon.AUTOMATION,
                    "AI-Powered Reviews",
                    "Uses Claude AI to analyze code changes and provide intelligent feedback about code quality, potential issues, and improvements",
                    "🤖"
                ),
                FeatureCard(
                    VaadinIcon.SEARCH,
                    "Smart Diff Analysis",
                    "Automatically detects changed files from Git and analyzes only the relevant changes in your pull requests",
                    "🔍"
                ),
                FeatureCard(
                    VaadinIcon.FILE_TEXT,
                    "Multiple Output Formats",
                    "Generate reports in Markdown, HTML, or JSON formats to suit your workflow and integration needs",
                    "📝"
                ),
                FeatureCard(
                    VaadinIcon.GLOBE,
                    "Multi-Language Support",
                    "Get code reviews in English or Russian, making it accessible for international development teams",
                    "🌍"
                ),
                FeatureCard(
                    VaadinIcon.FILTER,
                    "Flexible Filtering",
                    "Use include/exclude patterns to focus reviews on specific files and ignore generated or test code",
                    "🎯"
                ),
                FeatureCard(
                    VaadinIcon.ROCKET,
                    "Two Modes",
                    "Choose between API mode for cloud-based reviews or local Claude CLI mode for enhanced privacy",
                    "🚀"
                ),
                FeatureCard(
                    VaadinIcon.CHECK_CIRCLE,
                    "CI/CD Ready",
                    "Fail builds on critical issues to maintain code quality standards automatically in your pipeline",
                    "⚡"
                ),
                FeatureCard(
                    VaadinIcon.TIMER,
                    "Fast Reviews",
                    "Get comprehensive code analysis in seconds, not minutes, speeding up your development workflow",
                    "⏱️"
                )
            )
        }
    }
}