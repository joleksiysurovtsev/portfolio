package com.oleksii.surovtsev.portfolio.util

import org.owasp.html.HtmlPolicyBuilder
import org.owasp.html.PolicyFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class InputSanitizer(
    @Autowired private val emailValidator: EmailValidator
) {

    companion object {
        // Policy for plain text - no HTML allowed
        private val PLAIN_TEXT_POLICY: PolicyFactory = HtmlPolicyBuilder()
            .toFactory()

        // Policy for basic formatting - allows basic text formatting
        private val BASIC_FORMATTING_POLICY: PolicyFactory = HtmlPolicyBuilder()
            .allowElements("b", "i", "em", "strong", "u", "br")
            .toFactory()

        // Email regex pattern for additional validation
        private val EMAIL_PATTERN = Regex(
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"
        )
    }

    /**
     * Sanitizes plain text input by removing all HTML tags
     */
    fun sanitizePlainText(input: String?): String {
        if (input.isNullOrBlank()) return ""

        // First pass: Remove all HTML using OWASP sanitizer
        val sanitized = PLAIN_TEXT_POLICY.sanitize(input.trim())

        // Second pass: Additional protection against script injection
        return sanitized
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("javascript:", "")
            .replace("onclick", "")
            .replace("onerror", "")
            .replace("onload", "")
            .trim()
    }

    /**
     * Sanitizes text that may contain basic formatting
     */
    fun sanitizeFormattedText(input: String?): String {
        if (input.isNullOrBlank()) return ""

        // Allow basic formatting tags only
        return BASIC_FORMATTING_POLICY.sanitize(input.trim())
    }

    /**
     * Validates and sanitizes email address using enhanced validation
     */
    fun sanitizeEmail(email: String?): String {
        if (email.isNullOrBlank()) {
            throw IllegalArgumentException("Email address is required")
        }

        // First, use the EmailValidator to sanitize
        val sanitizedEmail = emailValidator.sanitizeEmail(email)
            ?: throw IllegalArgumentException("Invalid email format - contains invalid characters")

        // Apply additional OWASP sanitization
        val htmlSanitized = PLAIN_TEXT_POLICY.sanitize(sanitizedEmail)

        // Perform strict validation
        if (!emailValidator.isValidEmailStrict(htmlSanitized)) {
            when {
                emailValidator.containsSuspiciousContent(htmlSanitized) ->
                    throw IllegalArgumentException("Email contains potentially malicious content")
                emailValidator.isDisposableDomain(
                    emailValidator.extractDomain(htmlSanitized) ?: ""
                ) ->
                    throw IllegalArgumentException("Disposable email addresses are not allowed")
                else ->
                    throw IllegalArgumentException("Invalid email format")
            }
        }

        return htmlSanitized
    }

    /**
     * Sanitizes name input with length restriction
     */
    fun sanitizeName(name: String?, maxLength: Int = 100): String {
        if (name.isNullOrBlank()) return ""

        val sanitized = sanitizePlainText(name)

        if (sanitized.length > maxLength) {
            return sanitized.take(maxLength)
        }

        return sanitized
    }

    /**
     * Sanitizes message input with length restriction
     */
    fun sanitizeMessage(message: String?, maxLength: Int = 5000): String {
        if (message.isNullOrBlank()) return ""

        val sanitized = sanitizeFormattedText(message)

        if (sanitized.length > maxLength) {
            return sanitized.take(maxLength)
        }

        return sanitized
    }
}