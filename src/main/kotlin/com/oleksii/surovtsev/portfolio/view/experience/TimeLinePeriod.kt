package com.oleksii.surovtsev.portfolio.view.experience

import com.oleksii.surovtsev.portfolio.entity.ExperiencePeriod
import com.vaadin.flow.component.html.H5
import com.vaadin.flow.component.orderedlayout.VerticalLayout

class TimeLinePeriod(period: ExperiencePeriod): VerticalLayout() {
    init {
        isSpacing = false
        isPadding = false
        add(H5("${period.start} - ${period.end}"))
    }
}