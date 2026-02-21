package com.oleksii.surovtsev.portfolio.view.home

import com.oleksii.surovtsev.portfolio.components.CustomDivider
import com.oleksii.surovtsev.portfolio.components.CustomDividerH2
import com.oleksii.surovtsev.portfolio.components.ScrollButton
import com.oleksii.surovtsev.portfolio.config.RoutesConfig
import com.oleksii.surovtsev.portfolio.layout.MainLayout
import com.oleksii.surovtsev.portfolio.service.ResourceLoaderService
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route


@Route(value = RoutesConfig.Main.HOME, layout = MainLayout::class)
class HomeView(
    private val resourceLoaderService: ResourceLoaderService
) : VerticalLayout() {

    init {
        setWidthFull()
        justifyContentMode = FlexComponent.JustifyContentMode.START
        alignItems = Alignment.CENTER
        addClassName("home-view")
        add(
            CustomDividerH2("ABOUT ME"),
            AboutMeBlock(resourceLoaderService),
            CustomDivider(),
            ScrollLayount(),
            CurrentTechStackBlock(resourceLoaderService),
            CustomDivider(),
            TechStackBlock(resourceLoaderService)
        )
    }
}

class ScrollLayount : FlexLayout() {
    init {
        setWidthFull()
        justifyContentMode = FlexComponent.JustifyContentMode.START
        addClassName("scroll-container")
        add(ScrollButton())
    }
}

