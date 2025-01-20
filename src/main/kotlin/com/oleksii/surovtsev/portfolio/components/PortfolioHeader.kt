package com.oleksii.surovtsev.portfolio.components

import com.vaadin.flow.component.dependency.JsModule
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.oleksii.surovtsev.portfolio.util.UtilFileManager
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.Div


@JsModule("./elements/menu-toggle.js")
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



@JsModule("./elements/switch-theme-toggle.js")
class SwitchThemeButton : Div() {

    init {
        val button =  Button().apply {
            addClassName("sun-moon")
            element.setProperty("innerHTML", UtilFileManager.loadSvg("sun-icon.svg"))
            element.style.set("flex-wrap", "nowrap")
            addClickListener {
                ui.get().page.executeJs("return window.toggleTheme()").then { result ->
                    val newTheme = result.asString()
                    val newIcon = if (newTheme == "dark") "sun-icon.svg" else "moon-icon.svg"
                    element.setProperty("innerHTML", UtilFileManager.loadSvg(newIcon))
                }

                // Animation
                addClassName("animate")
                element.addEventListener("animationend") {
                    removeClassName("animate")
                }
            }
        }

        // We place the button in the container
        val layout = Div(button).apply {
            setWidthFull()
            element.style.set("flex-wrap", "nowrap") // We prohibit the transfer of elements
        }

        add(layout)
    }

}