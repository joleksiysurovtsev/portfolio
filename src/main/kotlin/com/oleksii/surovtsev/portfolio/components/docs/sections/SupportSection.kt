package com.oleksii.surovtsev.portfolio.components.docs.sections

import com.oleksii.surovtsev.portfolio.components.docs.DocSection
import com.vaadin.flow.component.html.*

/**
 * Support section component for plugin documentation
 */
class SupportSection : DocSection("Support", "support") {
    init {
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

        addContent(contactInfo)
    }
}
