package com.oleksii.surovtsev.portfolio.view.experience

import com.flowingcode.vaadin.addons.carousel.Carousel
import com.flowingcode.vaadin.addons.carousel.Slide
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.notification.Notification.Position
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.VerticalLayout


class CertificationSliderBlock : VerticalLayout() {

    init {
        justifyContentMode = FlexComponent.JustifyContentMode.START
        alignItems = Alignment.CENTER
        style.set("padding", "20px")
        isSpacing = true
        isPadding = true
        val s1: Slide =
            Slide(
                Div(
                    "Slide 1",
                )
            )
        val s2: Slide =
            Slide(
                Div(
                    "Slide 2",
                )
            )
        val s3: Slide =
            Slide(
                Div(
                    "Slide 3",
                )
            )
        val s4: Slide =
            Slide(
                Div(
                    "Slide 4",
                )
            )

        val c: Carousel = Carousel(s1, s2, s3, s4)
        c.setSizeFull()
        c.addChangeListener { e ->
            Notification.show(
                "Slide Changed!",
                1000,
                Position.BOTTOM_START
            )
        }

        add(c)
    }
}
