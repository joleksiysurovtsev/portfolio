package com.oleksii.surovtsev.portfolio.mainpage

import com.oleksii.surovtsev.portfolio.MainLayout


import com.vaadin.flow.component.html.Div
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Route(value = "", layout = MainLayout::class)
@PageTitle("Main Page")
class MainPage : Div() {

    init {
        text = "This is the content of the main page."
    }
}
