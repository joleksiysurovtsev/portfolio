package com.oleksii.surovtsev.portfolio.view.contact

import com.oleksii.surovtsev.portfolio.util.InputSanitizer
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
import org.slf4j.LoggerFactory

class SendMailForm(
    private val emailService: EmailService,
    private val inputSanitizer: InputSanitizer
) : VerticalLayout() {

    private val logger = LoggerFactory.getLogger(SendMailForm::class.java)

    companion object {
        private const val MAX_NAME_LENGTH = 100
        private const val MAX_MESSAGE_LENGTH = 5000
    }

    init {
        setWidthFull()
        minHeight = "400px"
        addClassName("send-mail-block")
        isSpacing = true
        isPadding = true

        val nameField = TextField("Your Name").apply {
            setWidthFull()
            maxLength = MAX_NAME_LENGTH
            isRequired = true
        }
        val emailField = EmailField("Your Email Address").apply {
            setWidthFull()
            isRequired = true
            errorMessage = "Please enter a valid email address"
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
            maxLength = MAX_MESSAGE_LENGTH
            isRequired = true
        }

        val sendButton = Button("Send Message").apply {
            icon = Icon(VaadinIcon.MAILBOX)
            addClassName("send-button")

            addClickListener {
                val name = nameField.value?.trim() ?: ""
                val email = emailField.value?.trim() ?: ""
                val message = messageField.value?.trim() ?: ""

                if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                    Notification.show("Please fill in all fields", 3000, Notification.Position.MIDDLE)
                    return@addClickListener
                }

                if (emailField.isInvalid) {
                    Notification.show("Please enter a valid email address", 3000, Notification.Position.MIDDLE)
                    return@addClickListener
                }

                if (name.length > MAX_NAME_LENGTH) {
                    Notification.show("Name is too long (max $MAX_NAME_LENGTH characters)", 3000, Notification.Position.MIDDLE)
                    return@addClickListener
                }

                if (message.length > MAX_MESSAGE_LENGTH) {
                    Notification.show("Message is too long (max $MAX_MESSAGE_LENGTH characters)", 3000, Notification.Position.MIDDLE)
                    return@addClickListener
                }

                try {
                    // Sanitize all inputs using OWASP sanitizer
                    val sanitizedName = inputSanitizer.sanitizeName(name, MAX_NAME_LENGTH)
                    val sanitizedEmail = inputSanitizer.sanitizeEmail(email)
                    val sanitizedMessage = inputSanitizer.sanitizeMessage(message, MAX_MESSAGE_LENGTH)

                    emailService.sendEmail(sanitizedName, sanitizedEmail, sanitizedMessage)
                    Notification.show("Message sent successfully!", 3000, Notification.Position.MIDDLE)
                    logger.info("Contact form submitted successfully from: {}", sanitizedEmail)
                    nameField.clear()
                    emailField.clear()
                    messageField.clear()
                } catch (e: IllegalArgumentException) {
                    logger.warn("Invalid input in contact form: {}", e.message)
                    Notification.show(e.message ?: "Invalid input", 3000, Notification.Position.MIDDLE)
                } catch (e: com.oleksii.surovtsev.portfolio.exception.EmailValidationException) {
                    logger.warn("Email validation failed: {}", e.message)
                    Notification.show(e.message ?: "Invalid input", 3000, Notification.Position.MIDDLE)
                } catch (e: com.oleksii.surovtsev.portfolio.exception.EmailRateLimitException) {
                    logger.warn("Email rate limit exceeded")
                    val retryMessage = if (e.retryAfterSeconds != null) {
                        "Too many messages sent. Please try again in ${e.retryAfterSeconds} seconds."
                    } else {
                        e.message ?: "Too many messages sent. Please try again later."
                    }
                    Notification.show(retryMessage, 5000, Notification.Position.MIDDLE)
                } catch (e: com.oleksii.surovtsev.portfolio.exception.EmailNetworkException) {
                    logger.error("Network error sending email", e)
                    Notification.show("Network error. Please check your connection and try again.", 4000, Notification.Position.MIDDLE)
                } catch (e: com.oleksii.surovtsev.portfolio.exception.EmailConfigurationException) {
                    logger.error("Email configuration error", e)
                    Notification.show("Email service is not configured properly. Please contact support.", 5000, Notification.Position.MIDDLE)
                } catch (e: com.oleksii.surovtsev.portfolio.exception.EmailException) {
                    logger.error("Failed to send contact form email", e)
                    Notification.show(e.message ?: "Failed to send message. Please try again later.", 4000, Notification.Position.MIDDLE)
                } catch (e: EmailSendingException) {
                    // Legacy exception for backward compatibility
                    logger.error("Failed to send contact form email", e)
                    Notification.show("Failed to send message. Please try again later.", 4000, Notification.Position.MIDDLE)
                } catch (e: Exception) {
                    logger.error("Unexpected error in contact form", e)
                    Notification.show("An unexpected error occurred. Please try again later.", 4000, Notification.Position.MIDDLE)
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