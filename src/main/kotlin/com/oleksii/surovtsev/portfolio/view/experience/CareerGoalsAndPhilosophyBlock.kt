package com.oleksii.surovtsev.portfolio.view.experience

import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.orderedlayout.VerticalLayout

class CareerGoalsAndPhilosophyBlock : VerticalLayout() {
    init {
        addClassName("career-block")

        add(H2("Career Goals and Philosophy"))

        add(H3("My Goals:"))

        add(
            VerticalLayout().apply {
                addClassName("goals-list")

                add(Paragraph("• Master new professional certifications, including AWS and other cloud technologies."))
                add(Paragraph("• Develop public speaking skills and improve English communication."))
                add(Paragraph("• Continue creating solutions that simplify users' lives and help businesses grow."))
            }
        )

        add(H3("Philosophy:"))

        add(
            Paragraph(
                """
                I believe that a successful developer is not just a master of technical solutions but also someone 
                who can work in a team, understand the needs of businesses and users. My goal is to seamlessly 
                connect technology and business, creating elegant and reliable products.
                """.trimIndent()
            ).apply {
                addClassName("philosophy-paragraph")
            }
        )
    }
}