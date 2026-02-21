package com.oleksii.surovtsev.portfolio.view.experience

import com.oleksii.surovtsev.portfolio.components.CustomDivider
import com.oleksii.surovtsev.portfolio.config.RoutesConfig
import com.oleksii.surovtsev.portfolio.layout.MainLayout
import com.oleksii.surovtsev.portfolio.service.ResourceLoaderService
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route


@Route(value = RoutesConfig.Main.EXPERIENCE, layout = MainLayout::class)
class ExperienceView(
    private val resourceLoaderService: ResourceLoaderService
) : VerticalLayout() {

    init {
        setWidthFull()
        justifyContentMode = FlexComponent.JustifyContentMode.START
        alignItems = Alignment.CENTER
        addClassName("experience")

        val skillsAndCertificatesContainer = HorizontalLayout().apply {
            setWidthFull()
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            addClassName("skills-certificates-container")
            add(SkillsSelection(resourceLoaderService), CertificatesSelection(resourceLoaderService))
        }

        add(
            ExperienceAndSkillsBlock(resourceLoaderService),
            skillsAndCertificatesContainer,
            CustomDivider(),
            CareerGoalsAndPhilosophyBlock()
        )
    }
}