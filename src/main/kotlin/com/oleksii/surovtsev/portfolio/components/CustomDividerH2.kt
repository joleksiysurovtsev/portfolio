package com.oleksii.surovtsev.portfolio.components

import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.html.Hr
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class CustomDividerH2(val name: String) : HorizontalLayout() {
    init {
        setWidthFull()
        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        alignItems = FlexComponent.Alignment.CENTER

        val leftLine = Hr().apply { addClassName("contact-custom-divider") }
        val text = H2(name)
        val rightLine = Hr().apply { addClassName("contact-custom-divider") }

        add(leftLine, text, rightLine)
    }
}