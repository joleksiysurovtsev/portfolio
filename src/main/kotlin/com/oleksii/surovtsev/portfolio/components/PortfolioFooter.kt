package com.oleksii.surovtsev.portfolio.components

import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.orderedlayout.HorizontalLayout


class PortfolioFooter : Footer() {
    init {
        className = "footer"
        setWidthFull()

        val about = Div().apply {
            className = "footer-copy"
            add(Paragraph("© 2025 Oleksii Surovtsev. All Rights Reserved."))
        }

        val links = Div().apply {
            className = "footer-links"
            add(
                Anchor("#portfolio", "Portfolio").apply { className = "footer-link" },
                Anchor("#about", "About Me").apply { className = "footer-link" },
                Anchor("#contact", "Contact").apply { className = "footer-link" }
            )
        }


        val social = createSocialLinksIconBlock()
        add(about, links, social)
    }

    private fun createSocialLinksIconBlock(): HorizontalLayout {
        val githubIcon = createClickableImage("icons/github_mark.svg", "https://github.com/joleksiysurovtsev")
        val linkedInIcon = createClickableImage("icons/linkedIn_icon.svg", "https://www.linkedin.com/in/oleksiy-surovtsev/")
        val facebookIcon = createClickableImage("icons/facebook_icon.svg", "https://www.facebook.com/oleksiysurovtsev")

        return HorizontalLayout(githubIcon, linkedInIcon, facebookIcon).apply {
            addClassName("social-links")
        }
    }

    private fun createClickableImage(imagePath: String, url: String): Anchor {
        val image = Image(imagePath, "Custom Icon").apply {
            addClassName("clickable-icon")
        }

        return Anchor(url, image)
            .apply {
                setTarget("_blank") // Open in new tab
            }
    }
}