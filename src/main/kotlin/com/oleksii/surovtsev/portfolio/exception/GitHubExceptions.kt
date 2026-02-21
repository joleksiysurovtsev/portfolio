package com.oleksii.surovtsev.portfolio.exception

/**
 * Base exception for GitHub API-related errors.
 */
sealed class GitHubException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)

/**
 * Exception thrown when GitHub API call fails.
 */
class GitHubApiException(
    val statusCode: Int? = null,
    val responseBody: String? = null,
    message: String = "GitHub API error${statusCode?.let { ": status $it" } ?: ""}"
) : GitHubException(message)

/**
 * Exception thrown when GitHub rate limit is exceeded.
 */
class GitHubRateLimitException(
    val remaining: Int = 0,
    val resetAt: Long? = null,
    message: String = "GitHub API rate limit exceeded"
) : GitHubException(message)

/**
 * Exception thrown for network-related GitHub API failures.
 */
class GitHubNetworkException(
    message: String = "Network error while calling GitHub API",
    cause: Throwable? = null
) : GitHubException(message, cause)

/**
 * Exception thrown when GitHub authentication fails.
 */
class GitHubAuthenticationException(
    message: String = "GitHub authentication failed",
    cause: Throwable? = null
) : GitHubException(message, cause)

/**
 * Exception thrown when GitHub data parsing fails.
 */
class GitHubDataException(
    message: String = "Failed to parse GitHub response",
    cause: Throwable? = null
) : GitHubException(message, cause)