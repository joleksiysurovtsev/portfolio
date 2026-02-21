package com.oleksii.surovtsev.portfolio.view.contact

import com.oleksii.surovtsev.portfolio.components.ContactIconBlock
import com.oleksii.surovtsev.portfolio.components.CustomDivider
import com.oleksii.surovtsev.portfolio.components.CustomDividerH2
import com.oleksii.surovtsev.portfolio.config.RoutesConfig
import com.oleksii.surovtsev.portfolio.layout.MainLayout
import com.oleksii.surovtsev.portfolio.service.ContactActionService
import com.oleksii.surovtsev.portfolio.service.ResourceLoaderService
import com.oleksii.surovtsev.portfolio.util.InputSanitizer
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route

@Route(value = RoutesConfig.Main.CONTACT, layout = MainLayout::class)
class ContactView(
    emailService: EmailService,
    inputSanitizer: InputSanitizer,
    resourceLoaderService: ResourceLoaderService,
    contactActionService: ContactActionService
) : VerticalLayout() {
    init {
        setWidthFull()
        justifyContentMode = FlexComponent.JustifyContentMode.START
        alignItems = Alignment.CENTER
        addClassName("contact-view")
        add(
                CustomDividerH2("CONTACT"),
                ContactIconBlock(resourceLoaderService, contactActionService),
                CustomDivider(),
                SendMailForm(emailService, inputSanitizer)
        )
    }
}
