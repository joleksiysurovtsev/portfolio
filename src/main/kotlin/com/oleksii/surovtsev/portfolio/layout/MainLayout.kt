package com.oleksii.surovtsev.portfolio.layout

import com.oleksii.surovtsev.portfolio.components.PortfolioFooter
import com.oleksii.surovtsev.portfolio.components.PortfolioHeader
import com.vaadin.flow.component.AttachEvent
import com.vaadin.flow.component.HasElement
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.RouterLayout


class MainLayout : VerticalLayout(), RouterLayout {
    private val contentWrapper = VerticalLayout()
    private var currentContent: HasElement? = null

    init {
        isPadding = false
        isSpacing = false

        val header = PortfolioHeader()
        val footer = PortfolioFooter()

        contentWrapper.apply {
            isPadding = false
            isSpacing = false
        }

        header.apply {
            setWidthFull()
            className = "l-header"
        }
        add(header, contentWrapper, footer)
    }

    override fun showRouterLayoutContent(content: HasElement) {
        if (currentContent != null) {
            currentContent!!.element.classList.add("blur-out")

            contentWrapper.element.executeJs(
                """
                    setTimeout(() => {
                        $0.remove();
                    }, 5000);
                    """,
                currentContent!!.element
            )
        }

        currentContent = content
        contentWrapper.element.appendChild(content.element)
        content.element.classList.add("blur-in")
    }

    override fun onAttach(attachEvent: AttachEvent) {
        super.onAttach(attachEvent)
        attachEvent.ui.page.executeJs("""
            const savedTheme = localStorage.getItem('theme') || 'light';
            document.documentElement.setAttribute('theme', savedTheme);
        """)
    }
}