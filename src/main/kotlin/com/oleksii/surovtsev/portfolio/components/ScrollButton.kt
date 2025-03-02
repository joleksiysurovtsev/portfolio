package com.oleksii.surovtsev.portfolio.components

import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button

class ScrollButton : Button() {
    init {
        addClassName("scroll-button")
        text = "Scroll For More"

        val lottieIcon = LottieComponent("lottie/scroll-down-dark2.lottie", true, true, "3rem", "3rem")
        setIcon(lottieIcon)
        isIconAfterText = true

        addClickListener {
            UI.getCurrent().page.executeJs("window.scrollBy({ top: window.innerHeight, behavior: 'smooth' });")
        }
    }
}