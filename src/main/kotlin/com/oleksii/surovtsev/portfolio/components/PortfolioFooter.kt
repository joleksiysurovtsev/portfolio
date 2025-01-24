package com.oleksii.surovtsev.portfolio.components

import com.oleksii.surovtsev.portfolio.entity.FooterLink
import com.oleksii.surovtsev.portfolio.entity.SocialIcon
import com.oleksii.surovtsev.portfolio.util.UtilFileManager
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.orderedlayout.HorizontalLayout


class PortfolioFooter : Footer() {
    init {
        addClassName("footer")
        val copyright = CopywriterDisclaimer()
        val footerLinks = FooterLinks()
        val social = SocialLinksIconBlock()
        add(copyright, footerLinks, social)
    }
}

class CopywriterDisclaimer : Div() {
    init {
        addClassName("footer-copyright-disclaimer")
        add(Paragraph("© 2025 Oleksii Surovtsev. All Rights Reserved."))
    }
}

class FooterLinks : Div() {
    init {
        addClassName("footer-links-div")
        val links = UtilFileManager.getDataFromJson<FooterLink>("footer-links.json").map {
            Anchor(it.href, it.text).apply { addClassName("footer-link") }
        }
        add(links)
    }
}

class SocialLinksIconBlock : HorizontalLayout() {
    init {
        addClassName("footer-social-links")
        val socialIcons = UtilFileManager.getDataFromJson<SocialIcon>("social-icons.json")
            .map { socialIcon: SocialIcon ->
                val image = Image(socialIcon.imagePath, socialIcon.name)
                    .apply { addClassName("footer-clickable-icon") }
                Anchor(socialIcon.url, image).apply { setTarget("_blank") }
            }
        add(socialIcons)
    }
}