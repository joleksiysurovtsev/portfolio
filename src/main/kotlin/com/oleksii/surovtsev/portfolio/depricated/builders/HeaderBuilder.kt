package com.oleksii.surovtsev.portfolio.depricated.builders

import com.oleksii.surovtsev.portfolio.depricated.utils.NavButtonsBuilder
import com.oleksii.surovtsev.portfolio.depricated.utils.SocialLinksIconBlockBuilder
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout

class HeaderBuilder {

    fun createHeader(): VerticalLayout {
        val header = VerticalLayout().apply {
            width = "100%"
            addClassName("my-header")
        }

        // navigation buttons
        val navButtons: HorizontalLayout = NavButtonsBuilder.createNavButtons().apply {
            width = "100%" // Cover the entire width of the parent
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        }

//        val socialLinks: HorizontalLayout = SocialLinksIconBlockBuilder.createSocialLinksIconBlock().apply {
//            justifyContentMode = FlexComponent.JustifyContentMode.END
//        }

        val themeToggle: Button = addThemeToggle().apply {
            addClassName("toggle-styles")
        }

        val horizontalLayout = HorizontalLayout(navButtons, themeToggle, /*socialLinks*/).apply {
            alignItems = Alignment.CENTER
            width = "100%"
            setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN)
        }

        header.add(horizontalLayout)
        return header
    }

    private fun createLogo(): Image {
        return Image("icons/my_logo.svg", "Logo").apply {
            style.set("height", "50px")
            style.set("width", "auto")
            style.set("cursor", "pointer")
        }
    }




    private fun addThemeToggle(): Button {
        return Button().apply {
            element.setProperty("innerHTML", loadSvg("sun-icon.svg"))

            addClickListener {
                ui.get().page.executeJs(
                    """
                const root = document.documentElement;
                const currentTheme = root.getAttribute('theme') || 'light';
                const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
                root.setAttribute('theme', newTheme);
                localStorage.setItem('theme', newTheme);
            """
                )

                ui.get().page.executeJs(
                    """ 
                    return document.documentElement.getAttribute('theme');
                    """.trimIndent()
                ).then {
                    val newTheme = it.asString()
                    val newIcon = if (newTheme == "dark") "sun-icon.svg" else "moon-icon.svg"
                    element.setProperty("innerHTML", loadSvg(newIcon))
                }

                // Animation
                addClassName("animate-rotate")
                element.addEventListener("animationend") {
                    removeClassName("animate-rotate")
                }
            }
        }
    }

    private fun loadSvg(fileName: String): String {
        val resource = javaClass.getResource("/static/icons/$fileName")
            ?: throw IllegalArgumentException("Icon file not found: $fileName")
        return resource.readText()
    }

}