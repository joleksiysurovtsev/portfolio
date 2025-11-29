package com.oleksii.surovtsev.portfolio.view.contact

/**
 * Service interface for sending emails.
 */
interface EmailService {
    /**
     * Sends an email with the provided details.
     *
     * @param name Sender's name
     * @param email Sender's email address
     * @param message Message content
     * @throws EmailSendingException if email sending fails
     */
    fun sendEmail(name: String, email: String, message: String)
}
