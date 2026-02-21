package com.oleksii.surovtsev.portfolio.view.experience

import com.oleksii.surovtsev.portfolio.service.ResourceLoaderService
import com.vaadin.flow.component.html.H5
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout

class ExperienceAndSkillsBlock(
    private val resourceLoaderService: ResourceLoaderService
) : VerticalLayout() {

    init {

        val textLayout = VerticalLayout().apply {
            addClassName("experience-text")
            add(H5("4 Years of Experience"))
        }

        val experienceSectionAndSkillsSection = FlexLayout().apply {
            setWidthFull()
            flexDirection = FlexLayout.FlexDirection.ROW
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            alignItems = FlexComponent.Alignment.START
            addClassName("experience-skills-container")

            val experienceSection = ExperienceSection(resourceLoaderService).apply {
                addClassName("experience-section")
            }

            val educationSection = EducationSection(resourceLoaderService).apply {
                addClassName("experience-section")
            }



            add(experienceSection, educationSection)
        }
        add(textLayout, experienceSectionAndSkillsSection)
    }
}