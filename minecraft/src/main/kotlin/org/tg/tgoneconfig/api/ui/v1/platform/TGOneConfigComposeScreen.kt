@file:OptIn(ExperimentalTGOneConfigUiApi::class)

package org.tg.tgoneconfig.api.ui.v1.platform

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.TGOneConfigPrefs
import org.tg.tgoneconfig.api.ui.v1.TGOneConfigUI
import org.tg.tgoneconfig.api.ui.v1.components.handleEscapeStack
import org.tg.tgoneconfig.api.ui.v1.theme.TGOneConfigTheme
import org.tg.tgoneconfig.api.ui.v1.theme.ProvideTGOneConfigTheme
import org.tg.tgoneconfig.internal.ThemeConfig
import org.polyfrost.compose.minecraft.screen.ComposeScreen

//? if = 1.8.9 {
/*import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.TitleScreen
import org.lwjgl.input.Keyboard
*///?}

@ExperimentalTGOneConfigUiApi
abstract class TGOneConfigComposeScreen(
    title: Component = Component.empty(),
    val previous: Screen? = TGOneConfigScreens.current(),
    renderMode: RenderMode = RenderMode.OnDemand,
) : ComposeScreen(renderMode.internal(), TGOneConfigComposeOptions) {
    @ExperimentalTGOneConfigUiApi
    enum class RenderMode {
        OnDemand,

        Continuous,
    }

    private val screenTitle: Component = title

    private var openedAt = 0L

    init {
        TGOneConfigUI.ensureInitialized()
    }

    open val blurBackground: Boolean get() = TGOneConfigPrefs.backgroundBlur

    open val closeOnEscape: Boolean get() = true

    open val panoramaBackground: Boolean get() = true

    open val dimBackground: Boolean get() = false

    open val pausesGame: Boolean get() = false

    open val uiScale: Float get() = 1f

    @Composable
    abstract fun Content()

    open fun onClosed() {}

    fun dismiss() {
        if (TGOneConfigScreens.current() !== this) return
        TGOneConfigScreens.display(previous)
    }

    @Composable
    final override fun compose() {
        ProvideTGOneConfigTheme {
            val scrim = TGOneConfigTheme.current.scrimColor
            val dim = needsScrim()
            val fade = remember { Animatable(if (TGOneConfigPrefs.animationsEnabled) 0f else 1f) }
            LaunchedEffect(dim) { if (dim) fade.animateTo(1f, tween(BLUR_FADE_MS.toInt())) }
            Box(
                Modifier.fillMaxSize().drawBehind { if (dim) drawRect(scrim, alpha = fade.value) }.handleEscapeStack(),
                propagateMinConstraints = true,
            ) { Content() }
        }
    }

    private fun needsScrim(): Boolean =
        dimBackground && client.level != null && (!blurBackground || ThemeConfig.reduceTransparency)

    override fun isPauseScreen(): Boolean = pausesGame

    override fun densityScale(): Float = uiScale

    override fun init() {
        if (openedAt == 0L) openedAt = System.currentTimeMillis()
        super.init()
    }

    override fun blurAlpha(): Float {
        if (!blurBackground || client.level == null || ThemeConfig.reduceTransparency) return 0f
        if (!TGOneConfigPrefs.animationsEnabled) return 1f
        val start = openedAt.takeIf { it > 0L } ?: return 0f
        return ((System.currentTimeMillis() - start).toFloat() / BLUR_FADE_MS).coerceIn(0f, 1f)
    }

    override fun drawsPanorama(): Boolean = panoramaBackground

    override fun handleKeyPressed(key: Int, modifiers: Int): Boolean {
        if (key != InputConstants.KEY_ESCAPE) return false
        if (closeOnEscape) dismiss()
        return true
    }

    //? if > 1.8.9 {
    override fun getTitle(): Component = screenTitle

    override fun shouldCloseOnEsc(): Boolean = closeOnEscape

    override fun onClose() {
        dismiss()
        if (TGOneConfigScreens.current() !== this) super.onClose()
    }
    //?} else {
    /*private var escaping = false

    override fun handleKeyboard() {
        escaping = Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_ESCAPE
        try {
            super.handleKeyboard()
        } finally {
            escaping = false
        }
    }

    private fun restoreAfterEscape() {
        val previous = previous ?: return
        val current = TGOneConfigScreens.current()
        if (current !== previous && (current == null || current is TitleScreen)) TGOneConfigScreens.display(previous)
    }
    *///?}

    override fun removed() {
        super.removed()
        //? if = 1.8.9
        //if (escaping) Minecraft.getInstance().schedule(Runnable { restoreAfterEscape() })
        onClosed()
    }

    private companion object {
        const val BLUR_FADE_MS = 250L

        fun RenderMode.internal(): ComposeScreen.RenderMode = when (this) {
            RenderMode.OnDemand -> ComposeScreen.RenderMode.ON_DEMAND
            RenderMode.Continuous -> ComposeScreen.RenderMode.CONTINUOUS
        }
    }
}

@ExperimentalTGOneConfigUiApi
fun isTGOneConfigComposeScreen(screen: Any?): Boolean = screen is ComposeScreen
