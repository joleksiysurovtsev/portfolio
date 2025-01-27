package com.oleksii.surovtsev.portfolio.view.experience

import com.flowingcode.vaadin.addons.carousel.Carousel
import com.flowingcode.vaadin.addons.carousel.Slide
import com.oleksii.surovtsev.portfolio.components.CustomDivider
import com.oleksii.surovtsev.portfolio.entity.Certification
import com.oleksii.surovtsev.portfolio.entity.ExperiencePart
import com.oleksii.surovtsev.portfolio.entity.ExperiencePeriod
import com.oleksii.surovtsev.portfolio.entity.SkillBarInfo
import com.oleksii.surovtsev.portfolio.entity.enums.SkillType
import com.oleksii.surovtsev.portfolio.util.UtilFileManager
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout

class ExperienceAndSkillsBlock : VerticalLayout() {

    init {
        val textLayout = VerticalLayout().apply {
            addClassName("experience-text")
            add(H1("Experience"))
            add(CustomDivider("0%", "0%"))
            add(H5("4 Years of Experience"))
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
        val experienceItems: List<ExperiencePart> = UtilFileManager.getDataFromJson("experience-parts.json")
        return VerticalLayout().apply {
            setId("timeline")
            addClassName("timeline-section")
            isSpacing = true
            isPadding = false

            experienceItems.forEach { experience: ExperiencePart ->
                val itemLayout = VerticalLayout().apply {
                    addClassName("timeline-item")
                    isSpacing = false
                    isPadding = false
                    val title = H3(experience.title)
                    this.add(title)
                    add(TimeLinePeriod(experience.period))
                    experience.experienceDescription.listDescription.forEach { line ->
                        add(Paragraph("• $line").apply { addClassName("timeline-text") })
                    }

                    val badges: List<BadgePillIcons> = experience.experienceDescription.badges.map { badge -> badge.toBadgePillIcon() }
                    add(ExperienceBadges(badges))
                }
                add(itemLayout)
            }
        }
    }

    class ExperienceBadges(badges: List<BadgePillIcons>) : HorizontalLayout() {
        init {
                isSpacing = false
                isPadding = false
                addClassName("badge-container")
                add(badges)
        }
    }

    class TimeLinePeriod(period: ExperiencePeriod): VerticalLayout() {
        init {
            isSpacing = false
            isPadding = false
            add(H5("${period.start} - ${period.end}"))
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

            add(
                H3("My certificates").apply {
                    addClassName("soft-skills-section-header")
                }
            )

            add(createCertificationSlider())

        }
    }

    private fun createCertificationSlider(): VerticalLayout {
        val certifications: List<Certification> = UtilFileManager.getDataFromJson("certification.json")

        val slides = certifications.map { certification ->
            Slide(
                VerticalLayout().apply {
                    alignItems = FlexComponent.Alignment.CENTER
                    justifyContentMode = FlexComponent.JustifyContentMode.START
                    isSpacing = true
                    isPadding = false
                    setWidthFull()

                    add(
                        Image(certification.imageUrl, certification.title).apply {
                            alignItems = FlexComponent.Alignment.CENTER
                            addClassName("slide-image")
                        }
                    )
                }
            )
        }

        return VerticalLayout().apply {
            alignItems = FlexComponent.Alignment.CENTER
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            isSpacing = false
            isPadding = false
            setWidthFull()

            val carousel = Carousel(*slides.toTypedArray()).apply {
                addClassName("custom-carousel-style")
                setWidth("90%")
                setHeight("400px")
                isAutoProgress = true
            }

            add(carousel)
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