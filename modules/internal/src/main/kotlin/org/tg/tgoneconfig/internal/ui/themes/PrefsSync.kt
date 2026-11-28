package org.tg.tgoneconfig.internal.ui.themes

import org.tg.tgoneconfig.api.ui.v1.TGOneConfigPrefs
import org.tg.tgoneconfig.internal.ThemeConfig

object PrefsSync {
    @JvmStatic
    fun sync() = push()

    @JvmStatic
    fun animations(value: Boolean) = push(animations = value)

    @JvmStatic
    fun reducedMotion(value: Boolean) = push(reducedMotion = value)

    @JvmStatic
    fun backgroundBlur(value: Boolean) = push(backgroundBlur = value)

    @JvmStatic
    fun guiOpenAnimation(value: Boolean) = push(guiOpenAnimation = value)

    @JvmStatic
    fun uiScale(percent: Float) = push(uiScalePercent = percent)

    @JvmStatic
    fun reduceTransparency(value: Boolean) = push(reduceTransparency = value)

    private fun push(
        animations: Boolean = ThemeConfig.animations,
        reducedMotion: Boolean = ThemeConfig.reducedMotion,
        backgroundBlur: Boolean = ThemeConfig.enableBackgroundBlur,
        guiOpenAnimation: Boolean = ThemeConfig.guiOpenAnimation,
        uiScalePercent: Float = ThemeConfig.uiScale,
        reduceTransparency: Boolean = ThemeConfig.reduceTransparency,
    ) {
        TGOneConfigPrefs.update(animations, reducedMotion, backgroundBlur, guiOpenAnimation, uiScalePercent / 100f, reduceTransparency)
    }
}
