package com.oleksii.surovtsev.portfolio.entity.enums

enum class TechCardType(
    val basicStyle: String,
    val contentStyle: String,
) {
    SMALL("small-tech-card", "small-tech-card-content"),
    EXTENDED("extended-tech-card", "extended-tech-card-content")
}