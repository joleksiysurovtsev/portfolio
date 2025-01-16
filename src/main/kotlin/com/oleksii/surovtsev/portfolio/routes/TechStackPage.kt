package com.oleksii.surovtsev.portfolio.routes

import com.oleksii.surovtsev.portfolio.layouts.TechStackLayout
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Route(value = "tech-stack", layout = TechStackLayout::class)
@PageTitle("Tech Stack")
class TechStackPage : Div()