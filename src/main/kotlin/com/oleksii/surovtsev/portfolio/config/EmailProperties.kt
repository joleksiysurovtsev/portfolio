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
)

@Configuration
class EmailSenderConfig {

    @Bean
    fun emailSender(emailProperties: EmailProperties): EmailSender {
        return EmailSender(emailProperties)
    }

}