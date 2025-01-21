package com.oleksii.surovtsev.portfolio.view

import com.oleksii.surovtsev.portfolio.components.AboutMeBlock
import com.oleksii.surovtsev.portfolio.components.CurrentTechStackBlock
import com.oleksii.surovtsev.portfolio.components.TechCard
import com.oleksii.surovtsev.portfolio.entity.TechCardType
import com.oleksii.surovtsev.portfolio.layout.MainLayout
import com.oleksii.surovtsev.portfolio.util.UtilFileManager
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route


@Route(value = "", layout = MainLayout::class)
class HomeView : VerticalLayout() {

    init {
        setWidthFull()
        justifyContentMode = FlexComponent.JustifyContentMode.START
        alignItems = Alignment.CENTER
        style.set("padding", "20px")

        add(
            AboutMeBlock(),
            CurrentTechStackBlock(),
            getTechStackLayout()
        )
    }

    private fun getTechStackLayout(): VerticalLayout {
        return VerticalLayout().apply {
            addClassName("general-tech-stack-container")
            defaultHorizontalComponentAlignment = Alignment.CENTER
            element.setAttribute("id", "tech-stack") // Setting ID for Tech Stack section

            val textLayout = Div().apply {
                className = "general-tech-stack-text"
                val title = H1("Additional Experience")
                val description = Div().apply {
                    text = "Technologies I’ve had hands-on experience with:"
                }
                add(title, description)
            }

            val techIconsLayout = HorizontalLayout().apply {
                justifyContentMode = FlexComponent.JustifyContentMode.CENTER
                alignItems = Alignment.CENTER
                style.set("flex-wrap", "wrap")
                isSpacing = true

                // We read JSON and create cards
                val techCards = UtilFileManager.getTechStackFromJson("tech-stack.json")
                techCards.map { TechCard.fromData(it, TechCardType.SMALL) }.forEach { add(it) }
            }

            add(textLayout, techIconsLayout)
        }
    }
}


