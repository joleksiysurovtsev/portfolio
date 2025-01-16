package com.oleksii.surovtsev.portfolio.utils

import com.vaadin.flow.component.html.Anchor
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

object SocialLinksIconBlockBuilder {

    fun createSocialLinksIconBlock(): HorizontalLayout {
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

object NavButtonsBuilder {
    fun createNavButtons(): HorizontalLayout {
        val navButton1 = Anchor("#about", "About").apply {
            addClassName("nav-button")
            element.setAttribute("data-target", "about")
        }
        val navButton2 = Anchor("tech-stack", "Tech Stack").apply {
            addClassName("nav-button")
        }
        val navButton3 = Anchor("skills-achievements", "Skills & Achievements").apply {
            addClassName("nav-button")
        }
        val navButton4 = Anchor("#projects", "My Projects").apply {
            addClassName("nav-button")
            element.setAttribute("data-target", "projects")
        }
        val navButton5 = Anchor("#contact", "Contact").apply {
            addClassName("nav-button")
            element.setAttribute("data-target", "contact")
        }

        return HorizontalLayout(navButton1, navButton2, navButton3, navButton4, navButton5).apply {
            addClassName("nav-buttons-container")
        }
    }
}
