package com.oleksii.surovtsev.portfolio

import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.server.PWA
import com.vaadin.flow.theme.Theme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@PWA(name = "Project Base for Vaadin with Spring", shortName = "Project Base")
@Theme(value="my-theme")
class PortfolioApplication: AppShellConfigurator

fun main(args: Array<String>) {
    runApplication<PortfolioApplication>(*args)
}
