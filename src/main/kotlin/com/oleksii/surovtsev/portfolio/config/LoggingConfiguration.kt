package com.oleksii.surovtsev.portfolio.config

import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.classic.spi.ILoggingEvent
import org.springframework.context.annotation.Configuration
import java.util.regex.Pattern

/**
 * Custom logging configuration to mask sensitive data in logs
 */
@Configuration
class LoggingConfiguration {

    /**
     * Custom PatternLayout that masks sensitive data in log messages
     */
    class MaskingPatternLayout : PatternLayout() {

        companion object {
            // Patterns for sensitive data
            private val PATTERNS = listOf(
                // API Keys and tokens
                Pattern.compile("(api[_\\-]?key[\"']?[:\\s=]+[\"']?)([^\"'\\s]+)([\"']?)", Pattern.CASE_INSENSITIVE),
                Pattern.compile("(token[\"']?[:\\s=]+[\"']?)([^\"'\\s]+)([\"']?)", Pattern.CASE_INSENSITIVE),
                Pattern.compile("(bearer\\s+)([^\\s]+)", Pattern.CASE_INSENSITIVE),

                // SendGrid specific
                Pattern.compile("(SG\\.)([A-Za-z0-9_\\-]+)", Pattern.CASE_INSENSITIVE),

                // GitHub tokens
                Pattern.compile("(ghp_)([A-Za-z0-9]+)", Pattern.CASE_INSENSITIVE),
                Pattern.compile("(gho_)([A-Za-z0-9]+)", Pattern.CASE_INSENSITIVE),
                Pattern.compile("(github_pat_)([A-Za-z0-9_]+)", Pattern.CASE_INSENSITIVE),

                // Passwords
                Pattern.compile("(password[\"']?[:\\s=]+[\"']?)([^\"'\\s]+)([\"']?)", Pattern.CASE_INSENSITIVE),
                Pattern.compile("(pwd[\"']?[:\\s=]+[\"']?)([^\"'\\s]+)([\"']?)", Pattern.CASE_INSENSITIVE),

                // Database URLs with credentials
                Pattern.compile("(postgresql://[^:]+:)([^@]+)(@)", Pattern.CASE_INSENSITIVE),
                Pattern.compile("(mysql://[^:]+:)([^@]+)(@)", Pattern.CASE_INSENSITIVE),

                // Email addresses in certain contexts
                Pattern.compile("(email[\"']?[:\\s=]+[\"']?)([^\"'\\s]+@[^\"'\\s]+)([\"']?)", Pattern.CASE_INSENSITIVE),

                // Credit card numbers (basic pattern)
                Pattern.compile("\\b([0-9]{4})[\\s\\-]?([0-9]{4})[\\s\\-]?([0-9]{4})[\\s\\-]?([0-9]{4})\\b")
            )
        }

        override fun doLayout(event: ILoggingEvent): String {
            val message = super.doLayout(event)
            return maskSensitiveData(message)
        }

        private fun maskSensitiveData(message: String): String {
            var maskedMessage = message

            for (pattern in PATTERNS) {
                val matcher = pattern.matcher(maskedMessage)
                val sb = StringBuffer()

                while (matcher.find()) {
                    val replacement = when (matcher.groupCount()) {
                        1 -> "****" // Simple pattern
                        2 -> matcher.group(1) + maskValue(matcher.group(2))
                        3 -> matcher.group(1) + maskValue(matcher.group(2)) + matcher.group(3)
                        4 -> {
                            // Credit card pattern
                            if (matcher.group(1).all { it.isDigit() }) {
                                matcher.group(1) + " **** **** " + matcher.group(4)
                            } else {
                                matcher.group(0) // Return original if not matching expected format
                            }
                        }
                        else -> matcher.group(0)
                    }
                    matcher.appendReplacement(sb, replacement)
                }
                matcher.appendTail(sb)
                maskedMessage = sb.toString()
            }

            return maskedMessage
        }

        private fun maskValue(value: String): String {
            return when {
                value.length <= 4 -> "****"
                value.length <= 8 -> "${value.take(2)}****"
                else -> "${value.take(4)}****${value.takeLast(4)}"
            }
        }
    }
}