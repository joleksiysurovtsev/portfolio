package com.oleksii.surovtsev.portfolio.components

import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import org.slf4j.LoggerFactory

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

    private val logger = LoggerFactory.getLogger(LinkButton::class.java)

    init {
        addClassName(style)
        text = buttonText
        val lottieIcon = LottieComponent(animationPath, animationAutoplay, animationLoop, "${iconSize.first}rem", "${iconSize.second}rem")
        setIcon(lottieIcon)
        isIconAfterText = positionIconAfterText

        url?.let { safeUrl ->
            addClickListener {
                try {
                    UI.getCurrent().page.open(safeUrl, "_blank")
                } catch (e: Exception) {
                    logger.error("Failed to open URL: {}", safeUrl, e)
                }
            }
        }
    }
}