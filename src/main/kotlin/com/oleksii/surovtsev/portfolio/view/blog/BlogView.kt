package com.oleksii.surovtsev.portfolio.view.blog

import com.oleksii.surovtsev.portfolio.layout.MainLayout
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route

@Route(value = "/blog", layout = MainLayout::class)
class BlogView : VerticalLayout() {

    init {
        setWidthFull()
        justifyContentMode = FlexComponent.JustifyContentMode.START
        alignItems = FlexComponent.Alignment.CENTER
        addClassName("experience")
        add(
            H1("Not implemented yet")
        )
    }
}