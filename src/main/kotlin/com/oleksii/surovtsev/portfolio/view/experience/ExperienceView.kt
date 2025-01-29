package com.oleksii.surovtsev.portfolio.view.experience

import com.oleksii.surovtsev.portfolio.components.CustomDivider
import com.oleksii.surovtsev.portfolio.components.CustomDividerH2
import com.oleksii.surovtsev.portfolio.layout.MainLayout
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route


@Route(value = "/experience", layout = MainLayout::class)
class ExperienceView : VerticalLayout() {

    init {
        setWidthFull()
        justifyContentMode = FlexComponent.JustifyContentMode.START
        alignItems = Alignment.CENTER
        addClassName("experience")
        add(
            CustomDividerH2("EXPERIENCE"),
            ExperienceAndSkillsBlock(),
            CustomDivider(),
            CareerGoalsAndPhilosophyBlock()
        )
    }

}
