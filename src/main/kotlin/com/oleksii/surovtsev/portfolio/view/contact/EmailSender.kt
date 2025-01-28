package com.oleksii.surovtsev.portfolio.view.contact

import com.oleksii.surovtsev.portfolio.config.EmailProperties
import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email

class EmailSender(private val emailProperties: EmailProperties) {

    fun sendEmail(name: String, email: String, message: String) {
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
            println("Status Code: ${response.statusCode}")
            println("Body: ${response.body}")
            println("Headers: ${response.headers}")
        } catch (e: Exception) {
            println("Error sending email: ${e.message}")
        }
    }
}