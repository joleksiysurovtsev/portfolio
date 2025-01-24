package com.oleksii.surovtsev.portfolio.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class TechCardData @JsonCreator constructor(
    @JsonProperty("cardName") val cardName: String,
    @JsonProperty("cardIcon") val cardIcon: String,
    @JsonProperty("description") val description: String? = null,
    @JsonProperty("link") val link: String? = null,
)