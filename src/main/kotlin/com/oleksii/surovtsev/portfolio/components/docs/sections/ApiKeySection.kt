package com.oleksii.surovtsev.portfolio.components.docs.sections

import com.oleksii.surovtsev.portfolio.components.docs.DocSection
import com.vaadin.flow.component.html.Anchor
import com.vaadin.flow.component.html.ListItem
import com.vaadin.flow.component.html.OrderedList
import com.vaadin.flow.component.html.Paragraph

/**
 * API key section component for plugin documentation
 */
class ApiKeySection : DocSection("Getting an API Key", "getting-api-key") {
    init {
        addContent(
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
    }
}
