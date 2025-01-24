package com.oleksii.surovtsev.portfolio.components

import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon

class BadgePillIcons : Span() {

    private fun initBadge(text: String, vaadinIcon: VaadinIcon?) {
        vaadinIcon?.let { add(createIcon(it)) }
        add(Span(text))
        element.themeList.add("badge pill")
        addClassName("badge")
    }

    private fun createIcon(vaadinIcon: VaadinIcon): Icon {
        return vaadinIcon.create().apply {
            style["padding"] = "var(--lumo-space-xs)"
        }
    }

    companion object {
        fun fromData(text: String, iconName: String?): BadgePillIcons {
            val badge = BadgePillIcons()
            val vaadinIcon = iconName?.let { runCatching { VaadinIcon.valueOf(it) }.getOrNull() }
            badge.initBadge(text, vaadinIcon)
            return badge
        }
    }
}
