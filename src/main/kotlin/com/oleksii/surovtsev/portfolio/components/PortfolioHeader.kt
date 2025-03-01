package com.oleksii.surovtsev.portfolio.components

import com.oleksii.surovtsev.portfolio.util.UtilFileManager
import com.vaadin.flow.component.Html
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
        addClassName("header")
        val nav = Navigation()

        val menuDiv = Div().apply {
            setId("nav-menu")
            addClassName("nav-menu")
            add(NavList())
        }

        val toggleDiv = Div().apply {
            setId("nav-toggle")
            addClassName("nav-toggle")
            val toggleIcon: Span = Span().apply { className = "bx bx-menu" }
            add(toggleIcon)
        }

        nav.add(HeaderLogoDiv(), menuDiv, toggleDiv)
        add(nav)
    }
}

class NavList : Div() {
    init {
        className = "nav-list"
        add(getNavButtons())
    }

    fun getNavButtons(): List<Button> {
        val buttons = listOf(
            Button("Home", Icon(VaadinIcon.HOME)).apply { setId(" ") },
            Button("Experience", Icon(VaadinIcon.USER)).apply { setId("experience") },
            Button("Projects", Icon(VaadinIcon.AUTOMATION)).apply { setId("projects") },
//            Button("Blog", Icon(VaadinIcon.PENCIL)).apply { setId("blog") },
            Button("Contact", Icon(VaadinIcon.CONNECT)).apply { setId("contact") }
        )

        buttons.forEach { button ->
            button.apply {
                addClassName("nav-button")
                isIconAfterText = true
                addClickListener {
                    UI.getCurrent().navigate(button.id.get())
                    UI.getCurrent().page.executeJs(
                        "document.getElementById('nav-menu').classList.remove('show')"
                    )
                }
            }
        }
        return buttons
    }
}

class Navigation : Nav() {
    init {
        className = "nav bd-grid"
    }
}

class HeaderLogoDiv : HorizontalLayout() {
    init {
        alignItems = FlexComponent.Alignment.CENTER
        val svgContent = UtilFileManager.loadSvg("sur.svg")
        val logoSvg = Html("<div class='logo-icon'>$svgContent</div>")
        add(SwitchThemeButton(), HeaderAnchor())
    }
}

class HeaderAnchor : Anchor() {
    init {
        addClassName("header-logo")
        href = "#"
        text = "Oleksii"
    }
}


@JsModule("./elements/switch-theme-toggle.js")
class SwitchThemeButton : Div() {
    init {
        addClassName("theme-button-container")
        val button = Button().apply {
            addClassName("sun-moon")
            element.setProperty("innerHTML", UtilFileManager.loadSvg("sun-icon.svg"))
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
        add(button)
    }
}