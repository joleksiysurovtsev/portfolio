package com.oleksii.surovtsev.portfolio.view.experience

import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.VerticalLayout

class CareerGoalsAndPhilosophyBlock : VerticalLayout() {
    init {
        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        alignItems = FlexComponent.Alignment.START
        style.set("padding", "20px")
        setWidth("100%")
        isSpacing = true
        isPadding = true

        add(H2("Career Goals and Philosophy").apply {
            style.set("font-size", "28px")
            style.set("font-weight", "bold")
            style.set("margin-bottom", "20px")
        })

        add(H3("My Goals:").apply {
            style.set("font-size", "22px")
            style.set("font-weight", "bold")
            style.set("margin-bottom", "10px")
        })

        add(
            VerticalLayout().apply {
                isSpacing = true
                isPadding = false
                style.set("margin-left", "20px")

                add(Paragraph("• Master new professional certifications, including AWS and other cloud technologies.").apply {
                    style.set("margin", "0 0 5px 0")
                })
                add(Paragraph("• Develop public speaking skills and improve English communication.").apply {
                    style.set("margin", "0 0 5px 0")
                })
                add(Paragraph("• Continue creating solutions that simplify users' lives and help businesses grow.").apply {
                    style.set("margin", "0 0 5px 0")
                })
            }
        )

        add(H3("Philosophy:").apply {
            style.set("font-size", "22px")
            style.set("font-weight", "bold")
            style.set("margin-top", "20px")
            style.set("margin-bottom", "10px")
        })

        add(
            Paragraph(
                """
                I believe that a successful developer is not just a master of technical solutions but also someone 
                who can work in a team, understand the needs of businesses and users. My goal is to seamlessly 
                connect technology and business, creating elegant and reliable products.
                """.trimIndent()
            ).apply {
                style.set("line-height", "1.5")
                style.set("color", "gray")
                style.set("margin", "0 20px 0 20px")
            }
        )
    }
}