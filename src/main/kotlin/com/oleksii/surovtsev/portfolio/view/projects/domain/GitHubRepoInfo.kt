package com.oleksii.surovtsev.portfolio.view.projects.domain

import com.oleksii.surovtsev.portfolio.view.projects.ProjectCard

data class GitHubRepoInfo(
    val id: String? = null,
    val name: String,
    val description: String? = null,
    val url: String? = null,
    val openGraphImageUrl: String? = null,
    val stargazerCount: Long? = null,
    val repositoryTopics: RepositoryTopics? = null,
) {
    /**
     * Converts GitHub repository information to a ProjectCard for UI display.
     *
     * @return ProjectCard with sanitized data
     * @throws IllegalStateException if repository URL is missing (required field)
     */
    fun toProjectCard(): ProjectCard {
        // Validate required fields
        require(name.isNotBlank()) { "Repository name cannot be blank" }
        requireNotNull(url) { "Repository URL is required but was null for repository: $name" }

        return ProjectCard(
            title = name.trim(),
            description = description?.trim()?.takeIf { it.isNotBlank() } ?: "No description available",
            technologies = repositoryTopics?.nodes?.mapNotNull { it.topic?.name?.trim() }?.filter { it.isNotBlank() } ?: emptyList(),
            repositoryUrl = url,
            imageUrl = openGraphImageUrl?.takeIf { it.isNotBlank() }
        )
    }
}