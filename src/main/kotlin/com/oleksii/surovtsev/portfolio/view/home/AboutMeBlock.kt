package com.oleksii.surovtsev.portfolio.view.home

import com.oleksii.surovtsev.portfolio.util.UtilFileManager
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.FlexLayout

class AboutMeBlock : FlexLayout() {

    init {
        setWidthFull()
        flexDirection = FlexDirection.ROW
        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        alignItems = Alignment.CENTER
        addClassName("about-container")

        val photoLayout = Div().apply {
            val photo = Image("img/my-photo-r.png", "My Photo")
            add(photo)
            addClassName("about-photo")
        }

        val textLayout = Div().apply {
            val textLines = UtilFileManager.getTextFromFile("greeting.txt")
            val title = H1(textLines[0])
            val description: Div = Div().apply { text = textLines.drop(1).joinToString("\n") }
            add(title, description)
            addClassName("about-text")
        }
        add(photoLayout, textLayout)
    }
}
