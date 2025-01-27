package com.oleksii.surovtsev.portfolio.view.home

import com.oleksii.surovtsev.portfolio.entity.TechCardData
import com.oleksii.surovtsev.portfolio.entity.enums.TechCardType
import com.oleksii.surovtsev.portfolio.util.UtilFileManager
import com.oleksii.surovtsev.portfolio.view.home.element.TechCard
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout

class CurrentTechStackBlock : VerticalLayout() {

    init {
        addClassName("general-tech-stack-container")
        defaultHorizontalComponentAlignment = Alignment.CENTER
        element.setAttribute("id", "tech-stack") // Setting ID for Tech Stack section

        val textLayout = Div().apply {
            className = "general-tech-stack-text"
            val title = H1("My Current Tech Stack").apply {
                className = "general-tech-stack h1"
            }
            val description = Div().apply {
                text = "Technologies I’ve been working with recently:"
                className = "general-tech-stack-text div"
            }
            add(title, description)
        }

        val techIconsLayout = HorizontalLayout().apply {
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            alignItems = Alignment.CENTER
            style.set("flex-wrap", "wrap")
            isSpacing = true

            val techCards: List<TechCardData> = UtilFileManager.getDataFromJson("main-tech-stack.json")
            techCards.map { TechCard.fromData(it, TechCardType.EXTENDED) }.forEach {
                add(it)
            }
        }

        add(textLayout, techIconsLayout)
    }
}