package com.oleksii.surovtsev.portfolio.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class ExperiencePeriod @JsonCreator constructor(
    @JsonProperty("start") val start: LocalDate,
    @JsonProperty("end") val end: LocalDate,
)