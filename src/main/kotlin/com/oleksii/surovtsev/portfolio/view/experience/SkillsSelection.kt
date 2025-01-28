package com.oleksii.surovtsev.portfolio.view.experience

import com.oleksii.surovtsev.portfolio.entity.SkillBarInfo
import com.oleksii.surovtsev.portfolio.entity.enums.SkillType
import com.oleksii.surovtsev.portfolio.util.UtilFileManager
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.orderedlayout.VerticalLayout

class SkillsSelection : VerticalLayout() {
    init {
        addClassName("skills-section")
        isSpacing = true
        isPadding = false
        val codingHeader = H3("Coding Skills").apply {
            addClassName("coding-skills-section-header")
        }
        add(codingHeader)

        val skills: List<SkillBarInfo> = UtilFileManager.getDataFromJson("skill-barr-info.json")
        skills.filter { it.type == SkillType.CODING }.forEach { (skill, percentage) ->
            add(SkillBar(skill, percentage))
        }

        val skillsHeader = H3("Soft Skills").apply {
            addClassName("soft-skills-section-header")
        }

        add(skillsHeader)

        skills.filter { it.type == SkillType.SOFT }.forEach { (skill, percentage) ->
            add(SkillBar(skill, percentage))
        }

        val certificatesHeader = H3("My certificates").apply {
            addClassName("soft-skills-section-header")
        }

        add(certificatesHeader)

        add(CertificationSlider())
    }
}