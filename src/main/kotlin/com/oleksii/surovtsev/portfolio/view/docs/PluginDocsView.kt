package com.oleksii.surovtsev.portfolio.view.docs

import com.oleksii.surovtsev.portfolio.components.docs.*
import com.oleksii.surovtsev.portfolio.components.docs.sections.*
import com.oleksii.surovtsev.portfolio.config.RoutesConfig
import com.oleksii.surovtsev.portfolio.layout.MainLayout
import com.vaadin.flow.component.AttachEvent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

/**
 * Documentation page for Claude Code Review Gradle Plugin
 */
@Route(value = RoutesConfig.Projects.CLAUDE_REVIEW_PLUGIN, layout = MainLayout::class)
@PageTitle("Claude Code Review Plugin Documentation | Oleksii Surovtsev")
class PluginDocsView : VerticalLayout() {

    private lateinit var tableOfContents: TableOfContents

    init {
        isPadding = false
        isSpacing = false
        addClassName("plugin-docs-view")
        setWidthFull()

        // Create header section
        val header = HeaderSection()

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
                    FeaturesSection(),
                    InstallationSection(),
                    ConfigurationSection(),
                    UsageSection(),
                    CiCdSection(),
                    ApiKeySection(),
                    SupportSection()
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
}