package com.oleksii.surovtsev.portfolio.components.docs

import com.vaadin.flow.component.ClickEvent
import com.vaadin.flow.component.ComponentEventListener
import com.vaadin.flow.component.html.Anchor
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.html.ListItem
import com.vaadin.flow.component.html.UnorderedList
import com.vaadin.flow.component.orderedlayout.VerticalLayout

/**
 * Table of Contents component for navigation through documentation sections
 */
class TableOfContents(
    private val sections: List<TocSection>
) : VerticalLayout() {

    private val tocItems = mutableMapOf<String, Anchor>()
    private var activeSection: String? = null

    init {
        isPadding = true
        isSpacing = true
        addClassName("table-of-contents")
        setWidth("280px")

        // Add title
        val title = H3("Contents").apply {
            addClassName("toc-title")
        }
        add(title)

        // Add separator
        val separator = Div().apply {
            addClassName("toc-separator")
            setWidthFull()
        }
        add(separator)

        // Create navigation list
        val navList = UnorderedList().apply {
            addClassName("toc-list")
            sections.forEach { section ->
                add(createTocItem(section))
            }
        }
        add(navList)

        // Make it sticky
        element.style.set("position", "sticky")
        element.style.set("top", "20px")
        element.style.set("max-height", "calc(100vh - 100px)")
        element.style.set("overflow-y", "auto")
    }

    private fun createTocItem(section: TocSection): ListItem {
        val listItem = ListItem().apply {
            addClassName("toc-item")
            if (section.isSubSection) {
                addClassName("toc-subsection-item")
            }
        }

        val anchor = Anchor("#${section.id}", section.title).apply {
            addClassName("toc-link")
            if (!section.isSubSection) {
                addClassName("toc-main-link")
            }

            // Override default anchor behavior for smooth scrolling
            element.addEventListener("click", { _ ->
                scrollToSection(section.id)
                setActiveSection(section.id)
            })

            // Prevent default navigation
            element.executeJs("this.onclick = function(e) { e.preventDefault(); }")
        }

        tocItems[section.id] = anchor
        listItem.add(anchor)

        // Add sub-sections if present
        if (section.subSections.isNotEmpty()) {
            val subList = UnorderedList().apply {
                addClassName("toc-sublist")
                section.subSections.forEach { subSection ->
                    add(createTocItem(subSection))
                }
            }
            listItem.add(subList)
        }

        return listItem
    }

    private fun scrollToSection(sectionId: String) {
        // Execute smooth scroll via JavaScript
        element.executeJs(
            """
            const element = document.getElementById($0);
            if (element) {
                const yOffset = -80; // Offset for header
                const y = element.getBoundingClientRect().top + window.pageYOffset + yOffset;
                window.scrollTo({top: y, behavior: 'smooth'});
            }
            """,
            sectionId
        )
    }

    fun setActiveSection(sectionId: String) {
        // Remove active class from previous section
        activeSection?.let {
            tocItems[it]?.removeClassName("toc-link-active")
        }

        // Add active class to new section
        tocItems[sectionId]?.addClassName("toc-link-active")
        activeSection = sectionId
    }

    /**
     * Set up scroll spy to highlight current section
     */
    fun setupScrollSpy() {
        // Collect all section IDs including subsections
        val allSectionIds = mutableListOf<String>()
        sections.forEach { section ->
            allSectionIds.add(section.id)
            section.subSections.forEach { subSection ->
                allSectionIds.add(subSection.id)
            }
        }

        // Convert section IDs to a JSON array string
        val sectionIds = allSectionIds.joinToString(separator = ",") { "\"$it\"" }

        element.executeJs(
            """
            const sectionIds = [$sectionIds];
            const tocElement = this;

            const observerOptions = {
                rootMargin: '-20% 0px -70% 0px'
            };

            const observerCallback = (entries) => {
                entries.forEach(entry => {
                    if (entry.isIntersecting) {
                        const sectionId = entry.target.id;
                        tocElement.${'$'}server.setActiveSectionFromClient(sectionId);
                    }
                });
            };

            const observer = new IntersectionObserver(observerCallback, observerOptions);

            sectionIds.forEach(sectionId => {
                const element = document.getElementById(sectionId);
                if (element) {
                    observer.observe(element);
                }
            });
            """
        )
    }

    @com.vaadin.flow.component.ClientCallable
    fun setActiveSectionFromClient(sectionId: String) {
        setActiveSection(sectionId)
    }

    /**
     * Data class for TOC sections
     */
    data class TocSection(
        val id: String,
        val title: String,
        val isSubSection: Boolean = false,
        val subSections: List<TocSection> = emptyList()
    )

    companion object {
        /**
         * Create TOC for plugin documentation
         */
        fun createPluginDocsToc(): TableOfContents {
            val sections = listOf(
                TocSection(
                    "features",
                    "Features",
                    subSections = listOf(
                        TocSection("ai-powered", "AI-Powered Reviews", true),
                        TocSection("smart-diff", "Smart Diff Analysis", true),
                        TocSection("output-formats", "Output Formats", true)
                    )
                ),
                TocSection(
                    "installation",
                    "Installation",
                    subSections = listOf(
                        TocSection("gradle-kotlin", "Gradle Kotlin DSL", true),
                        TocSection("gradle-groovy", "Gradle Groovy", true)
                    )
                ),
                TocSection(
                    "configuration",
                    "Configuration",
                    subSections = listOf(
                        TocSection("basic-config", "Basic Configuration", true),
                        TocSection("advanced-config", "Advanced Configuration", true),
                        TocSection("file-filtering", "File Filtering", true)
                    )
                ),
                TocSection(
                    "usage",
                    "Usage",
                    subSections = listOf(
                        TocSection("api-mode", "Using Claude API", true),
                        TocSection("local-mode", "Using Local CLI", true)
                    )
                ),
                TocSection(
                    "ci-cd",
                    "CI/CD Integration",
                    subSections = listOf(
                        TocSection("github-actions", "GitHub Actions", true),
                        TocSection("jenkins", "Jenkins", true)
                    )
                ),
                TocSection("getting-api-key", "Getting an API Key"),
                TocSection("support", "Support")
            )

            return TableOfContents(sections)
        }
    }
}