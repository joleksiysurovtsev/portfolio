package com.oleksii.surovtsev.portfolio.config

import com.vaadin.flow.server.ServiceInitEvent
import com.vaadin.flow.server.VaadinServiceInitListener
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Configuration

/**
 * Vaadin configuration for production mode.
 * Applies optimizations when running with production profile.
 */
@Configuration
@ConditionalOnProperty(name = ["vaadin.productionMode"], havingValue = "true")
class VaadinProductionConfiguration : VaadinServiceInitListener {

    private val logger = LoggerFactory.getLogger(VaadinProductionConfiguration::class.java)

    override fun serviceInit(event: ServiceInitEvent) {
        logger.info("Vaadin running in PRODUCTION mode - frontend assets are minified and optimized")

        // Add production-specific configurations
        event.source.addUIInitListener { uiEvent ->
            val ui = uiEvent.ui

            // Add production error handler
            ui.session.setErrorHandler { errorEvent ->
                logger.error("Uncaught UI exception", errorEvent.throwable)
                // Don't expose stack traces to users in production
                ui.access {
                    ui.navigate("error")
                }
            }

            // Set production-specific page configurator
            ui.addBeforeEnterListener { beforeEnterEvent ->
                val page = beforeEnterEvent.ui.page

                // Add performance monitoring
                page.executeJs("""
                    // Log performance metrics
                    if (window.performance && window.performance.timing) {
                        window.addEventListener('load', function() {
                            const timing = window.performance.timing;
                            const loadTime = timing.loadEventEnd - timing.navigationStart;
                            console.log('Page load time: ' + loadTime + 'ms');
                        });
                    }
                """)
            }
        }

        // Configure production session settings
        event.source.addSessionInitListener { sessionInitEvent ->
            // Set production session timeout (30 minutes)
            sessionInitEvent.session.session.maxInactiveInterval = 1800
        }
    }
}

/**
 * Vaadin configuration for development mode.
 * Enables debugging features when not in production.
 */
@Configuration
@ConditionalOnProperty(name = ["vaadin.productionMode"], havingValue = "false", matchIfMissing = true)
class VaadinDevelopmentConfiguration : VaadinServiceInitListener {

    private val logger = LoggerFactory.getLogger(VaadinDevelopmentConfiguration::class.java)

    override fun serviceInit(event: ServiceInitEvent) {
        logger.info("Vaadin running in DEVELOPMENT mode - debugging features enabled")

        // Add development-specific configurations
        event.source.addUIInitListener { uiEvent ->
            val ui = uiEvent.ui

            // More verbose error handling in development
            ui.session.setErrorHandler { errorEvent ->
                logger.error("UI exception in development", errorEvent.throwable)
                // Show detailed error in development
                errorEvent.throwable.printStackTrace()
            }

            // Add development tools
            ui.addBeforeEnterListener { beforeEnterEvent ->
                val page = beforeEnterEvent.ui.page

                // Add development helpers
                page.executeJs("""
                    // Enable Vue devtools for Vaadin debugging
                    if (window.__VUE_DEVTOOLS_GLOBAL_HOOK__) {
                        console.log('Development mode: Vue DevTools available');
                    }

                    // Log all Vaadin requests in development
                    console.log('Vaadin Development Mode Active');
                """)
            }
        }
    }
}