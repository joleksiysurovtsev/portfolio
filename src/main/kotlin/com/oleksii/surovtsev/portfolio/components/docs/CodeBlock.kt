package com.oleksii.surovtsev.portfolio.components.docs

import com.vaadin.flow.component.ClickEvent
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.ComponentEventListener
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Pre
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.notification.NotificationVariant

/**
 * Code block component with syntax highlighting and copy functionality
 */
class CodeBlock(
    private val code: String,
    private val language: String = "text",
    private val fileName: String? = null
) : Div() {

    private val copyButton: Button
    private val codeContent: Pre

    init {
        addClassName("code-block")
        setWidthFull()

        // Create header if fileName is provided
        if (fileName != null) {
            val header = Div().apply {
                addClassName("code-block-header")
                val fileNameSpan = Span(fileName).apply {
                    addClassName("code-block-filename")
                }
                add(fileNameSpan)
            }
            add(header)
        }

        // Create code container
        val codeContainer = Div().apply {
            addClassName("code-block-container")
            addClassName("language-$language")

            // Create code content
            codeContent = Pre().apply {
                addClassName("code-block-content")
                element.setProperty("innerHTML", escapeAndHighlight(code, language))
            }

            // Create copy button
            copyButton = Button(Icon(VaadinIcon.COPY)).apply {
                addClassName("code-block-copy-button")
                addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL)
                element.setAttribute("title", "Copy to clipboard")

                addClickListener(ComponentEventListener<ClickEvent<Button>> {
                    copyToClipboard()
                })
            }

            add(codeContent, copyButton)
        }

        add(codeContainer)
    }

    private fun copyToClipboard() {
        // Execute JavaScript to copy code to clipboard
        element.executeJs(
            """
            const text = $0;
            if (navigator.clipboard && window.isSecureContext) {
                navigator.clipboard.writeText(text).then(() => {
                    $1.${'$'}server.notifyCopySuccess();
                }).catch(() => {
                    $1.${'$'}server.notifyCopyError();
                });
            } else {
                // Fallback for older browsers
                const textArea = document.createElement("textarea");
                textArea.value = text;
                textArea.style.position = "fixed";
                textArea.style.left = "-999999px";
                document.body.appendChild(textArea);
                textArea.select();
                try {
                    document.execCommand('copy');
                    $1.${'$'}server.notifyCopySuccess();
                } catch (error) {
                    $1.${'$'}server.notifyCopyError();
                } finally {
                    document.body.removeChild(textArea);
                }
            }
            """,
            code,
            element
        )
    }

    @com.vaadin.flow.component.ClientCallable
    fun notifyCopySuccess() {
        // Change button icon temporarily
        copyButton.icon = Icon(VaadinIcon.CHECK)
        copyButton.element.setAttribute("title", "Copied!")

        // Show notification
        Notification.show("Code copied to clipboard", 2000, Notification.Position.BOTTOM_CENTER).apply {
            addThemeVariants(NotificationVariant.LUMO_SUCCESS)
        }

        // Reset icon after delay
        ui.ifPresent { ui ->
            ui.access {
                Thread.sleep(2000)
                ui.access {
                    copyButton.icon = Icon(VaadinIcon.COPY)
                    copyButton.element.setAttribute("title", "Copy to clipboard")
                }
            }
        }
    }

    @com.vaadin.flow.component.ClientCallable
    fun notifyCopyError() {
        Notification.show("Failed to copy code", 3000, Notification.Position.BOTTOM_CENTER).apply {
            addThemeVariants(NotificationVariant.LUMO_ERROR)
        }
    }

    /**
     * Basic syntax highlighting for different languages
     */
    private fun escapeAndHighlight(code: String, language: String): String {
        // First escape HTML
        val escaped = code
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")

        // Apply basic syntax highlighting based on language
        return when (language.lowercase()) {
            "kotlin", "java" -> highlightKotlinJava(escaped)
            "groovy" -> highlightGroovy(escaped)
            "yaml", "yml" -> highlightYaml(escaped)
            "bash", "sh" -> highlightBash(escaped)
            "json" -> highlightJson(escaped)
            else -> escaped
        }
    }

    private fun highlightKotlinJava(code: String): String {
        var highlighted = code

        // Keywords
        val keywords = listOf(
            "fun", "val", "var", "class", "object", "interface", "package", "import",
            "if", "else", "when", "for", "while", "do", "return", "break", "continue",
            "private", "public", "protected", "internal", "override", "abstract", "open",
            "sealed", "data", "enum", "companion", "const", "lateinit", "lazy",
            "apply", "let", "also", "run", "with", "inline", "reified"
        )
        keywords.forEach { keyword ->
            highlighted = highlighted.replace(
                Regex("\\b$keyword\\b"),
                "<span class=\"code-keyword\">$keyword</span>"
            )
        }

        // Strings
        highlighted = highlighted.replace(
            Regex("\"([^\"]*)\""),
            "<span class=\"code-string\">\"$1\"</span>"
        )

        // Comments
        highlighted = highlighted.replace(
            Regex("//(.*)$", RegexOption.MULTILINE),
            "<span class=\"code-comment\">//$1</span>"
        )

        return highlighted
    }

    private fun highlightGroovy(code: String): String {
        var highlighted = code

        // Keywords
        val keywords = listOf(
            "def", "class", "interface", "extends", "implements", "import", "package",
            "if", "else", "for", "while", "switch", "case", "return", "break",
            "buildscript", "repositories", "dependencies", "plugins", "apply",
            "classpath", "id", "version"
        )
        keywords.forEach { keyword ->
            highlighted = highlighted.replace(
                Regex("\\b$keyword\\b"),
                "<span class=\"code-keyword\">$keyword</span>"
            )
        }

        // Strings
        highlighted = highlighted.replace(
            Regex("\"([^\"]*)\""),
            "<span class=\"code-string\">\"$1\"</span>"
        )
        highlighted = highlighted.replace(
            Regex("'([^']*)'"),
            "<span class=\"code-string\">'$1'</span>"
        )

        return highlighted
    }

    private fun highlightYaml(code: String): String {
        var highlighted = code

        // Keys (before colon)
        highlighted = highlighted.replace(
            Regex("^(\\s*)([\\w-]+):", RegexOption.MULTILINE),
            "$1<span class=\"code-key\">$2</span>:"
        )

        // Values after colon
        highlighted = highlighted.replace(
            Regex(": (.+)$", RegexOption.MULTILINE),
            ": <span class=\"code-value\">$1</span>"
        )

        // Comments
        highlighted = highlighted.replace(
            Regex("#(.*)$", RegexOption.MULTILINE),
            "<span class=\"code-comment\">#$1</span>"
        )

        return highlighted
    }

    private fun highlightBash(code: String): String {
        var highlighted = code

        // Commands
        val commands = listOf(
            "git", "gradle", "gradlew", "export", "cd", "ls", "mkdir", "rm",
            "echo", "cat", "grep", "sed", "awk", "curl", "wget", "npm", "yarn"
        )
        commands.forEach { cmd ->
            highlighted = highlighted.replace(
                Regex("\\b$cmd\\b"),
                "<span class=\"code-command\">$cmd</span>"
            )
        }

        // Comments
        highlighted = highlighted.replace(
            Regex("#(.*)$", RegexOption.MULTILINE),
            "<span class=\"code-comment\">#$1</span>"
        )

        // Strings
        highlighted = highlighted.replace(
            Regex("\"([^\"]*)\""),
            "<span class=\"code-string\">\"$1\"</span>"
        )

        // Variables
        highlighted = highlighted.replace(
            Regex("\\$\\{?([A-Z_]+)\\}?"),
            "<span class=\"code-variable\">$&</span>"
        )

        return highlighted
    }

    private fun highlightJson(code: String): String {
        var highlighted = code

        // Keys
        highlighted = highlighted.replace(
            Regex("\"([^\"]+)\"\\s*:"),
            "<span class=\"code-key\">\"$1\"</span>:"
        )

        // String values
        highlighted = highlighted.replace(
            Regex(":\\s*\"([^\"]*)\""),
            ": <span class=\"code-string\">\"$1\"</span>"
        )

        // Numbers
        highlighted = highlighted.replace(
            Regex(":\\s*(\\d+)"),
            ": <span class=\"code-number\">$1</span>"
        )

        // Booleans and null
        highlighted = highlighted.replace(
            Regex(":\\s*(true|false|null)"),
            ": <span class=\"code-keyword\">$1</span>"
        )

        return highlighted
    }

    companion object {
        fun kotlin(code: String, fileName: String? = null) = CodeBlock(code, "kotlin", fileName)
        fun groovy(code: String, fileName: String? = null) = CodeBlock(code, "groovy", fileName)
        fun yaml(code: String, fileName: String? = null) = CodeBlock(code, "yaml", fileName)
        fun bash(code: String, fileName: String? = null) = CodeBlock(code, "bash", fileName)
        fun json(code: String, fileName: String? = null) = CodeBlock(code, "json", fileName)
    }
}