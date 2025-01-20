package com.oleksii.surovtsev.portfolio.components

import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class PortfolioHeader : Header() {

    init {

        className = "l-header"
        val nav = Nav()
        nav.className = "nav bd-grid"

        val logoDiv = HorizontalLayout().apply {
            alignItems = FlexComponent.Alignment.CENTER
            val logoLink = Anchor("#", "Oleksii").apply {
                className = "nav-logo"
            }
            add(SwitchThemeButton(), logoLink)
        }


        val menuDiv = Div()
        menuDiv.setId("nav-menu")
        menuDiv.className = "nav-menu"

        val navList = UnorderedList()
        navList.className = "nav-list"

        val menuItems = listOf("Home", "About", "Stack", "Skills", "Work", "Contact")
        menuItems.forEach { item ->
            val listItem = ListItem()
            listItem.className = "nav-item"

            val link = Anchor("#${item.lowercase()}", item)
            link.className = "nav-link"
            if (item == "Home") link.addClassName("active")

            listItem.add(link)
            navList.add(listItem)
        }
        menuDiv.add(navList)

        val toggleDiv = Div().apply {
            setId("nav-toggle")
            addClassName("nav-toggle")
        }
        val toggleIcon: Span = Span().apply { className = "bx bx-menu" }
        toggleDiv.add(toggleIcon)



        nav.add(logoDiv, menuDiv, toggleDiv)
        add(nav)
    }
}