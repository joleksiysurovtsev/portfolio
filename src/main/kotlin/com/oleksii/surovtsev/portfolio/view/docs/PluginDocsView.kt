package com.oleksii.surovtsev.portfolio.view.docs

import com.oleksii.surovtsev.portfolio.components.docs.*
import com.oleksii.surovtsev.portfolio.layout.MainLayout
import com.vaadin.flow.component.AttachEvent
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.theme.lumo.LumoUtility

/**
 * Documentation page for Claude Code Review Gradle Plugin
 */
@Route(value = "projects/claude-review-plugin", layout = MainLayout::class)
@PageTitle("Claude Code Review Plugin Documentation | Oleksii Surovtsev")
class PluginDocsView : VerticalLayout() {

    private lateinit var tableOfContents: TableOfContents

    init {
        isPadding = false
        isSpacing = false
        addClassName("plugin-docs-view")
        setWidthFull()

        // Create header section
        val header = createHeader()

        // Create main content layout with TOC and content
        val mainLayout = HorizontalLayout().apply {
            isPadding = true
            isSpacing = true
            addClassName("docs-main-layout")
            setWidthFull()

            // Table of Contents
            tableOfContents = TableOfContents.createPluginDocsToc().apply {
                addClassName("docs-toc")
            }

            // Content area
            val contentArea = VerticalLayout().apply {
                isPadding = false
                isSpacing = true
                addClassName("docs-content")
                setWidthFull()

                add(
                    createFeaturesSection(),
                    createInstallationSection(),
                    createConfigurationSection(),
                    createUsageSection(),
                    createCiCdSection(),
                    createApiKeySection(),
                    createSupportSection()
                )
            }

            add(tableOfContents)
            add(contentArea)
            setFlexGrow(0.0, tableOfContents)
            setFlexGrow(1.0, contentArea)
        }

        add(header, mainLayout)
    }

    override fun onAttach(attachEvent: AttachEvent) {
        super.onAttach(attachEvent)
        // Setup scroll spy after attachment
        tableOfContents.setupScrollSpy()
    }

    private fun createHeader(): VerticalLayout {
        return VerticalLayout().apply {
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

    private fun createFeaturesSection(): DocSection {
        val section = DocSection("Features", "features")

        // Create feature cards grid
        val featuresGrid = FlexLayout().apply {
            setFlexWrap(FlexLayout.FlexWrap.WRAP)
            addClassName("features-grid")

            FeatureCard.createPluginFeatures().forEach { card ->
                card.apply {
                    element.style.set("flex", "1 1 calc(50% - 1rem)")
                    element.style.set("min-width", "300px")
                }
                add(card)
            }
        }

        section.addContent(featuresGrid)
        return section
    }

    private fun createInstallationSection(): DocSection {
        val section = DocSection("Installation", "installation")

        section.addContent(
            Paragraph("Add the plugin to your Gradle build configuration:"),

            // Kotlin DSL subsection
            DocSection("Gradle Kotlin DSL", "gradle-kotlin", true).apply {
                val kotlinCode = """
                    |plugins {
                    |    id("dev.surovtsev.claude-review") version "1.0.0"
                    |}
                """.trimMargin()
                addContent(CodeBlock.kotlin(kotlinCode, "build.gradle.kts"))
            },

            // Groovy subsection
            DocSection("Gradle Groovy", "gradle-groovy", true).apply {
                val groovyCode = """
                    |buildscript {
                    |    repositories {
                    |        gradlePluginPortal()
                    |    }
                    |    dependencies {
                    |        classpath("dev.surovtsev:claude-review:1.0.0")
                    |    }
                    |}
                    |
                    |apply plugin: "dev.surovtsev.claude-review"
                """.trimMargin()
                addContent(CodeBlock.groovy(groovyCode, "build.gradle"))
            }
        )

        return section
    }

    private fun createConfigurationSection(): DocSection {
        val section = DocSection("Configuration", "configuration")

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

        section.addContent(basicConfig, advancedConfig, fileFiltering)
        return section
    }

    private fun createUsageSection(): DocSection {
        val section = DocSection("Usage", "usage")

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

        section.addContent(
            apiMode,
            localMode,
            Paragraph("Reports are saved to the configured output directory (default: build/reviews/).")
        )

        return section
    }

    private fun createCiCdSection(): DocSection {
        val section = DocSection("CI/CD Integration", "ci-cd")

        // GitHub Actions subsection
        val githubActions = DocSection("GitHub Actions", "github-actions", true).apply {
            val yamlCode = """
                |name: Code Review
                |on: [pull_request]
                |
                |jobs:
                |  review:
                |    runs-on: ubuntu-latest
                |    steps:
                |      - uses: actions/checkout@v3
                |        with:
                |          fetch-depth: 0
                |
                |      - name: Set up JDK
                |        uses: actions/setup-java@v3
                |        with:
                |          java-version: '17'
                |          distribution: 'temurin'
                |
                |      - name: Run Claude Review
                |        env:
                |          ANTHROPIC_API_KEY: ${'$'}{{ secrets.ANTHROPIC_API_KEY }}
                |        run: ./gradlew claudeReview
                |
                |      - name: Upload Review Report
                |        uses: actions/upload-artifact@v3
                |        if: always()
                |        with:
                |          name: review-report
                |          path: build/reviews/
            """.trimMargin()

            addContent(
                Paragraph("Example GitHub Actions workflow:"),
                CodeBlock.yaml(yamlCode, ".github/workflows/code-review.yml")
            )
        }

        // Jenkins subsection
        val jenkins = DocSection("Jenkins", "jenkins", true).apply {
            val groovyCode = """
                |pipeline {
                |    agent any
                |
                |    environment {
                |        ANTHROPIC_API_KEY = credentials('anthropic-api-key')
                |    }
                |
                |    stages {
                |        stage('Code Review') {
                |            steps {
                |                sh './gradlew claudeReview'
                |            }
                |            post {
                |                always {
                |                    archiveArtifacts artifacts: 'build/reviews/**/*',
                |                                     allowEmptyArchive: true
                |                }
                |            }
                |        }
                |    }
                |}
            """.trimMargin()

            addContent(
                Paragraph("Example Jenkins pipeline:"),
                CodeBlock.groovy(groovyCode, "Jenkinsfile")
            )
        }

        section.addContent(
            Paragraph("Integrate the plugin into your CI/CD pipeline to automatically review pull requests:"),
            githubActions,
            jenkins
        )

        return section
    }

    private fun createApiKeySection(): DocSection {
        val section = DocSection("Getting an API Key", "getting-api-key")

        section.addContent(
            Paragraph("To use the Claude API, you need to obtain an API key from Anthropic:"),
            OrderedList().apply {
                add(ListItem("Sign up at Anthropic Console").apply {
                    add(Anchor("https://console.anthropic.com/", " (console.anthropic.com)").apply {
                        element.setAttribute("target", "_blank")
                        element.setAttribute("rel", "noopener noreferrer")
                    })
                })
                add(ListItem("Navigate to the API Keys section"))
                add(ListItem("Generate a new API key"))
                add(ListItem("Set it as an environment variable or add to your build configuration"))
            },
            Paragraph("Keep your API key secure and never commit it to version control!")
        )

        return section
    }

    private fun createSupportSection(): DocSection {
        val section = DocSection("Support", "support")

        val contactInfo = Div().apply {
            addClassName("support-links")

            val issuesLink = Paragraph().apply {
                add(Span("🐛 Issues: "))
                add(Anchor(
                    "https://github.com/joleksiysurovtsev/claude-review-plugin/issues",
                    "GitHub Issues"
                ).apply {
                    element.setAttribute("target", "_blank")
                    element.setAttribute("rel", "noopener noreferrer")
                })
            }

            val contactLink = Paragraph().apply {
                add(Span("📧 Contact: "))
                add(Anchor("https://surovtsev.dev/", "surovtsev.dev").apply {
                    element.setAttribute("target", "_blank")
                    element.setAttribute("rel", "noopener noreferrer")
                })
            }

            val docsLink = Paragraph().apply {
                add(Span("📖 Documentation: "))
                add(Anchor(
                    "https://github.com/joleksiysurovtsev/claude-review-plugin/wiki",
                    "Wiki"
                ).apply {
                    element.setAttribute("target", "_blank")
                    element.setAttribute("rel", "noopener noreferrer")
                })
            }

            val authorInfo = Paragraph().apply {
                addClassName("author-info")
                add(Span("Author: ").apply {
                    element.style.set("font-weight", "600")
                })
                add(Span("Oleksii Surovtsev"))
                add(Span(" · "))
                add(Anchor("https://github.com/joleksiysurovtsev", "@joleksiysurovtsev").apply {
                    element.setAttribute("target", "_blank")
                    element.setAttribute("rel", "noopener noreferrer")
                })
            }

            add(issuesLink, contactLink, docsLink, Hr(), authorInfo)
        }

        section.addContent(contactInfo)
        return section
    }
}