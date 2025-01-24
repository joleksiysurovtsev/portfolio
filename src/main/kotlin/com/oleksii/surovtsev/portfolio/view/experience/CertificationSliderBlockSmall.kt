package com.oleksii.surovtsev.portfolio.view.experience

import com.flowingcode.vaadin.addons.carousel.Carousel
import com.flowingcode.vaadin.addons.carousel.Slide
import com.oleksii.surovtsev.portfolio.entity.Certification
import com.oleksii.surovtsev.portfolio.util.UtilFileManager
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout

class CertificationSliderBlockSmall : VerticalLayout() {

    init {
        // Заголовок
        addClassName("skills-section")
        add(H2("My certificates").apply {
            style.set("text-align", "left") // Выровнять текст по левому краю
//            style.set("width", "100%") // Растянуть заголовок на всю ширину
        })

        // Устанавливаем базовые настройки для контейнера
        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        alignItems = FlexComponent.Alignment.CENTER

        style.set("padding", "20px")
        isSpacing = false
        isPadding = false

        // Получаем данные из JSON
        val certifications: List<Certification> = UtilFileManager.getDataFromJson("certification.json")
        val slides = certifications.map { createSlide(it) }

        // Создаем карусель без фиксированных размеров
        val carousel = Carousel(*slides.toTypedArray()).apply {
            addClassName("custom-carousel-style")
            isAutoProgress = true
            style.set("margin", "0 auto") // Центрируем карусель
        }

        // Добавляем карусель в макет
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
                // Не задаем фиксированные размеры, чтобы слайд подстраивался под родительский контейнер

                add(
                    Image(certification.imageUrl, certification.title).apply {
                        className = "slide-image"
                        style.set("max-height", "400px") // Ограничиваем только максимальную высоту изображения
                        style.set("max-weight", "500px") // Ограничиваем только максимальную высоту изображения
                        style.set("border-radius", "10px")
                        style.set("object-fit", "contain")
                    }
                )
            }
        )
    }
}