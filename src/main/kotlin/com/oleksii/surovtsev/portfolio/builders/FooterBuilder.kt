package com.oleksii.surovtsev.portfolio.builders

import com.oleksii.surovtsev.portfolio.utils.NavButtonsBuilder
import com.oleksii.surovtsev.portfolio.utils.SocialLinksIconBlockBuilder
import com.vaadin.flow.component.html.Anchor
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout

class FooterBuilder {
    fun createFooter(): VerticalLayout {
        val footer = VerticalLayout().apply {
            width = "100%"
            isPadding = true
            isSpacing = true

        }.apply {
            addClassName("my-footer")
        }

        val contactInfo = HorizontalLayout().apply {
            addClassName("contact-info")
            isSpacing = true
            isPadding = true
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            add(
                Anchor("tel:+380674708862", "Phone: +380674708862").apply {
                    addClassName("contact-link")
                },
                Anchor("mailto:joleksiysurovtsev@gmail.com", "Email: joleksiysurovtsev@gmail.com").apply {
                    addClassName("contact-link")
                }
            )
        }

        // social network
        val socialLinks: HorizontalLayout = SocialLinksIconBlockBuilder.createSocialLinksIconBlock(
        ).apply { justifyContentMode = FlexComponent.JustifyContentMode.END } // Social Media Right

        // Main horizontal container
        val horizontalLayoutContact = HorizontalLayout(contactInfo, socialLinks).apply {
            alignItems = Alignment.CENTER // Elements are aligned vertically
            width = "100%" // Full width
            setJustifyContentMode(FlexComponent.JustifyContentMode.END)
        }

        // line-separator
        val separator1 = HorizontalLayout().apply {
            width = "100%"
            style.set("height", "1px").set("background-color", "var(--lumo-contrast-20pct)")
            addClassName("footer-separator")
        }

        val footerText = com.vaadin.flow.component.html.Span(
            "Created and built by Oleksiy with Love & Coffee"
        ).apply {
            addClassName("footer-text")
            style.set("font-size", "0.9em").set("color", "var(--lumo-secondary-text-color)")
        }

        // navigation buttons
        val navButtons: HorizontalLayout = NavButtonsBuilder.createNavButtons().apply {
            width = "100%" // Cover the entire width of the parent
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER // Center of buttons
        }

        // Main horizontal container
        val horizontalLayout = HorizontalLayout(navButtons, footerText).apply {
            alignItems = Alignment.CENTER // Elements are aligned vertically
            width = "100%" // Full width
            setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN) // equitable distribution
        }

        // Add horizontal layout to the header
        footer.add(horizontalLayoutContact, separator1, horizontalLayout)
        return footer
    }
}
