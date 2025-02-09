package com.oleksii.surovtsev.portfolio.components

import com.vaadin.flow.component.ClickNotifier
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasSize


//@Tag("dotlottie-wc")
//@NpmPackage(value = "@lottiefiles/dotlottie-wc", version = "0.2.21")
//@JsModule("@lottiefiles/dotlottie-wc/dist/index.js")
class LottieComponent(animationPath: String?, autoplay: Boolean, loop: Boolean, width: String?, height: String?) :
    Component(), ClickNotifier<LottieComponent?>, HasSize {
    // Constructor to initialize the animation
    init {
        getElement().setAttribute("src", animationPath)
        getElement().setAttribute("autoplay", autoplay)
        getElement().setAttribute("loop", loop)
        getElement().setAttribute("background", "transparent")
        if (width != null) {
            setWidth(width)
        }
        if (height != null) {
            setHeight(height)
        }
    }

    fun play() {
        getElement().executeJs("this.dotLottie.play()")
    }

    fun pause() {
        getElement().executeJs("this.dotLottie.pause()")
    }

    fun setMode(mode: Mode) {
        getElement().setAttribute("mode", mode.value)
    }

    fun setSpeed(speed: Double) {
        getElement().setAttribute("speed", speed.toString())
    }

    fun setLoop(loop: Boolean) {
        getElement().setAttribute("loop", loop.toString())
    }

    fun setAutoplay(autoplay: Boolean) {
        getElement().setAttribute("autoplay", autoplay.toString())
    }

    fun setSegments(start: Int, end: Int) {
        getElement().setAttribute("segments", "[" + start + "," + end + "]")
    }

    /** Make the animation full overlay but don't block the clicks.
     */
    fun makeFullOverlay() {
        getStyle().set("position", "fixed")
        getStyle().set("top", "0")
        getStyle().set("left", "0")
        getStyle().set("bottom", "0")
        getStyle().set("right", "0")
        getStyle().set("pointer-events", "none")
    }

    fun makeRelative(top: String?, left: String?) {
        getStyle().set("position", "relative")
        getStyle().set("top", top)
        getStyle().set("left", left)
    }


    /** Animation play modes.
     */
    enum class Mode(value: String) {
        FORWARD("forward"),
        REVERSE("reverse"),
        BOUNCE("bounce"),
        REVERSE_BOUNCE("reverse-bounce");

        val value: String?

        init {
            this.value = value
        }
    }
}