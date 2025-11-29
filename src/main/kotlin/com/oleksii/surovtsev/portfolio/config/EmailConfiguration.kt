package com.oleksii.surovtsev.portfolio.config

import com.oleksii.surovtsev.portfolio.view.contact.EmailSender
import com.oleksii.surovtsev.portfolio.view.contact.EmailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class for email-related beans.
 */
@Configuration
class EmailConfiguration {

    /**
     * Creates an EmailService bean.
     *
     * @param emailProperties Email configuration properties
     * @return EmailService implementation
     */
    @Bean
    fun emailService(emailProperties: EmailProperties): EmailService {
        return EmailSender(emailProperties)
    }
}
