package com.oleksii.surovtsev.portfolio.components.docs

import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.data.renderer.ComponentRenderer

/**
 * Configuration table component for displaying plugin parameters
 */
class ConfigTable(
    private val configs: List<ConfigParameter>
) : Div() {

    init {
        addClassName("config-table-container")
        setWidthFull()

        val grid = Grid<ConfigParameter>().apply {
            addClassName("config-table")
            setWidthFull()
            setAllRowsVisible(true)
            addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES)

            // Parameter column
            addColumn(ComponentRenderer { param ->
                Div().apply {
                    val nameSpan = Span(param.name).apply {
                        addClassName("config-param-name")
                        if (param.required) {
                            element.setAttribute("title", "Required parameter")
                            val requiredMark = Span(" *").apply {
                                addClassName("config-required")
                            }
                            add(requiredMark)
                        }
                    }
                    add(nameSpan)
                }
            }).setHeader("Parameter")
                .setAutoWidth(true)
                .setFlexGrow(0)

            // Type column
            addColumn(ComponentRenderer { param ->
                Span(param.type).apply {
                    addClassName("config-param-type")
                }
            }).setHeader("Type")
                .setAutoWidth(true)
                .setFlexGrow(0)

            // Default column
            addColumn(ComponentRenderer { param ->
                Span(param.defaultValue ?: "-").apply {
                    addClassName("config-param-default")
                    if (param.defaultValue != null) {
                        element.style.set("font-family", "monospace")
                    }
                }
            }).setHeader("Default")
                .setAutoWidth(true)
                .setFlexGrow(0)

            // Description column
            addColumn(ComponentRenderer { param ->
                Div().apply {
                    addClassName("config-param-description")
                    add(Span(param.description))

                    // Add example if present
                    param.example?.let { example ->
                        val exampleDiv = Div().apply {
                            addClassName("config-param-example")
                            val label = Span("Example: ").apply {
                                addClassName("config-example-label")
                            }
                            val code = Span(example).apply {
                                addClassName("config-example-code")
                            }
                            add(label, code)
                        }
                        add(exampleDiv)
                    }
                }
            }).setHeader("Description")
                .setFlexGrow(1)

            setItems(configs)
        }

        add(grid)
    }

    /**
     * Data class for configuration parameters
     */
    data class ConfigParameter(
        val name: String,
        val type: String,
        val defaultValue: String? = null,
        val description: String,
        val required: Boolean = false,
        val example: String? = null
    )

    companion object {
        /**
         * Create basic configuration parameters
         */
        fun createBasicConfigs(): List<ConfigParameter> {
            return listOf(
                ConfigParameter(
                    "apiKey",
                    "String",
                    null,
                    "Your Anthropic API key for Claude AI",
                    required = true,
                    example = "System.getenv(\"ANTHROPIC_API_KEY\")"
                ),
                ConfigParameter(
                    "model",
                    "String",
                    "claude-3-5-sonnet-20241022",
                    "Claude model to use for reviews",
                    example = "\"claude-3-5-sonnet-20241022\""
                ),
                ConfigParameter(
                    "language",
                    "Language",
                    "ENGLISH",
                    "Language for review output (ENGLISH or RUSSIAN)",
                    example = "ClaudeReviewExtension.Language.ENGLISH"
                ),
                ConfigParameter(
                    "outputDir",
                    "String",
                    "build/reviews",
                    "Directory where review reports will be saved",
                    example = "\"build/reviews\""
                ),
                ConfigParameter(
                    "outputFormat",
                    "OutputFormat",
                    "MARKDOWN",
                    "Format for review reports (MARKDOWN, HTML, or JSON)",
                    example = "ClaudeReviewExtension.OutputFormat.MARKDOWN"
                )
            )
        }

        /**
         * Create advanced configuration parameters
         */
        fun createAdvancedConfigs(): List<ConfigParameter> {
            return listOf(
                ConfigParameter(
                    "targetBranch",
                    "String",
                    "auto-detect",
                    "Target branch for comparison (main, master, develop, etc.)",
                    example = "\"main\""
                ),
                ConfigParameter(
                    "fallbackCommits",
                    "Int",
                    "10",
                    "Number of commits to review if branch detection fails",
                    example = "10"
                ),
                ConfigParameter(
                    "includePatterns",
                    "List<String>",
                    "all files",
                    "Glob patterns for files to include in review",
                    example = "listOf(\"**/*.kt\", \"**/*.java\")"
                ),
                ConfigParameter(
                    "excludePatterns",
                    "List<String>",
                    "none",
                    "Glob patterns for files to exclude from review",
                    example = "listOf(\"**/build/**\", \"**/generated/**\")"
                ),
                ConfigParameter(
                    "failOnCritical",
                    "Boolean",
                    "false",
                    "Fail the build if critical issues are found",
                    example = "true"
                ),
                ConfigParameter(
                    "maxFileSize",
                    "Long",
                    "100000",
                    "Maximum file size in bytes to review",
                    example = "100_000L"
                ),
                ConfigParameter(
                    "maxTokens",
                    "Int",
                    "4096",
                    "Maximum tokens for Claude response",
                    example = "4096"
                ),
                ConfigParameter(
                    "claudePath",
                    "String",
                    "/usr/local/bin/claude",
                    "Path to Claude CLI executable (for local mode)",
                    example = "\"/usr/local/bin/claude\""
                )
            )
        }
    }
}