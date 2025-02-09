package com.oleksii.surovtsev.portfolio.service.domain

data class Experience(
    val companyName: String,
    val position: String,
    val period: String,
    val description: List<String>?,
    val technologies: List<String>?,
)