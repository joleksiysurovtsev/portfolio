package com.oleksii.surovtsev.portfolio.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class ExperiencePart @JsonCreator constructor(
    @JsonProperty("title") val title: String,
    @JsonProperty("period") val period: ExperiencePeriod,
    @JsonProperty("experienceDescription") val experienceDescription: ExperienceDescription,
)

data class FooterLink  @JsonCreator constructor(
    @JsonProperty("href") val href: String,
    @JsonProperty("text") val text: String,
)

data class SocialIcon  @JsonCreator constructor(
    @JsonProperty("imagePath") val imagePath: String,
    @JsonProperty("url") val url: String,
    @JsonProperty("name") val name: String,
)