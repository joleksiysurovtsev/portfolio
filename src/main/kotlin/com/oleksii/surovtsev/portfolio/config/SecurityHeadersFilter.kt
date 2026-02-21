package com.oleksii.surovtsev.portfolio.config

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

/**
 * Simple filter to add security headers to all responses.
 * This is a lightweight alternative to Spring Security for a public portfolio site.
 */
@Component
@Order(1)
class SecurityHeadersFilter : Filter {

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        val httpResponse = response as HttpServletResponse

        // Prevent clickjacking attacks
        httpResponse.setHeader("X-Frame-Options", "DENY")

        // Prevent MIME type sniffing
        httpResponse.setHeader("X-Content-Type-Options", "nosniff")

        // Enable XSS protection (though modern browsers have this by default)
        httpResponse.setHeader("X-XSS-Protection", "1; mode=block")

        // Control referrer information
        httpResponse.setHeader("Referrer-Policy", "strict-origin-when-cross-origin")

        // Content Security Policy - adjust based on your needs
        httpResponse.setHeader("Content-Security-Policy",
            "default-src 'self'; " +
            "script-src 'self' 'unsafe-inline' 'unsafe-eval'; " +  // Vaadin requires unsafe-inline and unsafe-eval
            "style-src 'self' 'unsafe-inline'; " +  // Vaadin requires unsafe-inline for styles
            "img-src 'self' data: https:; " +
            "font-src 'self' data:; " +
            "connect-src 'self' ws: wss: https://api.github.com; " +  // WebSocket for Vaadin, GitHub API
            "frame-src 'none'; " +
            "object-src 'none'; " +
            "base-uri 'self'; " +
            "form-action 'self';"
        )

        // Restrict browser features
        httpResponse.setHeader("Permissions-Policy",
            "camera=(), microphone=(), geolocation=(), payment=()"
        )

        // HSTS - only in production (when using HTTPS)
        // Uncomment this when deploying with HTTPS
        // httpResponse.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains")

        chain.doFilter(request, response)
    }
}