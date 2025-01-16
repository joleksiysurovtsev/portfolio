package com.oleksii.surovtsev.portfolio.routes

import com.oleksii.surovtsev.portfolio.layouts.SkillsAchievementsLayout
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Route(value = "skills-achievements", layout = SkillsAchievementsLayout::class)
@PageTitle("Skills & Achievements")
class SkillsAchievementsPage : Div()