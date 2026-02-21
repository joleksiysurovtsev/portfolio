package com.oleksii.surovtsev.portfolio.service

import com.oleksii.surovtsev.portfolio.entity.ContactActionType
import com.vaadin.flow.component.UI
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * Service responsible for handling contact-related actions.
 * Separates the business logic from UI components.
 */
@Service
class ContactActionService {

    private val logger = LoggerFactory.getLogger(ContactActionService::class.java)

    /**
     * Executes a contact action based on the action type.
     *
     * @param ui The UI instance to perform the action
     * @param actionType The type of contact action to execute
     * @param value The value associated with the action (e.g., email address, phone number, URL)
     */
    fun executeAction(ui: UI, actionType: ContactActionType, value: String) {
        try {
            when (actionType) {
                ContactActionType.EMAIL -> {
                    ui.page.executeJs("window.location.href = 'mailto:' + $0", value)
                    logger.debug("Opened email client for: {}", maskEmail(value))
                }
                ContactActionType.PHONE -> {
                    ui.page.executeJs("window.location.href = 'tel:' + $0", value)
                    logger.debug("Initiated phone call to: {}", maskPhoneNumber(value))
                }
                ContactActionType.MAP -> {
                    ui.page.open(value, "_blank")
                    logger.debug("Opened map location")
                }
                ContactActionType.TELEGRAM -> {
                    ui.page.open(value, "_blank")
                    logger.debug("Opened Telegram link")
                }
            }
        } catch (e: Exception) {
            logger.error("Failed to execute contact action: {} with value: {}", actionType, value, e)
            // Consider showing a notification to the user
        }
    }

    /**
     * Masks email for logging purposes.
     */
    private fun maskEmail(email: String): String {
        val parts = email.split("@")
        if (parts.size != 2) return "****"

        val localPart = parts[0]
        val domain = parts[1]

        return if (localPart.length > 2) {
            "${localPart.take(2)}****@$domain"
        } else {
            "****@$domain"
        }
    }

    /**
     * Masks phone number for logging purposes.
     */
    private fun maskPhoneNumber(phone: String): String {
        val digitsOnly = phone.filter { it.isDigit() }
        return if (digitsOnly.length >= 8) {
            val visibleDigits = digitsOnly.takeLast(4)
            "**** **** $visibleDigits"
        } else {
            "****"
        }
    }
}