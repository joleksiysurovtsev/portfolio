package com.oleksii.surovtsev.portfolio.view.experience

import com.flowingcode.vaadin.addons.carousel.Carousel
import com.flowingcode.vaadin.addons.carousel.Slide
import com.oleksii.surovtsev.portfolio.entity.Certification
import com.oleksii.surovtsev.portfolio.util.UtilFileManager
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.VerticalLayout

class CertificationSlider : VerticalLayout() {
    init {
        val certifications: List<Certification> = UtilFileManager.getDataFromJson("certification.json")

        val slides = certifications.map { certification ->
            Slide(
                VerticalLayout().apply {
                    alignItems = FlexComponent.Alignment.CENTER
                    justifyContentMode = FlexComponent.JustifyContentMode.START
                    isSpacing = true
                    isPadding = false
                    setWidthFull()

                    add(
                        Image(certification.imageUrl, certification.title).apply {
                            alignItems = FlexComponent.Alignment.CENTER
                            addClassName("slide-image")
                        }
                    )
                }
            )
        }

        alignItems = FlexComponent.Alignment.CENTER
        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        isSpacing = false
        isPadding = false
        setWidthFull()

        val carousel = Carousel(*slides.toTypedArray()).apply {
            addClassName("custom-carousel-style")
            setWidth("90%")
            setHeight("400px")
            isAutoProgress = true
        }

        add(carousel)
    }
}