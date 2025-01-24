package com.oleksii.surovtsev.portfolio.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class SkillBarInfo @JsonCreator constructor(
    @JsonProperty("skill") val skill: String,
    @JsonProperty("percentage") val percentage: Int,
    @JsonProperty("type") val type: SkillType,
)