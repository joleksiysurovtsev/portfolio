package com.oleksii.surovtsev.portfolio.view.experience

import com.oleksii.surovtsev.portfolio.components.CustomDivider
import com.oleksii.surovtsev.portfolio.entity.ExperiencePart
import com.oleksii.surovtsev.portfolio.entity.SkillBarInfo
import com.oleksii.surovtsev.portfolio.entity.SkillType
import com.oleksii.surovtsev.portfolio.util.UtilFileManager
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout

class ExperienceAndSkillsBlock : VerticalLayout() {

    init {
        val textLayout = VerticalLayout().apply {
            add(H2("Experience"))
            add(CustomDivider("0%", "0%"))

            add(H5("4 Years of Experience").apply {
                style.set("margin", "0")
                style.set("opacity", "0.7")
                style.set("align-self", "flex-end")
            })

        }

        val horizont = FlexLayout().apply {
            setWidthFull()
            flexDirection = FlexLayout.FlexDirection.ROW
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            alignItems = FlexComponent.Alignment.START
            addClassName("experience-skills-container")

            val experienceSection = createExperienceSection().apply {
                addClassName("experience-section")
            }

            val skillsSection = createSkillsSelection().apply {
                addClassName("skills-section")
            }

            add(experienceSection, skillsSection)
        }
        add(textLayout, horizont)
    }

    private fun createExperienceSection(): VerticalLayout {
        val experienceParts: List<ExperiencePart> = UtilFileManager.getDataFromJson("experience-parts.json")
        return createTimelineSection(experienceParts)
    }

    private fun createTimelineSection(experienceItems: List<ExperiencePart>): VerticalLayout {
        return VerticalLayout().apply {
            setId("timeline")
            addClassName("timeline-section")
            isSpacing = true
            isPadding = true

            experienceItems.forEach { experience: ExperiencePart ->
                val itemLayout = VerticalLayout().apply {
                    addClassName("timeline-item")
                    isSpacing = false
                    isPadding = false

                    val title = H4(experience.title).apply { addClassName("timeline-item-title") }
                    this.add(title)
                    val period = Div().apply {
                        text = "${experience.period.start} - ${experience.period.end}"
                    }
                    add(period)
                    experience.experienceDescription.listDescription.forEach { line ->
                        add(Paragraph("• $line").apply { addClassName("timeline-text") })
                    }

                    val badges = experience.experienceDescription.badges.map { badge -> badge.toBadgePillIcon() }
                    val badgesBlock = HorizontalLayout().apply {
                        add(badges)
                    }
                    add(badgesBlock)
                }
                add(itemLayout)
            }
        }
    }

    private fun createSkillsSelection(): VerticalLayout {
        return VerticalLayout().apply {
            addClassName("skills-section")
            isSpacing = true
            isPadding = false

            add(
                H3("Coding Skills").apply {
                    addClassName("coding-skills-section-header")
                }
            )

            val skills: List<SkillBarInfo> = UtilFileManager.getDataFromJson("skill-barr-info.json")

            skills.filter { it.type == SkillType.CODING }.forEach { (skill, percentage) ->
                add(createSkillBar(skill, percentage))
            }

            add(
                H3("Soft Skills").apply {
                    addClassName("soft-skills-section-header")
                }
            )

            skills.filter { it.type == SkillType.SOFT }.forEach { (skill, percentage) ->
                add(createSkillBar(skill, percentage))
            }
        }
    }


    private fun createSkillBar(skill: String, percentage: Int): VerticalLayout {
        return VerticalLayout().apply {
            isSpacing = false
            isPadding = false
            style.set("margin-bottom", "8px")

            val skillHeader = HorizontalLayout().apply {
                setWidthFull()
                addClassName("skill-bar-header")
                justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN
                add(
                    Span(skill).apply {
                        addClassName("progress-bar-text")
                    },
                    Span("$percentage%").apply {
                        addClassName("progress-bar-percentage")
                    }
                )
            }

            val progressBar = Div().apply {
                className = "progress-bar-container"
                add(Div().apply {
                    className = "progress-bar-fill"
                    style.set("--progress", "$percentage%")
                })
            }

            add(skillHeader, progressBar)
        }
    }
}