package com.oleksii.surovtsev.portfolio.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class ExperiencePart @JsonCreator constructor(
    @JsonProperty("title") val title: String,
    @JsonProperty("period") val period: ExperiencePeriod,
    @JsonProperty("experienceDescription") val experienceDescription: ExperienceDescription,
)