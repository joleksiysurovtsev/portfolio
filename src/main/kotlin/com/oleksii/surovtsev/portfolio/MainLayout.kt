package com.oleksii.surovtsev.portfolio

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.RouterLayout
import org.springframework.beans.factory.annotation.Value

class MainLayout(
    @Value("\${web.link.github}")
    private val githubUrl: String,
    @Value("\${web.link.linkedin}")
    private val linkedinUrl: String,
    @Value("\${web.link.facebook}")
    private val facebookUrl: String,
) : VerticalLayout(), RouterLayout {

    init {
        width = "100%"
        height = "100vh"
        defaultHorizontalComponentAlignment = Alignment.STRETCH

        add(createHeader(), createContent(), createFooter())
    }

    private fun createHeader(): Header {
        val header = Header()

        // Logo on the left
        val logo = createLogo()

        // Navigation buttons in the center
        val navButtons = createNavButtons()
            .apply { justifyContentMode = FlexComponent.JustifyContentMode.CENTER }

        // The right theme switch
        val themeToggle = addThemeToggle()
            .apply { style.set("margin-left", "auto") }

        val horizontalLayout = HorizontalLayout(logo, navButtons, themeToggle).apply { alignItems = Alignment.CENTER }

        header.add(horizontalLayout)
        header.addClassName("my-header") //apply CSS styles
        return header
    }

    private fun createContent(): Main {
        val mainContent = Main()
        mainContent.style.set("flex", "1")
        mainContent.style.set("padding", "20px")
        mainContent.text = "Welcome to the main content area!"
        return mainContent
    }

    private fun createFooter(): Footer {
        val footer = Footer()
        footer.text = "Footer © 2025"
        footer.style.set("background", "#f1f1f1")
        footer.style.set("padding", "10px")
        footer.style.set("text-align", "center")
        return footer
    }

    private fun createNavButtons(): HorizontalLayout {
        val navButton1 = Anchor("/home", "Home").apply {
            style.set("margin-right", "10px")
            style.set("color", "#ffffff")
            style.set("text-decoration", "none")
        }
        val navButton2 = Anchor("/about", "About").apply {
            style.set("margin-right", "10px")
            style.set("color", "#ffffff")
            style.set("text-decoration", "none")
        }
        val navButton3 = Anchor("/tech-stack", "Tech Stack").apply {
            style.set("margin-right", "10px")
            style.set("color", "#ffffff")
            style.set("text-decoration", "none")
        }
        val navButton4 = Anchor("/projects", "My Projects").apply {
            style.set("margin-right", "10px")
            style.set("color", "#ffffff")
            style.set("text-decoration", "none")
        }
        val navButton5 = Anchor("/contact", "Contact").apply {
            style.set("color", "#ffffff")
            style.set("text-decoration", "none")
        }

        return HorizontalLayout(navButton1, navButton2, navButton3, navButton4, navButton5).apply {
            alignItems = Alignment.CENTER
            isSpacing = true
            isPadding = false
            style.set("margin", "0 auto")
        }
    }

    private fun createLogo(): Image {
        return Image("icons/my_logo.svg", "Logo").apply {
            style.set("height", "50px")
            style.set("width", "auto")
            style.set("cursor", "pointer")
        }
    }

    private fun createHeaderLayout(logo: Image, centerBlock: HorizontalLayout): HorizontalLayout {
        return HorizontalLayout(logo, centerBlock).apply {
            width = "100%" // Full page width
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER // Content alignment
            alignItems = Alignment.CENTER // vertical alignment
//            style.set("padding", "0 12.5%") // Equal indentation (25% of the page width on left and right)
            addClassName("my-header") // Apply class
        }
    }

    private fun createSocialLinksIconBlock(): HorizontalLayout {
        val githubIcon = createClickableImage("icons/github_mark.svg", githubUrl)
        val linkedInIcon = createClickableImage("icons/linkedIn_icon.svg", linkedinUrl)
        val facebookIcon = createClickableImage("icons/facebook_icon.svg", facebookUrl)

        // Combine them into a horizontal block
        val iconBlock: HorizontalLayout = HorizontalLayout(githubIcon, linkedInIcon, facebookIcon)
        iconBlock.setSpacing(true) // Add indents between icons
        return HorizontalLayout(iconBlock)
    }

    private fun createClickableImage(imagePath: String, url: String): Anchor {
        val image = Image(imagePath, "Custom Icon")
        image.style.set("cursor", "pointer") // Cursor Styling
        image.style.set("width", "24px") // image width
        image.style.set("height", "24px") // image height

        val link = Anchor(url, image)
        link.setTarget("_blank") // Open in new tab
        return link
    }


    private fun addThemeToggle(): Button {
        return Button("Switch to Dark Theme").apply {
            addClickListener {
                val isDarkTheme = ui.get().element.getAttribute("theme")?.contains("dark") ?: false
                if (isDarkTheme) {
                    ui.get().element.setAttribute("theme", "mytheme")
                    text = "Switch to Dark Theme"
                } else {
                    ui.get().element.setAttribute("theme", "mytheme dark")
                    text = "Switch to Light Theme"
                }
            }
            style.set("margin-left", "auto")
            style.set("padding", "5px 15px")
            style.set("cursor", "pointer")
        }
    }
}
