package com.oleksii.surovtsev.portfolio.components.docs.sections

import com.oleksii.surovtsev.portfolio.components.docs.CodeBlock
import com.oleksii.surovtsev.portfolio.components.docs.DocSection
import com.vaadin.flow.component.html.Paragraph

/**
 * CI/CD integration section component for plugin documentation
 */
class CiCdSection : DocSection("CI/CD Integration", "ci-cd") {
    init {
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

        addContent(
            Paragraph("Integrate the plugin into your CI/CD pipeline to automatically review pull requests:"),
            githubActions,
            jenkins
        )
    }
}
