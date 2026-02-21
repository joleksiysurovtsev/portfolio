package com.oleksii.surovtsev.portfolio.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.vaadin.flow.component.icon.VaadinIcon

/**
 * Enum representing different types of contact actions.
 */
enum class ContactActionType {
    EMAIL,
    PHONE,
    MAP,
    TELEGRAM
}

/**
 * Data class representing a contact icon configuration.
 * Used to configure contact buttons in the UI.
 */
data class ContactIconData
@JsonCreator
constructor(
    @JsonProperty("label") val label: String,
    @JsonProperty("icon") val icon: VaadinIcon,
    @JsonProperty("actionType") val actionType: ContactActionType,
    @JsonProperty("actionValue") val actionValue: String
)