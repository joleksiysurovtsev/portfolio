package com.oleksii.surovtsev.portfolio.view.home

import com.oleksii.surovtsev.portfolio.entity.TechCardData
import com.oleksii.surovtsev.portfolio.entity.enums.TechCardType
import com.oleksii.surovtsev.portfolio.util.UtilFileManager
import com.oleksii.surovtsev.portfolio.view.home.element.TechCard
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout

class TechStackBlock: VerticalLayout() {
    init {
        addClassName("general-tech-stack-container")
        defaultHorizontalComponentAlignment = FlexComponent.Alignment.CENTER
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
            alignItems = FlexComponent.Alignment.CENTER
            style.set("flex-wrap", "wrap")
            isSpacing = true

            // We read JSON and create cards
            val techCards: List<TechCardData> = UtilFileManager.getDataFromJson("tech-stack.json")
            techCards.map { TechCard.fromData(it, TechCardType.SMALL) }.forEach { add(it) }
        }

        add(textLayout, techIconsLayout)
    }
}