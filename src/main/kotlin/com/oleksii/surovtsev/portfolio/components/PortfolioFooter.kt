package com.oleksii.surovtsev.portfolio.components

import com.oleksii.surovtsev.portfolio.entity.SocialIcon
import com.oleksii.surovtsev.portfolio.util.UtilFileManager
import com.vaadin.flow.component.dependency.JsModule
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.server.VaadinSession

class PortfolioFooter : Footer() {
    init {
        addClassName("footer")

        val vaadinLogo = VaadinLogo()
        val copyright = CopywriterDisclaimer()
        val portfolioDescription = PortfolioDescription()
        val socialButtons = SocialButtonsLayout()

        val footerContent = HorizontalLayout(
            socialButtons,
            VerticalLayout( portfolioDescription, copyright).apply {
                addClassName("footer-center-content")
                setAlignItems(FlexComponent.Alignment.CENTER)
            },
            vaadinLogo
        ).apply {
            addClassName("footer-content")
            setWidthFull()
            isSpacing = true
            isPadding = false
        }

        add(footerContent)
    }
}

class VaadinLogo : HorizontalLayout() {
    init {
        addClassName("footer-logo")
        val logoImage = Image("icons/vaadin-logo.svg", "Vaadin Logo")
        val logoImage2 = Image("icons/tech/spring.svg", "Spring Logo")
        val logoImage3 = Image("icons/tech/kotlin.svg", "Kotlin Logo")
        isSpacing = true
        add(logoImage, logoImage2, logoImage3)
    }
}

class PortfolioDescription : Div() {
    init {
        addClassName("footer-description")

        val description = Span(
            "This project showcases my personal portfolio, built using " +
                    "modern technologies such as "
        ).apply {
            addClassName("footer-text")
        }

        val techStack = Span("Spring Boot, Kotlin, and Vaadin.").apply {
            addClassName("footer-highlight")
        }

        val learningNote = Span(
            " The main goal of this project is to explore the Vaadin framework " +
                    "while developing an interactive and efficient UI."
        ).apply {
            addClassName("footer-text")
        }

        add( description, techStack, learningNote)
    }
}

@JsModule("./elements/social-buttons.js")
class SocialButtonsLayout : Div() {
    private val buttonsDiv: Div = Div()

    init {
        add(SvgFilter())
        addClassName("social-buttons-container")

        buttonsDiv.addClassName("buttons")

        val openButton = Div()
        openButton.addClassName("open-btn")
        val openButtonImage = Image("icons/social/share.svg", "Share")
        openButtonImage.addClassName("social-icon")
        openButton.add(openButtonImage)

        val socialIcons = UtilFileManager.getDataFromJson<SocialIcon>("social-icons.json")
        socialIcons.forEachIndexed { index, socialIcon ->
            val socialButton = Div()
            socialButton.addClassNames("social-btn", "social-btn-${index + 1}")

            val socialImage = Image(socialIcon.imagePath, socialIcon.name)
            socialImage.addClassName("social-icon")

            val anchor = Anchor(socialIcon.url, socialImage)
            anchor.setTarget("_blank")

            socialButton.add(anchor)
            buttonsDiv.add(socialButton)
        }

        buttonsDiv.add(openButton)
        add(buttonsDiv)

        //initialize JS after rendering
        VaadinSession.getCurrent().access {
            element.executeJs("window.initializeSocialButtons($0)", buttonsDiv.element)
        }
    }

    class SvgFilter : Div() {
        init {
            element.setProperty(
                "innerHTML", """
            <svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="0" height="0">
                <defs>
                    <filter id="goo">
                        <feGaussianBlur in="SourceGraphic" stdDeviation="10" result="blur"/>
                        <feColorMatrix in="blur" mode="matrix" values="1 0 0 0 0  0 1 0 0 0  0 0 1 0 0  0 0 0 18 -7" result="goo"/>
                        <feBlend in="SourceGraphic" in2="goo"/>
                    </filter>
                </defs>
            </svg>
        """.trimIndent()
            )
        }
    }
}

class CopywriterDisclaimer : Div() {
    init {
        addClassName("footer-copyright-disclaimer")
        add(Paragraph("© 2025 Oleksii Surovtsev. All Rights Reserved."))
    }
}