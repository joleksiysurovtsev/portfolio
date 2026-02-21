package com.oleksii.surovtsev.portfolio.config

import com.oleksii.surovtsev.portfolio.view.contact.EmailSender
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "email")
data class EmailProperties(
    var sender: String = "",
    var recipient: String = "",
    var apiKey: String = ""
) {
    override fun toString(): String {
        return "EmailProperties(sender='$sender', recipient='$recipient', apiKey='${maskApiKey(apiKey)}')"
    }

    private fun maskApiKey(key: String): String {
        return if (key.length > 8) {
            "${key.take(4)}****${key.takeLast(4)}"
        } else {
            "****"
        }
    }
}

@Component
@ConfigurationProperties(prefix = "github")
data class GitCredentials(
    var githubToken: String = "",
    var githubOwner: String = ""
) {
    override fun toString(): String {
        return "GitCredentials(githubOwner='$githubOwner', githubToken='${maskToken(githubToken)}')"
    }

    private fun maskToken(token: String): String {
        return if (token.length > 8) {
            "${token.take(4)}****${token.takeLast(4)}"
        } else {
            "****"
        }
    }
}

@Configuration
class EmailSenderConfig {

    @Bean
    fun emailSender(emailProperties: EmailProperties): EmailSender {
        return EmailSender(emailProperties)
    }

}