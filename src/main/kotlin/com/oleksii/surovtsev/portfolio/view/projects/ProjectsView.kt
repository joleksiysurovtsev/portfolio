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

@Route(value = "/projects", layout = MainLayout::class)
class ProjectsView(val gitCredentials: GitCredentials) : VerticalLayout() {

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
            } catch (e: Exception) {
                println("Error while retrieving information: ${e.message}")
            }
        }
        return projectsLayout
    }
}


