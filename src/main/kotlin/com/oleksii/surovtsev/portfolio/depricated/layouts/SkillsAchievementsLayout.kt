package com.oleksii.surovtsev.portfolio.depricated.layouts

import com.oleksii.surovtsev.portfolio.depricated.builders.HeaderBuilder
import com.vaadin.flow.component.AttachEvent
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.html.H4
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.RouterLayout

class SkillsAchievementsLayout : VerticalLayout(), RouterLayout {

    private val headerBuilder: HeaderBuilder by lazy { HeaderBuilder() }
//    private val footerBuilder: FooterBuilder by lazy { FooterBuilder() }

    init {
        width = "100%"
        height = "100vh"
        defaultHorizontalComponentAlignment = Alignment.CENTER
        val header: VerticalLayout = headerBuilder.createHeader()
        val content: VerticalLayout = createContent()
//        val footer: VerticalLayout = footerBuilder.createFooter()
        add(header)
        addAndExpand(content)
//        add(footer)
    }

    private fun createContent(): VerticalLayout {
        val content = VerticalLayout().apply {
            defaultHorizontalComponentAlignment = Alignment.START
            isSpacing = false
            isPadding = true
            addClassName("skills-achievements")
        }
        // partition Experience
        val experienceSection = createExperienceSection()
        content.add(experienceSection)

        // partition Education
        val educationSection = createEducationSection()
        content.add(educationSection)

        return content
    }

    private fun createEducationSection(): VerticalLayout {
        return createTimelineSection(
            "Education",
            listOf(
                Triple("SPD-University", "March 2020 – April 2021", "Professional Programming Courses, specializing in Java for Web Development\n" +
                        "I gained practical experience in building scalable web applications and deepened my knowledge of Java technologies."),
                Triple("Kyiv National University of Technology and Design", "2013 – 2017", "I pursued my Specialist degree in Computer System Programming, focusing on advanced technologies and software development."),
            ),
            "book_icon.svg"
        ).apply {
            element.setAttribute("id", "VerticalLayoutEducation")
            addClassName("edu-ach-card")
        }
    }

    private fun createExperienceSection(): VerticalLayout {
        return createTimelineSection(
            "Experience",
            listOf(
                Triple(
                    "SPD Technology Java Developer (Full-time)",
                    "May 2021 – Present (3 years 9 months)",
                    listOf(
                        "Develop and maintain scalable, high-performance Java-based applications.",
                        "Collaborate with cross-functional teams to design and implement solutions aligned with business goals.",
                        "Optimize and refactor existing codebases to improve maintainability and performance.",
                        "Actively participate in code reviews, ensuring high-quality standards and best practices."
                    ),
                ),
            ),"expir.svg"
        ).apply {
            element.setAttribute("id", "VerticalLayoutExperience")
            addClassName("edu-ach-card")
        }
    }

    private fun createTimelineSection(title: String, items: List<Triple<String, String, Any>>, iconName: String): VerticalLayout {
        return VerticalLayout().apply {
            addClassName("timeline-section")
            isSpacing = true
            isPadding = true

            // section header
            val titleWrapper = HorizontalLayout().apply {
                addClassName("title-wrapper")

                val svgContent = loadSvg(iconName)
                val svgIcon = createSvgIcon(svgContent)
                val iconBox = Div(svgIcon).apply { addClassName("icon-box") }

                val titleLabel = H3(title)
                add(iconBox, titleLabel)
                defaultVerticalComponentAlignment = Alignment.START
                alignItems = Alignment.START
            }
            add(titleWrapper)

            // list of elements
            items.forEach { (itemTitle, itemTime, itemDescription) ->
                val itemLayout = VerticalLayout().apply {
                    addClassName("timeline-item")
                    isSpacing = false
                    isPadding = false

                    // Add a heading and time
                    add(H4(itemTitle).apply { addClassName("timeline-item-title") })
                    add(Div().apply { text = itemTime; addClassName("timeline-item-time") })

                    // Processing the third element as a string array
                    if (itemDescription is List<*>) {
                        itemDescription.forEach { line ->
                            if (line is String) {
                                add(Paragraph("• $line").apply { addClassName("timeline-text") })
                            }
                        }
                    } else if (itemDescription is String) {
                        // If it is a regular string, add it as a paragraph
                        add(Paragraph(itemDescription).apply { addClassName("timeline-text") })
                    }
                }
                add(itemLayout)
            }
        }
    }

    override fun onAttach(attachEvent: AttachEvent) {
        super.onAttach(attachEvent)
        super.addClassName("fade-in")
        attachEvent.ui.page.executeJs("""
            const savedTheme = localStorage.getItem('theme') || 'light';
            document.documentElement.setAttribute('theme', savedTheme);
        """)
    }

    private fun loadSvg(fileName: String): String {
        val resource = javaClass.getResourceAsStream("/static/icons/$fileName")
            ?: throw IllegalArgumentException("Icon file not found: $fileName")
        return resource.bufferedReader().use { it.readText() }
    }

    private fun createSvgIcon(svgContent: String): Div {
        val div = Div()
        div.element.setProperty("innerHTML", svgContent)
        div.addClassName("svg-icon")
        return div
    }
}