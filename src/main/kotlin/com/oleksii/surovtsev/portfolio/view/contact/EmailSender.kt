package com.oleksii.surovtsev.portfolio.view.contact

import com.oleksii.surovtsev.portfolio.config.EmailProperties
import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import org.slf4j.LoggerFactory
import java.io.IOException

class EmailSender(private val emailProperties: EmailProperties) : EmailService {

    private val logger = LoggerFactory.getLogger(EmailSender::class.java)

    override fun sendEmail(name: String, email: String, message: String) {
        val from = Email(emailProperties.sender)
        val subject = "New message from contact form"
        val to = Email(emailProperties.recipient)
        val content = Content("text/plain", "Name: $name\nEmail: $email\nMessage: $message")
        val mail = Mail(from, subject, to, content)

        val sg = SendGrid(emailProperties.apiKey)
        val request = Request()
        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = mail.build()
            val response = sg.api(request)

            if (response.statusCode in 200..299) {
                logger.info("Email sent successfully from: {} to: {}, status: {}", email, emailProperties.recipient, response.statusCode)
            } else {
                logger.warn("Email sending returned non-2xx status: {}, body: {}", response.statusCode, response.body)
            }
        } catch (e: IOException) {
            logger.error("Network error while sending email from: {}", email, e)
            throw EmailSendingException("Failed to send email due to network error", e)
        } catch (e: Exception) {
            logger.error("Unexpected error while sending email from: {}", email, e)
            throw EmailSendingException("Unexpected error during email send", e)
        }
    }
}

class EmailSendingException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)