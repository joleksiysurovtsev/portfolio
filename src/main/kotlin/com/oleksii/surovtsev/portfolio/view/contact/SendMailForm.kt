package com.oleksii.surovtsev.portfolio.view.contact

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.EmailField
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.component.textfield.TextField

class SendMailForm(emailSender: EmailSender) : VerticalLayout() {
    init {
        setWidthFull()
        minHeight = "400px"
        addClassName("send-mail-block")
        isSpacing = true
        isPadding = true

        val nameField = TextField("Your Name").apply {
            setWidthFull()
        }
        val emailField = EmailField("Your Email Address").apply {
            setWidthFull()
        }

        val topRow = HorizontalLayout(nameField, emailField).apply {
            setWidthFull()
            isSpacing = true
            className = "top-row"
        }

        val messageField = TextArea("Your Message").apply {
            width = "100%"
            height = "300px"
            className = "message-field"
        }

        val sendButton = Button("Send Message").apply {
            icon = Icon(VaadinIcon.MAILBOX)
            addClassName("send-button")

            addClickListener {
                val name = nameField.value.trim()
                val email = emailField.value.trim()
                val message = messageField.value.trim()

                if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                    Notification.show("Please fill in all fields before sending!", 3000, Notification.Position.MIDDLE)
                } else {
                    try {
                        emailSender.sendEmail(name, email, message)
                        Notification.show("Message sent successfully!", 3000, Notification.Position.MIDDLE)
                        nameField.clear()
                        emailField.clear()
                        messageField.clear()
                    } catch (e: Exception) {
                        Notification.show("Error sending message: ${e.message}", 3000, Notification.Position.MIDDLE)
                    }
                }
            }
        }

        val buttonWrapper = HorizontalLayout(sendButton).apply {
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            setWidthFull()
        }

        add(topRow, messageField, buttonWrapper)
    }
}