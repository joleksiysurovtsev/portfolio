package com.oleksii.surovtsev.portfolio.view.experience

import com.flowingcode.vaadin.addons.carousel.Carousel
import com.flowingcode.vaadin.addons.carousel.Slide
import com.oleksii.surovtsev.portfolio.entity.Certification
import com.oleksii.surovtsev.portfolio.util.UtilFileManager
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route

@Route("certification-slider")
class CertificationSliderBlock : VerticalLayout() {

    init {
        add(H2("My certificates").apply {
            style.set("text-align", "left") // Выровнять текст по левому краю
            style.set("width", "100%") // Растянуть заголовок на всю ширину
        })
        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        alignItems = FlexComponent.Alignment.CENTER
        style.set("padding", "20px")
        setSizeFull()
        isSpacing = false
        isPadding = false

        val certifications: List<Certification> = UtilFileManager.getDataFromJson("certification.json")
        val slides = certifications.map { createSlide(it) }

        val carousel = Carousel(*slides.toTypedArray()).apply {
            addClassName("custom-carousel-style")
            setWidth("80%") // Устанавливаем ширину карусели, чтобы она была по центру
            setHeight("400px")
            setStartPosition(0) // Начальная позиция
            isAutoProgress = true
            style.set("margin", "0 auto") // Центрируем карусель на странице
        }

        add(carousel)
    }

    private fun createSlide(certification: Certification): Slide {
        return Slide(
            HorizontalLayout().apply {
                justifyContentMode = FlexComponent.JustifyContentMode.CENTER
                alignItems = FlexComponent.Alignment.CENTER
                isSpacing = true
                isPadding = true
                isWrap = true
//                flexWrap = FlexLayout.FlexWrap.WRAP
                setWidthFull()
                setHeight("100%")

                add(
                    Image(certification.imageUrl, certification.title).apply {
                        className = "slide-image"
                        style.set("width", "auto")
                        style.set("height", "300px")
                        style.set("max-height", "300px")
                        style.set("border-radius", "10px")
                        style.set("object-fit", "contain")
                    }
                )

                add(
                    VerticalLayout().apply {
                        className = "slide-text"
                        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
                        alignItems = FlexComponent.Alignment.START
                        isSpacing = true
                        isPadding = true
                        style.set("text-align", "left")
                        setWidth("30%")

                        add(Div().apply {
                            text = certification.title
                            style.set("font-size", "24px")
                            style.set("font-weight", "bold")
                            style.set("margin-bottom", "10px")
                            style.set("white-space", "normal")
                        })

                        add(Div().apply {
                            text = certification.description
                            style.set("font-size", "16px")
                            style.set("color", "gray")
                            style.set("white-space", "normal")
                            style.set("word-wrap", "break-word")
                        })
                    }
                )
            }
        )
    }
}