package com.oleksii.surovtsev.portfolio.components

import com.oleksii.surovtsev.portfolio.entity.TechCardData
import com.oleksii.surovtsev.portfolio.entity.TechCardType
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.dependency.JsModule
import com.vaadin.flow.component.html.*

@JsModule("./elements/card-tech.js")
class TechCard : Div {

    private constructor() : super()

    private var iconPath: String = ""
    private var techName: String = ""
    private var cardType: TechCardType = TechCardType.SMALL
    private var description: String? = null
    private var link: String? = null

    // Keep references to the elements we will manage
    private var descriptionElement: Div? = null
    private var toggleButton: Button? = null

    private var expanded: Boolean = false
        set(value) {
            field = value
            updateExpandedState()
        }

    // Constructor for SMALL cards
    constructor(iconPath: String, techName: String) : this() {
        this.iconPath = iconPath
        this.techName = techName
        this.cardType = TechCardType.SMALL
        initSmall()
    }

    // Constructor for EXTENDED cards
    constructor(iconPath: String, techName: String, description: String, link: String) : this() {
        this.iconPath = iconPath
        this.techName = techName
        this.cardType = TechCardType.EXTENDED
        this.description = description
        this.link = link
        initExtended()
    }


    private fun updateExpandedState() {
        if (expanded) {
            // First change the state of the description
            descriptionElement?.apply {
                removeClassName("truncated")
                style.set("max-height", "var(--description-expanded-height)")
            }
            // Then add the expanded class to the card
            addClassName("expanded")
            toggleButton?.text = "Show less"
        } else {
            // When folding, we first remove the expanded class
            removeClassName("expanded")
            // Then update the description
            descriptionElement?.apply {
                addClassName("truncated")
                style.set("max-height", "var(--description-max-height)")
            }
            toggleButton?.text = "Show more"
        }
    }
    private fun initExtended() {
        addClassName(cardType.basicStyle)

        val icon = createIcon().apply {
            addClassName("extended-tech-card-icon")
        }

        val textContainer = Div().apply {
            addClassName("extended-tech-card-text")

            val name = H3(techName).apply { addClassName("tech-card-title")
            }

            descriptionElement = description?.let { desc ->
                Div().apply {
                    addClassName("tech-card-description")
                    addClassName("truncated")
                    text = desc
                }
            }

            toggleButton = Button().apply {
                addClassName("tech-card-toggle")
                text = "Show more"
                addClickListener {
                    expanded = !expanded
                }
            }

            add(name)
            descriptionElement?.let { add(it) }
            toggleButton?.let { add(it) }
        }

        val content = Div().apply {
            addClassName(cardType.contentStyle)
            style.set("display", "flex")
            style.set("align-items", "flex-start")
            style.set("gap", "16px")
            add(icon, textContainer)
        }

        // We add a cursor exit handler
        element.addEventListener("mouseleave") {
            expanded = false
        }

        add(content)
        initializeJavaScript()
    }

    private fun createIcon(): Image {
        return Image(iconPath, techName)
    }

    private fun initializeJavaScript() {
        element.executeJs("window.Vaadin.techtField.initTechCard($0)", element)
    }

    private fun initSmall() {
        addClassName(cardType.basicStyle)

        val icon = createIcon()
        val content = Div().apply {
            addClassName(cardType.contentStyle)
            val name = Span(techName)
            add(icon, name)
        }

        add(content)
        initializeJavaScript()
    }

    companion object {
        fun fromData(data: TechCardData, techCardType: TechCardType): TechCard {
            return if (techCardType == TechCardType.EXTENDED) {
                TechCard(
                    iconPath = data.cardIcon,
                    techName = data.cardName,
                    description = data.description!!,
                    link = data.link!!
                )
            } else {
                TechCard(
                    iconPath = data.cardIcon,
                    techName = data.cardName
                )
            }
        }
    }
}