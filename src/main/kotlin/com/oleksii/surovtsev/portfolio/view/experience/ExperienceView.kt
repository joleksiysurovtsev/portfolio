package com.oleksii.surovtsev.portfolio.view.experience

import com.oleksii.surovtsev.portfolio.components.CustomDivider
import com.oleksii.surovtsev.portfolio.layout.MainLayout
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route


@Route(value = "/experience", layout = MainLayout::class)
class ExperienceView : VerticalLayout() {

    init {
        setWidthFull()
        justifyContentMode = FlexComponent.JustifyContentMode.START
        alignItems = Alignment.CENTER
        addClassName("experience")

        val skillsAndCertificatesContainer = HorizontalLayout().apply {
            setWidthFull()
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            addClassName("skills-certificates-container")
            add(SkillsSelection(), CertificatesSelection())
        }

        add(
            ExperienceAndSkillsBlock(),
            skillsAndCertificatesContainer,
            CustomDivider(),
            CareerGoalsAndPhilosophyBlock()
        )
    }
}