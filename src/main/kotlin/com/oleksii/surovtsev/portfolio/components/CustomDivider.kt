package com.oleksii.surovtsev.portfolio.components

import com.vaadin.flow.component.html.Hr

class CustomDivider : Hr {

    constructor() : this("9%", "9%")

    constructor(marginLeft: String, marginRight: String) {
        initHr(marginLeft, marginRight)
    }

    private fun initHr(marginLeft: String, marginRight: String) {
        addClassName("custom-divider")
        style.set("margin-left", marginLeft)
        style.set("margin-right", marginRight)
    }
}