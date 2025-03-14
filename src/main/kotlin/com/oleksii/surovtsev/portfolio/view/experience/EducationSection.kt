package com.oleksii.surovtsev.portfolio.view.experience

import com.oleksii.surovtsev.portfolio.components.CustomDividerH2
import com.oleksii.surovtsev.portfolio.entity.ExperiencePart
import com.oleksii.surovtsev.portfolio.util.UtilFileManager
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.html.H4
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.orderedlayout.VerticalLayout

class EducationSection() : VerticalLayout() {
    init {
        add (CustomDividerH2("EDUCATION"))
        val experienceItems: List<ExperiencePart> = UtilFileManager.getDataFromJson("education-parts.json")
        setId("timeline")
        addClassName("timeline-section")
        isSpacing = true
        isPadding = false

        experienceItems.forEach { experience: ExperiencePart ->
            val itemLayout = VerticalLayout().apply {
                addClassName("timeline-item")
                isSpacing = false
                isPadding = false
                val title = H3(experience.title)
                this.add(title)
                add(TimeLinePeriod(experience.period).apply { isSpacing = true  })
                add(H4(experience.projectName).apply { style.set("margin-top", "8px")})
                add(Paragraph(experience.description).apply { isPadding = true })
                add(H4(experience.experienceDescription.experienceDescriptionHeader).apply { isPadding = true })
                experience.experienceDescription.listDescription.forEach { line ->
                    add(Paragraph("• $line").apply { addClassName("timeline-text") })
                }

                val badges: List<BadgePillIcons> =
                    experience.experienceDescription.badges.map { badge -> badge.toBadgePillIcon() }
                add(ExperienceBadges(badges))
            }
            add(itemLayout)
        }
    }
}