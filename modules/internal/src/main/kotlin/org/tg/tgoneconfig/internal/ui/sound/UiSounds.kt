package org.tg.tgoneconfig.internal.ui.sound

import org.tg.tgoneconfig.internal.TGOneConfigConfig
import java.util.ServiceLoader

object UiSounds {
    private val service: UiSoundService? by lazy { loadService() }

    private var ambienceActive = false

    private var ambienceTheme: UiSoundTheme? = null

    private fun loadService(): UiSoundService? = try {
        val it = ServiceLoader.load(UiSoundService::class.java, UiSoundService::class.java.classLoader).iterator()
        if (it.hasNext()) it.next() else null
    } catch (_: Throwable) {
        null
    }

    private fun soundVolume(): Float = (TGOneConfigConfig.uiSoundVolume / 100f).coerceIn(0f, 1f)

    private fun ambienceVolume(): Float = (TGOneConfigConfig.uiAmbienceVolume / 100f).coerceIn(0f, 1f)

    fun play(event: UiSoundEvent) {
        if (!TGOneConfigConfig.enableUISounds) return
        if (!isEventEnabled(event)) return
        val v = soundVolume()
        if (v <= 0f) return
        service?.play(event, UiSoundTheme.current(), v)
    }

    private fun isEventEnabled(event: UiSoundEvent): Boolean = when (event) {
        UiSoundEvent.OPEN,
        UiSoundEvent.CLOSE -> TGOneConfigConfig.enableUIMenuSounds
        UiSoundEvent.CLICK -> TGOneConfigConfig.enableUIClickSounds
        UiSoundEvent.SLIDER_TICK -> TGOneConfigConfig.enableUISliderSounds
        UiSoundEvent.HUD_SELECT,
        UiSoundEvent.HUD_DRAG_START,
        UiSoundEvent.HUD_DRAG_END,
        UiSoundEvent.HUD_RESIZE_START,
        UiSoundEvent.HUD_RESIZE_END -> TGOneConfigConfig.enableHudEditorSounds
    }

    @JvmStatic
    @Synchronized
    fun acquireAmbience() {
        if (ambienceActive) return
        ambienceActive = true
        startAmbienceIfEnabled()
    }

    @JvmStatic
    @Synchronized
    fun releaseAmbience() {
        if (!ambienceActive) return
        ambienceActive = false
        service?.stopAmbience(false)
    }

    @JvmStatic
    @Synchronized
    fun refreshAmbience() {
        if (!ambienceActive) return
        if (TGOneConfigConfig.enableUIAmbience) startAmbienceIfEnabled() else service?.stopAmbience(false)
    }

    @JvmStatic
    @JvmOverloads
    @Synchronized
    fun onSoundThemeChanged(preview: UiSoundEvent = UiSoundEvent.OPEN) {
        play(preview)
        if (ambienceActive && TGOneConfigConfig.enableUIAmbience) {
            service?.stopAmbience(true)
            startAmbienceIfEnabled()
        }
    }

    @JvmStatic
    @Synchronized
    fun syncAmbienceTheme() {
        if (!ambienceActive || !TGOneConfigConfig.enableUIAmbience) return
        if (ambienceTheme == UiSoundTheme.current()) return
        service?.stopAmbience(true)
        startAmbienceIfEnabled()
    }

    private fun startAmbienceIfEnabled() {
        if (!TGOneConfigConfig.enableUIAmbience) return
        val theme = UiSoundTheme.current()
        ambienceTheme = theme
        service?.startAmbience(theme, ambienceVolume())
    }
}
