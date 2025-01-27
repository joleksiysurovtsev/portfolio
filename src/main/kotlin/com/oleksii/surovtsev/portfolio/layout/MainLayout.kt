package com.oleksii.surovtsev.portfolio.layout

import com.oleksii.surovtsev.portfolio.components.PortfolioFooter
import com.oleksii.surovtsev.portfolio.components.PortfolioHeader
import com.vaadin.flow.component.AttachEvent
import com.vaadin.flow.component.HasElement
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.RouterLayout

class MainLayout : VerticalLayout(), RouterLayout {
    private val contentWrapper = VerticalLayout()

    init {
//        setSizeFull()
        isPadding = false
        isSpacing = false

        val header = PortfolioHeader()
        val footer = PortfolioFooter()

        // Container for content
        contentWrapper.apply {
//            setWidthFull()
            isPadding = false
            isSpacing = false
//            style["marginTop"] = "var(--header-height)"
        }

        // heder
        header.apply {
            setWidthFull()
            className = "l-header"
        }
        add(header, contentWrapper, footer)
    }

    override fun showRouterLayoutContent(content: HasElement) {
        contentWrapper.element.appendChild(content.element)
    }

    override fun onAttach(attachEvent: AttachEvent) {
        super.onAttach(attachEvent)
        super.addClassName("fade-in")
        attachEvent.ui.page.executeJs("""
        const savedTheme = localStorage.getItem('theme') || 'light';
        document.documentElement.setAttribute('theme', savedTheme);
    """)
    }
}