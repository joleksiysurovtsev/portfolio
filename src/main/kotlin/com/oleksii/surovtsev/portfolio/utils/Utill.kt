package com.oleksii.surovtsev.portfolio.utils

import com.vaadin.flow.component.UI

fun applyTheme(ui: UI) {
    ui.page.executeJs("""
        const savedTheme = localStorage.getItem('theme') || 'light';
        document.documentElement.setAttribute('theme', savedTheme);
    """)
}

