package com.oleksii.surovtsev.portfolio.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class ExperienceDescription @JsonCreator constructor(
    @JsonProperty("experienceDescriptionHeader") val experienceDescriptionHeader: String? = "",
    @JsonProperty("listDescription") val listDescription: List<String>,
    @JsonProperty("badges") val badges: List<BadgePillIconData>,
)