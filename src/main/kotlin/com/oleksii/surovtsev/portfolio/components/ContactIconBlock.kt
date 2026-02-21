package com.oleksii.surovtsev.portfolio.components

import com.oleksii.surovtsev.portfolio.entity.ContactActionType
import com.oleksii.surovtsev.portfolio.entity.ContactIconData
import com.oleksii.surovtsev.portfolio.service.ContactActionService
import com.oleksii.surovtsev.portfolio.service.ResourceLoaderService
import com.oleksii.surovtsev.portfolio.util.load
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexLayout
import org.slf4j.LoggerFactory

/**
 * Component that displays contact icons with associated actions.
 * Each icon button triggers a specific contact action (email, phone, map, telegram).
 */
class ContactIconBlock(
    private val resourceLoaderService: ResourceLoaderService,
    private val contactActionService: ContactActionService
) : FlexLayout() {

    private val logger = LoggerFactory.getLogger(ContactIconBlock::class.java)

    init {
        setWidthFull()
        addClassName("contact-icon-container")
        loadAndDisplayContactIcons()
    }

    /**
     * Loads contact icons from JSON and displays them.
     */
    private fun loadAndDisplayContactIcons() {
        try {
            val contactIcons: List<ContactIconData> =
                resourceLoaderService.json.load("contact-icons.json")

            contactIcons.forEach { iconData ->
                add(createIconButton(iconData))
            }

            logger.debug("Loaded {} contact icons", contactIcons.size)
        } catch (e: Exception) {
            logger.error("Failed to load contact icons", e)
            // Consider showing a fallback UI or notification
        }
    }

    /**
     * Creates an icon button with label for a contact action.
     */
    private fun createIconButton(iconData: ContactIconData): Div {
        val button = Button(Icon(iconData.icon)).apply {
            addClickListener {
                handleIconClick(iconData.actionType, iconData.actionValue)
            }
            addClassName("contact-icon-button")
        }

        val caption = Span(iconData.label).apply {
            addClassName("contact-icon-caption")
        }

        return Div(button, caption).apply {
            addClassName("contact-icon-wrapper")
        }
    }

    /**
     * Handles icon button clicks by delegating to the contact action service.
     */
    private fun handleIconClick(actionType: ContactActionType, actionValue: String) {
        ui.ifPresent { uiInstance ->
            contactActionService.executeAction(uiInstance, actionType, actionValue)
        }
    }
}