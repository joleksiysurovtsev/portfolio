package com.oleksii.surovtsev.portfolio.components

import com.oleksii.surovtsev.portfolio.util.UtilFileManager
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.Div

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