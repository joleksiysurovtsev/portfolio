package com.oleksii.surovtsev.portfolio.routes

import com.oleksii.surovtsev.portfolio.layouts.MainLayout
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route


@Route(value = "", layout = MainLayout::class)
@PageTitle("Main Page")
class MainPage : Div()
