package com.oleksii.surovtsev.portfolio.depricated.utils

import com.vaadin.flow.component.html.Anchor
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

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