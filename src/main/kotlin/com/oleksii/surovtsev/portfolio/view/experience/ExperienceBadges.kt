package com.oleksii.surovtsev.portfolio.view.experience

import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class ExperienceBadges(badges: List<BadgePillIcons>) : HorizontalLayout() {
    init {
            isSpacing = false
            isPadding = false
            addClassName("badge-container")
            add(badges)
    }
}