package com.oleksii.surovtsev.portfolio.components.docs.sections

import com.oleksii.surovtsev.portfolio.components.docs.DocSection
import com.oleksii.surovtsev.portfolio.components.docs.FeatureCard
import com.vaadin.flow.component.orderedlayout.FlexLayout

/**
 * Features section component for plugin documentation
 */
class FeaturesSection : DocSection("Features", "features") {
    init {
        // Create feature cards grid
        val featuresGrid = FlexLayout().apply {
            setFlexWrap(FlexLayout.FlexWrap.WRAP)
            addClassName("features-grid")

            FeatureCard.createPluginFeatures().forEach { card ->
                card.apply {
                    element.style.set("flex", "1 1 calc(50% - 1rem)")
                    element.style.set("min-width", "300px")
                }
                add(card)
            }
        }

        addContent(featuresGrid)
    }
}
