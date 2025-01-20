package com.oleksii.surovtsev.portfolio.entity

data class TechCardData(
    val cardName: String,
    val cardIcon: String,
    val description: String? = null,
    val link: String? = null,
)