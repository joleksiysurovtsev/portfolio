package com.oleksii.surovtsev.portfolio.util

import org.springframework.stereotype.Component

/**
 * Email validation utility that provides comprehensive email address validation.
 * Uses multiple levels of validation including format, domain, and security checks.
 */
@Component
class EmailValidator {

    companion object {
        // RFC 5322 compliant email regex pattern
        // This pattern allows most valid email addresses while preventing common attacks
        private val EMAIL_PATTERN = Regex(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
        )

        // More restrictive pattern for high-security contexts
        private val STRICT_EMAIL_PATTERN = Regex(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
        )

        // Pattern to detect potentially malicious content in email
        private val SUSPICIOUS_PATTERNS = listOf(
            Regex(".*<script.*", RegexOption.IGNORE_CASE),
            Regex(".*javascript:.*", RegexOption.IGNORE_CASE),
            Regex(".*onclick.*", RegexOption.IGNORE_CASE),
            Regex(".*onerror.*", RegexOption.IGNORE_CASE),
            Regex(".*<iframe.*", RegexOption.IGNORE_CASE),
            Regex(".*data:text/html.*", RegexOption.IGNORE_CASE)
        )

        // Common temporary/disposable email domains to block
        private val DISPOSABLE_DOMAINS = setOf(
            "mailinator.com",
            "guerrillamail.com",
            "10minutemail.com",
            "tempmail.com",
            "throwaway.email",
            "yopmail.com",
            "trashmail.com",
            "getnada.com",
            "temp-mail.org",
            "fakeinbox.com"
        )

        // Maximum reasonable email length per RFC 5321
        private const val MAX_EMAIL_LENGTH = 320
        private const val MAX_LOCAL_PART_LENGTH = 64
        private const val MAX_DOMAIN_LENGTH = 255
    }

    /**
     * Validates an email address using standard RFC 5322 rules.
     *
     * @param email The email address to validate
     * @return true if the email is valid, false otherwise
     */
    fun isValidEmail(email: String?): Boolean {
        if (email.isNullOrBlank()) return false
        if (email.length > MAX_EMAIL_LENGTH) return false

        val trimmedEmail = email.trim().lowercase()

        // Check basic format
        if (!EMAIL_PATTERN.matches(trimmedEmail)) return false

        // Split into local and domain parts
        val parts = trimmedEmail.split("@")
        if (parts.size != 2) return false

        val localPart = parts[0]
        val domain = parts[1]

        // Validate local part
        if (localPart.isEmpty() || localPart.length > MAX_LOCAL_PART_LENGTH) return false
        if (localPart.startsWith(".") || localPart.endsWith(".")) return false
        if (localPart.contains("..")) return false

        // Validate domain
        if (domain.isEmpty() || domain.length > MAX_DOMAIN_LENGTH) return false
        if (domain.startsWith(".") || domain.endsWith(".")) return false
        if (domain.startsWith("-") || domain.endsWith("-")) return false
        if (domain.contains("..")) return false

        // Check for suspicious patterns
        if (containsSuspiciousContent(trimmedEmail)) return false

        return true
    }

    /**
     * Validates an email address using strict rules.
     * More restrictive than standard validation, suitable for high-security contexts.
     *
     * @param email The email address to validate
     * @return true if the email passes strict validation, false otherwise
     */
    fun isValidEmailStrict(email: String?): Boolean {
        if (!isValidEmail(email)) return false

        val trimmedEmail = email!!.trim().lowercase()

        // Apply stricter pattern
        if (!STRICT_EMAIL_PATTERN.matches(trimmedEmail)) return false

        // Check against disposable domains
        val domain = trimmedEmail.substringAfter("@")
        if (isDisposableDomain(domain)) return false

        return true
    }

    /**
     * Checks if an email contains potentially malicious content.
     *
     * @param email The email to check
     * @return true if suspicious content is detected, false otherwise
     */
    fun containsSuspiciousContent(email: String): Boolean {
        return SUSPICIOUS_PATTERNS.any { pattern ->
            pattern.matches(email)
        }
    }

    /**
     * Checks if an email domain is a known disposable/temporary email service.
     *
     * @param domain The domain to check
     * @return true if the domain is disposable, false otherwise
     */
    fun isDisposableDomain(domain: String): Boolean {
        return DISPOSABLE_DOMAINS.contains(domain.lowercase())
    }

    /**
     * Sanitizes an email address by removing potentially dangerous characters
     * while preserving the email format.
     *
     * @param email The email to sanitize
     * @return The sanitized email or null if sanitization fails
     */
    fun sanitizeEmail(email: String?): String? {
        if (email.isNullOrBlank()) return null

        // Remove any whitespace and convert to lowercase
        var sanitized = email.trim().lowercase()

        // Remove any control characters or non-printable characters
        sanitized = sanitized.replace(Regex("[\\p{Cntrl}&&[^\r\n\t]]"), "")

        // Remove any HTML/JavaScript injection attempts
        sanitized = sanitized
            .replace("<", "")
            .replace(">", "")
            .replace("\"", "")
            .replace("'", "")
            .replace("&", "")
            .replace("\n", "")
            .replace("\r", "")
            .replace("\t", "")

        // Validate the sanitized email
        return if (isValidEmail(sanitized)) sanitized else null
    }

    /**
     * Extracts the domain from an email address.
     *
     * @param email The email address
     * @return The domain part or null if invalid
     */
    fun extractDomain(email: String?): String? {
        if (!isValidEmail(email)) return null
        return email!!.trim().lowercase().substringAfter("@")
    }

    /**
     * Extracts the local part (username) from an email address.
     *
     * @param email The email address
     * @return The local part or null if invalid
     */
    fun extractLocalPart(email: String?): String? {
        if (!isValidEmail(email)) return null
        return email!!.trim().lowercase().substringBefore("@")
    }

    /**
     * Validates multiple email addresses (useful for CC/BCC fields).
     *
     * @param emails Comma or semicolon separated email addresses
     * @return List of valid email addresses
     */
    fun validateMultipleEmails(emails: String?): List<String> {
        if (emails.isNullOrBlank()) return emptyList()

        return emails
            .split(Regex("[,;]"))
            .map { it.trim() }
            .filter { isValidEmail(it) }
    }
}