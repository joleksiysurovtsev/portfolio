package com.oleksii.surovtsev.portfolio.components

import com.oleksii.surovtsev.portfolio.entity.FooterLink
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

        val copyright = CopywriterDisclaimer()
        val footerLinks = FooterLinks()
        val socialButtons = SocialButtonsLayout()

        val footerContent = HorizontalLayout(
            VerticalLayout(copyright, footerLinks).apply {
                addClassName("footer-center-content")
                setAlignItems(FlexComponent.Alignment.CENTER)
            },
            socialButtons
        ).apply {
            addClassName("footer-content")
            setWidthFull()
            isSpacing = true
        }

        add(footerContent)
    }
}

class CopywriterDisclaimer : Div() {
    init {
        addClassName("footer-copyright-disclaimer")
        add(Paragraph("© 2025 Oleksii Surovtsev. All Rights Reserved."))
    }
}

class FooterLinks : Div() {
    init {
        addClassName("footer-links-div")
        val links = UtilFileManager.getDataFromJson<FooterLink>("footer-links.json").map {
            Anchor(it.href, it.text).apply { addClassName("footer-link") }
        }
        add(links)
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