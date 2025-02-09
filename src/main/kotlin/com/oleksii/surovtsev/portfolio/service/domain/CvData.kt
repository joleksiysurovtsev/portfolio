package com.oleksii.surovtsev.portfolio.service.domain

data class CvData(
    val fullName: String,
    val position: String,
    val contactInfo: ContactInfo,
    val experience: List<Experience>,
    val skills: List<String>,
    val education: List<Education>,
)