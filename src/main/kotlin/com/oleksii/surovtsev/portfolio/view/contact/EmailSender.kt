package com.oleksii.surovtsev.portfolio.view.contact

import com.oleksii.surovtsev.portfolio.config.EmailProperties
import com.oleksii.surovtsev.portfolio.exception.*
import com.oleksii.surovtsev.portfolio.util.EmailValidator
import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import org.slf4j.LoggerFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class EmailSender(
    private val emailProperties: EmailProperties,
    private val emailValidator: EmailValidator = EmailValidator()
) : EmailService {

    private val logger = LoggerFactory.getLogger(EmailSender::class.java)

    companion object {
        private const val MAX_SUBJECT_LENGTH = 200
        private const val MAX_NAME_LENGTH = 100
        private const val MAX_MESSAGE_LENGTH = 10000
    }

    override fun sendEmail(name: String, email: String, message: String) {
        // Validate inputs
        validateEmailInputs(name, email, message)

        // Validate configuration
        validateConfiguration()

        val from = Email(emailProperties.sender)
        val subject = "Contact Form: Message from $name"
        val to = Email(emailProperties.recipient)

        // Format message with HTML for better readability
        val htmlContent = formatEmailContent(name, email, message)
        val plainContent = "Name: $name\nEmail: $email\nMessage: $message"

        val mail = Mail(from, subject, to, Content("text/plain", plainContent))
        mail.addContent(Content("text/html", htmlContent))

        val sg = SendGrid(emailProperties.apiKey)
        val request = Request()

        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = mail.build()

            val response = sg.api(request)

            handleResponse(response, email)

        } catch (e: SocketTimeoutException) {
            logger.error("Timeout while sending email from: {}", maskEmail(email), e)
            throw EmailNetworkException("Email sending timed out. Please try again later.", e)

        } catch (e: UnknownHostException) {
            logger.error("Cannot resolve SendGrid host", e)
            throw EmailNetworkException("Cannot connect to email service. Please check your internet connection.", e)

        } catch (e: IOException) {
            logger.error("Network error while sending email from: {}", maskEmail(email), e)
            throw EmailNetworkException("Network error occurred while sending email.", e)

        } catch (e: EmailException) {
            // Re-throw our custom exceptions
            throw e

        } catch (e: Exception) {
            logger.error("Unexpected error while sending email from: {}", maskEmail(email), e)
            throw EmailNetworkException("Unexpected error occurred while sending email: ${e.message}", e)
        }
    }

    private fun validateEmailInputs(name: String, email: String, message: String) {
        if (name.isBlank() || email.isBlank() || message.isBlank()) {
            throw EmailValidationException("Name, email, and message are required")
        }

        if (name.length > MAX_NAME_LENGTH) {
            throw EmailValidationException("Name is too long (max $MAX_NAME_LENGTH characters)")
        }

        if (message.length > MAX_MESSAGE_LENGTH) {
            throw EmailValidationException("Message is too long (max $MAX_MESSAGE_LENGTH characters)")
        }

        // Enhanced email validation using EmailValidator
        if (!emailValidator.isValidEmail(email)) {
            throw EmailValidationException("Invalid email format")
        }

        // Check for suspicious content or disposable domains
        when {
            emailValidator.containsSuspiciousContent(email) ->
                throw EmailValidationException("Email contains potentially malicious content")
            emailValidator.isDisposableDomain(emailValidator.extractDomain(email) ?: "") ->
                throw EmailValidationException("Disposable email addresses are not allowed")
        }
    }

    private fun validateConfiguration() {
        if (emailProperties.apiKey.isBlank()) {
            throw EmailConfigurationException("SendGrid API key is not configured")
        }

        if (emailProperties.sender.isBlank()) {
            throw EmailConfigurationException("Sender email is not configured")
        }

        if (emailProperties.recipient.isBlank()) {
            throw EmailConfigurationException("Recipient email is not configured")
        }
    }

    private fun handleResponse(response: com.sendgrid.Response, senderEmail: String) {
        when (response.statusCode) {
            in 200..299 -> {
                logger.info("Email sent successfully from: {} to: {}, status: {}",
                    maskEmail(senderEmail), maskEmail(emailProperties.recipient), response.statusCode)
            }

            400 -> {
                logger.error("Bad request to SendGrid API: {}", response.body)
                throw EmailApiException(response.statusCode, response.body,
                    "Invalid email request. Please check the email addresses.")
            }

            401 -> {
                logger.error("SendGrid authentication failed")
                throw EmailAuthenticationException("Email service authentication failed. Please contact support.")
            }

            403 -> {
                logger.error("SendGrid access forbidden")
                throw EmailAuthenticationException("Email service access denied. Please contact support.")
            }

            413 -> {
                logger.error("Email payload too large")
                throw EmailValidationException("Email content is too large. Please shorten your message.")
            }

            429 -> {
                logger.warn("SendGrid rate limit exceeded")
                val retryAfter = response.headers?.get("X-RateLimit-Reset")?.toIntOrNull()
                throw EmailRateLimitException(retryAfter,
                    "Too many emails sent. Please try again later.")
            }

            500, 502, 503, 504 -> {
                logger.error("SendGrid server error: {}", response.statusCode)
                throw EmailApiException(response.statusCode, response.body,
                    "Email service is temporarily unavailable. Please try again later.")
            }

            else -> {
                logger.warn("Unexpected SendGrid response: {}, body: {}", response.statusCode, response.body)
                throw EmailApiException(response.statusCode, response.body,
                    "Failed to send email. Please try again later.")
            }
        }
    }

    private fun formatEmailContent(name: String, email: String, message: String): String {
        return """
            <html>
                <body style="font-family: Arial, sans-serif; color: #333;">
                    <h2>New Contact Form Submission</h2>
                    <hr>
                    <p><strong>Name:</strong> ${escapeHtml(name)}</p>
                    <p><strong>Email:</strong> ${escapeHtml(email)}</p>
                    <p><strong>Message:</strong></p>
                    <div style="background-color: #f5f5f5; padding: 15px; border-radius: 5px;">
                        ${escapeHtml(message).replace("\n", "<br>")}
                    </div>
                    <hr>
                    <p style="font-size: 0.9em; color: #666;">
                        This email was sent from the portfolio website contact form.
                    </p>
                </body>
            </html>
        """.trimIndent()
    }

    private fun escapeHtml(text: String): String {
        return text
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#x27;")
    }

    private fun maskEmail(email: String): String {
        val parts = email.split("@")
        if (parts.size != 2) return "****"

        val local = parts[0]
        val domain = parts[1]

        return if (local.length > 2) {
            "${local.take(2)}****@$domain"
        } else {
            "****@$domain"
        }
    }
}

// Legacy exception for backward compatibility
@Deprecated("Use specific email exceptions from com.oleksii.surovtsev.portfolio.exception package")
class EmailSendingException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)