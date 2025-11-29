package com.oleksii.surovtsev.portfolio.view.projects

import com.oleksii.surovtsev.portfolio.components.CustomDividerH2
import com.oleksii.surovtsev.portfolio.config.GitCredentials
import com.oleksii.surovtsev.portfolio.layout.MainLayout
import com.oleksii.surovtsev.portfolio.util.UtilFileManager
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route
import org.slf4j.LoggerFactory
import java.io.IOException

@Route(value = "/projects", layout = MainLayout::class)
class ProjectsView(val gitCredentials: GitCredentials) : VerticalLayout() {

    private val logger = LoggerFactory.getLogger(ProjectsView::class.java)

    init {
        setWidthFull()
        justifyContentMode = FlexComponent.JustifyContentMode.START
        alignItems = Alignment.CENTER
        addClassName("projects-view")
        add(
            CustomDividerH2("MY PROJECTS"),
            createProjectsList()
        )
    }

    private fun createProjectsList(): FlexLayout {
        val projectsLayout = FlexLayout().apply {
            setWidthFull()
            flexWrap = FlexLayout.FlexWrap.WRAP
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            alignItems = Alignment.CENTER
        }

        val gitRepositories: List<String> = UtilFileManager.getDataFromJson("git-repo.json")
        gitRepositories.forEach { repoName ->
            try {
                val projectCard = GitHubGraphQLService.getRepoInfo(gitCredentials, repoName).toProjectCard()
                projectsLayout.add(projectCard)
                logger.debug("Successfully loaded project: {}", repoName)
            } catch (e: IOException) {
                logger.warn("Failed to fetch repository info for: {}. Network error: {}", repoName, e.message)
            } catch (e: IllegalStateException) {
                logger.warn("Failed to create project card for: {}. Invalid data: {}", repoName, e.message)
            } catch (e: Exception) {
                logger.error("Unexpected error fetching repository: {}", repoName, e)
            }
        }

        if (projectsLayout.componentCount == 0) {
            logger.warn("No projects were loaded successfully")
        } else {
            logger.info("Loaded {} projects out of {}", projectsLayout.componentCount, gitRepositories.size)
        }

        return projectsLayout
    }
}


