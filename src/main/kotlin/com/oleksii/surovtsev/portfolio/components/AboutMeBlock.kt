package com.oleksii.surovtsev.portfolio.components

import com.oleksii.surovtsev.portfolio.util.UtilFileManager
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.FlexLayout

class AboutMeBlock : FlexLayout() {

    companion object {
        const val ABOUT_ME_TEXT_FILE_NAME = "greeting.txt"
        const val IMAGE_SRC = "img/my-photo-r.png"
        const val IMAGE_ALT = "My Photo"

        //STYLES
        const val ABOUT_CONTAINER_STYLE = "about-container"
        const val ABOUT_PHOTO_STYLE = "about-photo"
        const val ABOUT_TEXT_STYLE = "about-text"
    }

    init {
        setWidthFull()
        flexDirection = FlexDirection.ROW
        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        alignItems = Alignment.CENTER
        addClassName(ABOUT_CONTAINER_STYLE)

        val photoLayout = Div().apply {
            val photo = Image(IMAGE_SRC, IMAGE_ALT)
            add(photo)
            className = ABOUT_PHOTO_STYLE
        }

        val textLayout = Div().apply {
            val textLines = UtilFileManager.getTextFromFile(ABOUT_ME_TEXT_FILE_NAME)
            val title = H1(textLines[0])
            val description: Div = Div().apply { text = textLines.drop(1).joinToString("\n") }
            add(title, description)
            className = ABOUT_TEXT_STYLE
        }

        add(photoLayout, textLayout)
    }

}
