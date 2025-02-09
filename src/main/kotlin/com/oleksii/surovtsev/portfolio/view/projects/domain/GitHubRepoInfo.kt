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
    fun toProjectCard(): ProjectCard {
        return ProjectCard(
            title = this.name,
            description = this.description,
            technologies = this.repositoryTopics?.nodes?.mapNotNull { it.topic?.name } ?: emptyList(),
            repositoryUrl = this.url,
//            demoUrl = this.url,
            imageUrl = this.openGraphImageUrl
        )
    }
}