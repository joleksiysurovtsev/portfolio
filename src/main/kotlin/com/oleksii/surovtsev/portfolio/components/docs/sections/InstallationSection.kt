package com.oleksii.surovtsev.portfolio.components.docs.sections

import com.oleksii.surovtsev.portfolio.components.docs.CodeBlock
import com.oleksii.surovtsev.portfolio.components.docs.DocSection
import com.vaadin.flow.component.html.Paragraph

/**
 * Installation section component for plugin documentation
 */
class InstallationSection : DocSection("Installation", "installation") {
    init {
        addContent(
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
    }
}
