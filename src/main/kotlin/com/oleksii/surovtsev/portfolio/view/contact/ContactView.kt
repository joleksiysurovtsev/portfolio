package com.oleksii.surovtsev.portfolio.view.contact

import com.oleksii.surovtsev.portfolio.components.CustomDivider
import com.oleksii.surovtsev.portfolio.components.CustomDividerH2
import com.oleksii.surovtsev.portfolio.layout.MainLayout
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route

@Route(value = "/contact", layout = MainLayout::class)
class ContactView(emailSender: EmailSender) : VerticalLayout() {
    init {
        setWidthFull()
        justifyContentMode = FlexComponent.JustifyContentMode.START
        alignItems = Alignment.CENTER
        addClassName("contact-view")
        add(
            CustomDividerH2("CONTACT"),
            ContactIconBlock(),
            CustomDivider(),
            SendMailForm(emailSender)
        )
    }
}

class ContactIconBlock : FlexLayout() {
    init {
        setWidthFull()
        addClassName("contact-icon-container")

        // Добавляем кнопки
        add(createIconButton("joleksiysurovtsev@gmail.com", VaadinIcon.ENVELOPE) {
            getUI().ifPresent { ui -> ui.page.executeJs("window.location.href='mailto:joleksiysurovtsev@gmail.com'") }
        })

        add(createIconButton("Phone: +380674708802", VaadinIcon.PHONE) {
            getUI().ifPresent { ui -> ui.page.executeJs("window.location.href='tel:+380674708802'") }
        })

        add(createIconButton("Location: Ukraine", VaadinIcon.MAP_MARKER) {
            getUI().ifPresent { ui -> ui.page.executeJs("window.open('https://maps.app.goo.gl/Q39hez7Fr5P4uddD8', 'Cherkasy')") }
        })

        add(createIconButton("Telegram", VaadinIcon.PAPERPLANE) {
            getUI().ifPresent { ui -> ui.page.executeJs("window.open('https://t.me/SurovtsevOleksii', 'SurovtsevOleksii')") }
        })
    }

    private fun createIconButton(label: String, icon: VaadinIcon, onClick: () -> Unit): Div {
        val button = Button(Icon(icon)).apply {
            addClickListener { onClick() }
            addClassName("contact-icon-button")
        }

        val caption = Span(label).apply {
            addClassName("contact-icon-caption")
        }

        return Div(button, caption).apply {
            addClassName("contact-icon-wrapper")
        }
    }
}
