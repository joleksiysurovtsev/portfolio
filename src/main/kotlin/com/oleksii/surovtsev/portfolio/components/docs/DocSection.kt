package com.oleksii.surovtsev.portfolio.components.docs

import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.orderedlayout.VerticalLayout

/**
 * Component for documentation sections with consistent styling
 */
class DocSection(
    title: String,
    sectionId: String? = null,
    isSubSection: Boolean = false
) : VerticalLayout() {

    init {
        isPadding = false
        isSpacing = true
        setWidthFull()

        // Add section ID for navigation
        sectionId?.let { setId(it) }

        // Create appropriate heading
        val heading = if (isSubSection) {
            H3(title).apply {
                addClassName("doc-section-subtitle")
            }
        } else {
            H2(title).apply {
                addClassName("doc-section-title")
            }
        }

        add(heading)

        // Add visual separator for main sections
        if (!isSubSection) {
            val separator = Div().apply {
                addClassName("doc-section-separator")
                setWidthFull()
            }
            add(separator)
        }

        addClassName("doc-section")
        if (isSubSection) {
            addClassName("doc-subsection")
        }
    }

    /**
     * Add content to the section
     */
    fun addContent(vararg components: com.vaadin.flow.component.Component) {
        components.forEach { add(it) }
    }
}