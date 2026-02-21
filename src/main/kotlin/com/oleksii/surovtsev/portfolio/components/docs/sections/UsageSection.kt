package com.oleksii.surovtsev.portfolio.components.docs.sections

import com.oleksii.surovtsev.portfolio.components.docs.CodeBlock
import com.oleksii.surovtsev.portfolio.components.docs.DocSection
import com.vaadin.flow.component.html.Paragraph

/**
 * Usage section component for plugin documentation
 */
class UsageSection : DocSection("Usage", "usage") {
    init {
        // API mode subsection
        val apiMode = DocSection("Using Claude API", "api-mode", true).apply {
            val bashCode = """
                |# Set your Anthropic API key
                |export ANTHROPIC_API_KEY="your-api-key-here"
                |
                |# Run the review
                |./gradlew claudeReview
            """.trimMargin()

            addContent(
                Paragraph("The recommended way to use the plugin is with the Claude API:"),
                CodeBlock.bash(bashCode),
                Paragraph("The plugin will analyze your Git diff and generate a review report in the specified format.")
            )
        }

        // Local mode subsection
        val localMode = DocSection("Using Local Claude CLI", "local-mode", true).apply {
            val bashCode = """
                |# Run review using local Claude CLI
                |./gradlew claudeReviewLocal
            """.trimMargin()

            addContent(
                Paragraph("If you have Claude CLI installed locally:"),
                CodeBlock.bash(bashCode),
                Paragraph(
                    "This mode uses your local Claude CLI installation instead of the API. " +
                    "Useful for enhanced privacy or when API access is restricted."
                )
            )
        }

        addContent(
            apiMode,
            localMode,
            Paragraph("Reports are saved to the configured output directory (default: build/reviews/).")
        )
    }
}
