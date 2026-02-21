package com.oleksii.surovtsev.portfolio.view.experience

import com.oleksii.surovtsev.portfolio.entity.SkillBarInfo
import com.oleksii.surovtsev.portfolio.entity.enums.SkillType
import com.oleksii.surovtsev.portfolio.service.ResourceLoaderService
import com.oleksii.surovtsev.portfolio.util.load
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.orderedlayout.VerticalLayout

class SkillsSelection(
    private val resourceLoaderService: ResourceLoaderService
) : VerticalLayout() {
    init {
        addClassName("skills-section")
        isSpacing = true
        isPadding = true
        add(H3("Coding Skills"))

        val skills: List<SkillBarInfo> = resourceLoaderService.json.load("skill-barr-info.json")
        skills.filter { it.type == SkillType.CODING }.forEach { (skill, percentage) ->
            add(SkillBar(skill, percentage))
        }

        add(H3("Soft Skills"))

        skills.filter { it.type == SkillType.SOFT }.forEach { (skill, percentage) ->
            add(SkillBar(skill, percentage))
        }
    }
}

class CertificatesSelection(
    private val resourceLoaderService: ResourceLoaderService
) : VerticalLayout() {
    init {
        addClassName("certificates-section")
        isSpacing = true
        isPadding = true
        add(H3("My Certificates"))
        add(CertificationSlider(resourceLoaderService))
    }
}
