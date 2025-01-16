package com.oleksii.surovtsev.portfolio.layouts

import com.oleksii.surovtsev.portfolio.builders.ContentMainPageBuilder
import com.oleksii.surovtsev.portfolio.builders.FooterBuilder
import com.oleksii.surovtsev.portfolio.builders.HeaderBuilder
import com.vaadin.flow.component.AttachEvent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.RouterLayout


class MainLayout(
) : VerticalLayout(), RouterLayout {

    private val headerBuilder: HeaderBuilder by lazy { HeaderBuilder() }
    private val contentMainPageBuilder: ContentMainPageBuilder by lazy { ContentMainPageBuilder() }
    private val footerBuilder: FooterBuilder by lazy { FooterBuilder() }

    init {
        width = "100%"
        height = "100vh"
        defaultHorizontalComponentAlignment = Alignment.CENTER
        val header: VerticalLayout = headerBuilder.createHeader()
        val content: VerticalLayout = contentMainPageBuilder.createContent()
        val footer: VerticalLayout = footerBuilder.createFooter()
        add(header)
        addAndExpand(content)
        add(footer)
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
