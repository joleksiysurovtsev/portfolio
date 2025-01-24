package com.oleksii.surovtsev.portfolio.entity

import com.fasterxml.jackson.annotation.JsonProperty
import com.oleksii.surovtsev.portfolio.components.BadgePillIcons

data class BadgePillIconData(
    @JsonProperty("text") val text: String,
    @JsonProperty("icon") val icon: String? = null
){
    fun toBadgePillIcon(): BadgePillIcons {
        return BadgePillIcons.fromData(this.text, this.icon)
    }
}