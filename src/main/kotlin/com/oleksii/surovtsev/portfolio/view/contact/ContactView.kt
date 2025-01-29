package com.oleksii.surovtsev.portfolio.view.contact

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.oleksii.surovtsev.portfolio.components.CustomDivider
import com.oleksii.surovtsev.portfolio.components.CustomDividerH2
import com.oleksii.surovtsev.portfolio.layout.MainLayout
import com.oleksii.surovtsev.portfolio.util.UtilFileManager
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

data class ContactIconData
@JsonCreator
constructor(
        @JsonProperty("label") val label: String,
        @JsonProperty("icon") val icon: VaadinIcon,
        @JsonProperty("action") val action: String
)

class ContactIconBlock : FlexLayout() {
    init {
        setWidthFull()
        addClassName("contact-icon-container")

        val contactIcons: List<ContactIconData> =
                UtilFileManager.getDataFromJson("contact-icons.json")

        contactIcons.forEach { iconData ->
            add(
                    createIconButton(iconData.label, iconData.icon) {
                        getUI().ifPresent { ui -> ui.page.executeJs(iconData.action) }
                    }
            )
        }
    }

    private fun createIconButton(label: String, icon: VaadinIcon, onClick: () -> Unit): Div {
        val button =
                Button(Icon(icon)).apply {
                    addClickListener { onClick() }
                    addClassName("contact-icon-button")
                }

        val caption = Span(label).apply { addClassName("contact-icon-caption") }

        return Div(button, caption).apply { addClassName("contact-icon-wrapper") }
    }
}
