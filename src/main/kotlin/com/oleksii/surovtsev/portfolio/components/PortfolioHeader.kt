package com.oleksii.surovtsev.portfolio.components

import com.oleksii.surovtsev.portfolio.util.UtilFileManager
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.dependency.JsModule
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout


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

        val navList = Div()
        navList.className = "nav-list"

        val listNavButton = listOf(
            Button("Home", Icon(VaadinIcon.HOME)).apply { setId(" ") },
            Button("Experience", Icon(VaadinIcon.USER)).apply { setId("experience") },
            Button("Projects", Icon(VaadinIcon.AUTOMATION)).apply { setId("projects") },
            Button("Blog", Icon(VaadinIcon.PENCIL)).apply { setId("blog") },
            Button("Contact", Icon(VaadinIcon.CONNECT)).apply { setId("contact") }
        )

        listNavButton.forEach { button ->
            button.addClassName("nav-button")
            button.isIconAfterText = true
            button.addClickListener {
                UI.getCurrent().navigate(button.id.get())
            }
            navList.add(button)
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