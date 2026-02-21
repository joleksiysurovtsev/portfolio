package com.oleksii.surovtsev.portfolio.view.home.element

import com.oleksii.surovtsev.portfolio.entity.TechCardData
import com.oleksii.surovtsev.portfolio.entity.enums.TechCardType
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.dependency.JsModule
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.html.Span

@JsModule("./elements/card-tech.js")
class TechCard : Div {

    private lateinit var config: CardConfig
    private var expandedStateManager: ExpandedStateManager? = null

    // Constructor for SMALL cards
    constructor(iconPath: String, techName: String) : super() {
        config = CardConfig(iconPath, techName, TechCardType.SMALL)
        expandedStateManager = null
        buildSmallCard()
    }

    // Constructor for EXTENDED cards
    constructor(iconPath: String, techName: String, description: String, link: String) : super() {
        config = CardConfig(iconPath, techName, TechCardType.EXTENDED, description, link)
        expandedStateManager = ExpandedStateManager(this)
        buildExtendedCard()
    }

    private fun buildSmallCard() {
        addClassName(config.cardType.basicStyle)
        add(SmallCardBuilder(config).build())
        initializeJavaScript()
    }

    private fun buildExtendedCard() {
        addClassName(config.cardType.basicStyle)
        val builder = ExtendedCardBuilder(config, expandedStateManager!!)
        add(builder.build())
        setupMouseLeaveHandler()
        initializeJavaScript()
    }

    private fun setupMouseLeaveHandler() {
        element.addEventListener("mouseleave") {
            expandedStateManager?.collapse()
        }
    }

    private fun initializeJavaScript() {
        element.executeJs("window.Vaadin.techtField.initTechCard($0)", element)
    }

    companion object {
        fun fromData(data: TechCardData, techCardType: TechCardType): TechCard {
            return if (techCardType == TechCardType.EXTENDED) {
                TechCard(data.cardIcon, data.cardName, data.description!!, data.link!!)
            } else {
                TechCard(data.cardIcon, data.cardName)
            }
        }
    }
}

// Configuration holder
private data class CardConfig(
    val iconPath: String,
    val techName: String,
    val cardType: TechCardType,
    val description: String? = null,
    val link: String? = null
)

// Manages expanded/collapsed state for extended cards
private class ExpandedStateManager(private val card: Div) {
    private var descriptionElement: Div? = null
    private var toggleButton: Button? = null
    private var isExpanded: Boolean = false

    fun setElements(description: Div, button: Button) {
        descriptionElement = description
        toggleButton = button
    }

    fun toggle() {
        isExpanded = !isExpanded
        updateState()
    }

    fun collapse() {
        if (isExpanded) {
            isExpanded = false
            updateState()
        }
    }

    private fun updateState() {
        if (isExpanded) {
            expandDescription()
        } else {
            collapseDescription()
        }
    }

    private fun expandDescription() {
        descriptionElement?.apply {
            removeClassName("truncated")
            style.set("max-height", "var(--description-expanded-height)")
        }
        card.addClassName("expanded")
        toggleButton?.text = "Show less"
    }

    private fun collapseDescription() {
        card.removeClassName("expanded")
        descriptionElement?.apply {
            addClassName("truncated")
            style.set("max-height", "var(--description-max-height)")
        }
        toggleButton?.text = "Show more"
    }
}

// Builds small card UI
private class SmallCardBuilder(private val config: CardConfig) {
    fun build(): Div {
        val icon = createIcon()
        return Div().apply {
            addClassName(config.cardType.contentStyle)
            add(icon, Span(config.techName))
        }
    }

    private fun createIcon() = Image(config.iconPath, config.techName)
}

// Builds extended card UI
private class ExtendedCardBuilder(
    private val config: CardConfig,
    private val stateManager: ExpandedStateManager
) {
    fun build(): Div {
        val icon = createIcon()
        val textContainer = createTextContainer()

        return Div().apply {
            addClassName(config.cardType.contentStyle)
            style.set("display", "flex")
            style.set("align-items", "flex-start")
            style.set("gap", "16px")
            add(icon, textContainer)
        }
    }

    private fun createIcon() = Image(config.iconPath, config.techName).apply {
        addClassName("extended-tech-card-icon")
    }

    private fun createTextContainer() = Div().apply {
        addClassName("extended-tech-card-text")

        val title = createTitle()
        val description = createDescription()
        val toggleButton = createToggleButton()

        stateManager.setElements(description, toggleButton)

        add(title, description, toggleButton)
    }

    private fun createTitle() = H3(config.techName).apply {
        addClassName("tech-card-title")
    }

    private fun createDescription() = Div().apply {
        addClassName("tech-card-description")
        addClassName("truncated")
        text = config.description ?: ""
    }

    private fun createToggleButton() = Button("Show more").apply {
        addClassName("tech-card-toggle")
        addClickListener { stateManager.toggle() }
    }
}