package com.oleksii.surovtsev.portfolio.view.home

import com.oleksii.surovtsev.portfolio.components.CustomDivider
import com.oleksii.surovtsev.portfolio.layout.MainLayout
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
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
            CustomDivider(),
            CurrentTechStackBlock(),
            CustomDivider(),
            TechStackBlock()
        )
    }
}
