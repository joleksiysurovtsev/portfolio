package com.oleksii.surovtsev.portfolio.view.home

import com.oleksii.surovtsev.portfolio.components.CustomDivider
import com.oleksii.surovtsev.portfolio.components.CustomDividerH2
import com.oleksii.surovtsev.portfolio.components.LottieComponent
import com.oleksii.surovtsev.portfolio.layout.MainLayout
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
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
        addClassName("home-view")
        add(
            CustomDividerH2("ABOUT ME"),
            AboutMeBlock(),
            CustomDivider(),
            ScrollButton(),
            CurrentTechStackBlock(),
            CustomDivider(),
            TechStackBlock()
        )
    }
}

class ScrollButton : Button() {
    init {
        addClassName("scroll-button")
        setWidthFull()
        text = "Scroll For More"

        val lottieIcon = LottieComponent("lottie/scroll-down-dark2.lottie", true, true, "3rem", "3rem")
        setIcon(lottieIcon)
        isIconAfterText = true

        addClickListener {
            UI.getCurrent().page.executeJs("window.scrollBy({ top: window.innerHeight, behavior: 'smooth' });")
        }
    }
}