package com.oleksii.surovtsev.portfolio.components

import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button

class LinkButton(
    animationPath: String,
    style: String = "",
    buttonText: String = "",
    iconSize: Pair<Double, Double> = Pair(2.0, 2.0),
    url: String? = null,
    animationAutoplay: Boolean = true,
    animationLoop: Boolean = true,
    positionIconAfterText: Boolean = true,
) : Button() {
    init {
        addClassName(style)
        text = buttonText
        val lottieIcon = LottieComponent(animationPath, animationAutoplay, animationLoop, "${iconSize.first}rem", "${iconSize.second}rem")
        setIcon(lottieIcon)
        isIconAfterText = positionIconAfterText

        addClickListener {
            UI.getCurrent().page.executeJs("window.open('$url', '_blank')")
        }
    }
}