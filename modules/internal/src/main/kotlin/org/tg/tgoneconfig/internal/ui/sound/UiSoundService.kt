package org.tg.tgoneconfig.internal.ui.sound

interface UiSoundService {
    fun play(event: UiSoundEvent, theme: UiSoundTheme, volume: Float)

    fun startAmbience(theme: UiSoundTheme, volume: Float)

    fun stopAmbience(setDucking: Boolean)
}
