package com.oleksii.surovtsev.portfolio.exception

/**
 * Base exception for email-related errors.
 */
sealed class EmailException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)

/**
 * Exception thrown when email sending fails due to network issues.
 */
class EmailNetworkException(
    message: String = "Failed to send email due to network error",
    cause: Throwable? = null
) : EmailException(message, cause)

/**
 * Exception thrown when email API returns an error response.
 */
class EmailApiException(
    val statusCode: Int,
    val responseBody: String? = null,
    message: String = "Email API returned error status: $statusCode"
) : EmailException(message)

/**
 * Exception thrown when email configuration is invalid.
 */
class EmailConfigurationException(
    message: String = "Invalid email configuration",
    cause: Throwable? = null
) : EmailException(message, cause)

/**
 * Exception thrown when email validation fails.
 */
class EmailValidationException(
    message: String = "Email validation failed",
    cause: Throwable? = null
) : EmailException(message, cause)

/**
 * Exception thrown when rate limit is exceeded.
 */
class EmailRateLimitException(
    val retryAfterSeconds: Int? = null,
    message: String = "Email rate limit exceeded"
) : EmailException(message)

/**
 * Exception thrown for authentication failures.
 */
class EmailAuthenticationException(
    message: String = "Email authentication failed",
    cause: Throwable? = null
) : EmailException(message, cause)