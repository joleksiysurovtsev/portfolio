package com.oleksii.surovtsev.portfolio.components.docs.sections

import com.oleksii.surovtsev.portfolio.components.docs.CodeBlock
import com.oleksii.surovtsev.portfolio.components.docs.ConfigTable
import com.oleksii.surovtsev.portfolio.components.docs.DocSection
import com.vaadin.flow.component.html.ListItem
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.html.UnorderedList

/**
 * Configuration section component for plugin documentation
 */
class ConfigurationSection : DocSection("Configuration", "configuration") {
    init {
        // Basic configuration subsection
        val basicConfig = DocSection("Basic Configuration", "basic-config", true).apply {
            val basicCode = """
                |claudeReview {
                |    // API Configuration (required for API mode)
                |    apiKey = System.getenv("ANTHROPIC_API_KEY") // or set directly
                |
                |    // Model selection
                |    model = "claude-3-5-sonnet-20241022" // default
                |
                |    // Review language
                |    language = ClaudeReviewExtension.Language.ENGLISH // or RUSSIAN
                |
                |    // Output configuration
                |    outputDir = "build/reviews"
                |    outputFormat = ClaudeReviewExtension.OutputFormat.MARKDOWN // or HTML, JSON
                |}
            """.trimMargin()

            addContent(
                Paragraph("Configure the plugin with your API key and preferences:"),
                CodeBlock.kotlin(basicCode, "build.gradle.kts"),
                Paragraph("Basic configuration parameters:"),
                ConfigTable(ConfigTable.createBasicConfigs())
            )
        }

        // Advanced configuration subsection
        val advancedConfig = DocSection("Advanced Configuration", "advanced-config", true).apply {
            val advancedCode = """
                |claudeReview {
                |    // Git Configuration
                |    targetBranch = "main" // default: auto-detect
                |    fallbackCommits = 10  // number of commits to review if branch detection fails
                |
                |    // File Filtering
                |    includePatterns = listOf(
                |        "**/*.kt",
                |        "**/*.java",
                |        "**/*.ts",
                |        "**/*.js"
                |    )
                |    excludePatterns = listOf(
                |        "**/build/**",
                |        "**/generated/**",
                |        "**/*.test.kt"
                |    )
                |
                |    // Behavior
                |    failOnCritical = true // fail build if critical issues found
                |    maxFileSize = 100_000L // skip files larger than this (in bytes)
                |    maxTokens = 4096 // maximum tokens for Claude response
                |
                |    // Local CLI Configuration
                |    claudePath = "/usr/local/bin/claude" // path to Claude CLI (for local mode)
                |}
            """.trimMargin()

            addContent(
                Paragraph("Advanced configuration options for fine-tuning the plugin behavior:"),
                CodeBlock.kotlin(advancedCode, "build.gradle.kts"),
                Paragraph("Advanced configuration parameters:"),
                ConfigTable(ConfigTable.createAdvancedConfigs())
            )
        }

        // File filtering subsection
        val fileFiltering = DocSection("File Filtering", "file-filtering", true).apply {
            addContent(
                Paragraph(
                    "Use glob patterns to include or exclude specific files from review. " +
                    "This helps focus the review on relevant code and skip generated or test files."
                ),
                UnorderedList().apply {
                    add(ListItem("Include patterns are applied first"))
                    add(ListItem("Exclude patterns override include patterns"))
                    add(ListItem("Files larger than maxFileSize are automatically skipped"))
                    add(ListItem("Binary files are always excluded"))
                }
            )
        }

        addContent(basicConfig, advancedConfig, fileFiltering)
    }
}
