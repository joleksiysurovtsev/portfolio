package com.oleksii.surovtsev.portfolio.config

/**
 * Centralized configuration for application routes.
 * This prevents hardcoding routes throughout the codebase and makes them easier to maintain.
 */
object RoutesConfig {

    /**
     * Main navigation routes
     */
    object Main {
        const val HOME = ""
        const val EXPERIENCE = "/experience"
        const val PROJECTS = "/projects"
        const val BLOG = "/blog"
        const val CONTACT = "/contact"
    }

    /**
     * Project-specific routes
     */
    object Projects {
        const val CLAUDE_REVIEW_PLUGIN = "projects/claude-review-plugin"
    }

    /**
     * API routes
     */
    object Api {
        const val CV_DOWNLOAD = "/api/cv/download"
        const val CV_DATA = "/api/cv/data"
    }

    /**
     * Error pages
     */
    object Error {
        const val FORBIDDEN = "/error/403"
        const val NOT_FOUND = "/error/404"
        const val INTERNAL_ERROR = "/error/500"
    }

    /**
     * Helper method to get route by name for dynamic navigation
     */
    fun getRouteByName(routeName: String): String? {
        return when (routeName.uppercase()) {
            "HOME" -> Main.HOME
            "EXPERIENCE" -> Main.EXPERIENCE
            "PROJECTS" -> Main.PROJECTS
            "BLOG" -> Main.BLOG
            "CONTACT" -> Main.CONTACT
            "CLAUDE_REVIEW_PLUGIN" -> Projects.CLAUDE_REVIEW_PLUGIN
            else -> null
        }
    }

    /**
     * Get display name for a route
     */
    fun getDisplayName(route: String): String {
        return when (route) {
            Main.HOME -> "Home"
            Main.EXPERIENCE -> "Experience"
            Main.PROJECTS -> "Projects"
            Main.BLOG -> "Blog"
            Main.CONTACT -> "Contact"
            Projects.CLAUDE_REVIEW_PLUGIN -> "Claude Review Plugin"
            else -> "Unknown"
        }
    }

    /**
     * Check if a route requires authentication (for future use)
     */
    fun isPublicRoute(route: String): Boolean {
        // Currently all routes are public
        return true
    }
}