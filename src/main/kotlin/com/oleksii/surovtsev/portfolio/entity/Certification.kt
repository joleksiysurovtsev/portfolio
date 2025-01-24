package com.oleksii.surovtsev.portfolio.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class Certification  @JsonCreator constructor(
    @JsonProperty("title") val title: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("imageUrl") val imageUrl: String
)