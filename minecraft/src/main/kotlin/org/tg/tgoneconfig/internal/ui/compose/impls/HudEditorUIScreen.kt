package org.tg.tgoneconfig.internal.ui.compose.impls

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.mojang.blaze3d.platform.InputConstants
import org.apache.logging.log4j.LogManager
import org.tg.tgoneconfig.api.hud.v1.HudManager
import org.tg.tgoneconfig.api.platform.v1.Platform
import org.tg.tgoneconfig.api.ui.v1.keybind.KeybindManager
import org.tg.tgoneconfig.api.ui.v1.keybind.TGOneConfigKeybind
import org.tg.tgoneconfig.api.ui.v1.platform.TGOneConfigComposeOptions
import org.tg.tgoneconfig.internal.TGOneConfigConfig
import org.tg.tgoneconfig.internal.ThemeConfig
import org.tg.tgoneconfig.internal.ui.components.BackdropUsers
import org.tg.tgoneconfig.internal.ui.components.RetainedVisibility
import org.polyfrost.compose.minecraft.mc.McItemIcons
import org.polyfrost.compose.minecraft.screen.ComposeScreen
import org.tg.tgoneconfig.internal.ui.compose.TGOneConfigComposeHooks
//? if >= 1.21.11
import org.polyfrost.compose.minecraft.render.blaze.BlazeHud
//? if < 1.21.11
//import org.polyfrost.compose.render.RenderContext
import org.tg.tgoneconfig.internal.ui.guiCloseAnimationMillis
import org.tg.tgoneconfig.internal.ui.hud.screens.HudDesignSession
import org.tg.tgoneconfig.internal.ui.hud.screens.HudDesignStudio
import org.tg.tgoneconfig.internal.ui.hud.screens.HudEditorViewport
import org.tg.tgoneconfig.internal.ui.keybind.KeybindRecordingBus
import org.tg.tgoneconfig.internal.ui.shell.HudEditorRoute
import org.tg.tgoneconfig.internal.ui.shell.LocalShell
import org.tg.tgoneconfig.internal.ui.shell.ShellController
import org.tg.tgoneconfig.internal.ui.sound.UiSoundEvent
import org.tg.tgoneconfig.internal.ui.sound.UiSounds
import org.tg.tgoneconfig.internal.ui.themes.Theme
import org.tg.tgoneconfig.internal.ui.themes.updateAccent

//? if > 1.8.9 {
import net.minecraft.client.gui.GuiGraphicsExtractor
//?}

//? if < 1.21.11 && > 1.8.9 {
/*import org.lwjgl.glfw.GLFW
*///?}

private val LOGGER = LogManager.getLogger("TG OneConfig/HudEditor")

class HudEditorUIScreen private constructor() : ComposeScreen(options = TGOneConfigComposeOptions) {
    companion object {
        private var instance: HudEditorUIScreen? = null

        @JvmStatic
        fun open(fromTGOneConfig: Boolean): HudEditorUIScreen {
            val screen = instance ?: HudEditorUIScreen().also { instance = it }
            screen.enteredFromTGOneConfig = fromTGOneConfig
            return screen
        }
    }

    @Volatile private var everOpened = false

    override val retainsScene: Boolean get() = true

    override fun wantsBackdrop(): Boolean =
        ThemeConfig.enableBackgroundBlur && !ThemeConfig.reduceTransparency && BackdropUsers.count > 0

    /** Prepares this frame's HUDs when there's no world, whose HUD pass would otherwise do it */
    private fun prepareHudsOffWorld(): Boolean {
        val sw = Platform.screen().guiWidth().toFloat()
        val sh = Platform.screen().guiHeight().toFloat()
        HudManager.inWorld = false
        HudManager.guiScreenWidth = sw
        HudManager.guiScreenHeight = sh
        return HudManager.beginFrame(sw, sh, McItemIcons::renderPending, true, true)
    }

    // in a world the game's HUD pass draws the HUDs before the blur, so off it they're drawn the same way here
    //? if >= 1.21.11 {
    override fun extractBeneathBlur(ctx: GuiGraphicsExtractor): Boolean {
        if (client.level != null) return false
        if (prepareHudsOffWorld()) BlazeHud.markDirty()
        BlazeHud.render(ctx, TGOneConfigComposeHooks.drawHuds)
        return true
    }
    //?} else {
    /*private val drawHuds: (RenderContext) -> Unit = { ctx ->
        HudManager.render(ctx, HudManager.guiScreenWidth, HudManager.guiScreenHeight)
    }

    override val beneathBlur: ((RenderContext) -> Unit)?
        get() = if (client.level != null) null else drawHuds
    *///?}

    @Volatile private var closeRequested = false
    @Volatile private var closeRequestedAt = 0L
    @Volatile private var closeAnimationMs = 0L

    private fun beginClose() {
        if (closeRequested) return
        closeRequested = true
        closeRequestedAt = System.currentTimeMillis()
        closeAnimationMs = guiCloseAnimationMillis()
        UiSounds.play(UiSoundEvent.CLOSE)
        UiSounds.releaseAmbience()
    }

    private fun cancelClose(): Boolean {
        if (!closeRequested) return false
        closeRequested = false
        requestOpenCallback?.invoke()
        UiSounds.play(UiSoundEvent.OPEN)
        UiSounds.acquireAmbience()
        return true
    }

    @Volatile private var returningToTGOneConfig = false

    private var enteredFromTGOneConfig = false

    private fun returnToTGOneConfig() {
        returningToTGOneConfig = true
        Platform.screen().display(if (enteredFromTGOneConfig) TGOneConfigUIScreen.resume() else TGOneConfigUIScreen.open())
    }

    private var requestCloseCallback: (() -> Unit)? = null
    private var requestOpenCallback: (() -> Unit)? = null

    override fun init() {
        everOpened = true
        updateAccent()
        HudManager.onEditorScreenAdded()
        HudManager.editorOpenRevision.intValue++
        closeRequested = false
        closeRequestedAt = 0L
        closeAnimationMs = 0L
        returningToTGOneConfig = false
        UiSounds.acquireAmbience()
        ShellController.shared.session.resume()
        super.init()
        requestOpenCallback?.invoke()
    }

    override fun removed() {
        HudDesignSession.launcherPending = false
        if (!returningToTGOneConfig) {
            // off by default so closing the editor leaves the last config page as the route to come back to
            if (TGOneConfigConfig.restoreHudEditor) ShellController.shared.navigation.recordLastRoute(HudEditorRoute)
            ShellController.shared.session.markClosed(System.currentTimeMillis())
        }
        UiSounds.releaseAmbience()
        ShellController.shared.session.stop()
        HudManager.onEditorScreenRemoved()
        super.removed()
    }

    override fun isPauseScreen(): Boolean = TGOneConfigConfig.pauseGame

    private fun handleTGOneConfigKeybind(onRelease: Boolean = false): Boolean {
        if (closeRequested) return cancelClose()
        if (TGOneConfigConfig.keybindClosesGui) {
            if (!onRelease) TGOneConfigConfig.notifyKeybindClosedGui()
            beginClose()
            requestCloseCallback?.invoke()
        } else {
            returnToTGOneConfig()
        }
        return true
    }

    override fun handleKeyPressed(key: Int, modifiers: Int): Boolean {
        if (key == InputConstants.KEY_ESCAPE) {
            if (!closeRequested) {
                beginClose()
                requestCloseCallback?.invoke()
            }
            return true
        }
        val toggleKey = TGOneConfigConfig.oneConfigKeybind.keyCodes?.firstOrNull()
        if (toggleKey != null && key == toggleKey && !KeybindRecordingBus.isRecording && !isModifierTapBind) {
            return handleTGOneConfigKeybind()
        }
        return false
    }

    override val modifierTapKeys: Set<Int>? get() = TGOneConfigComposeHooks.modifierTapKeys

    override fun handleModifierTapped() {
        handleTGOneConfigKeybind(onRelease = true)
    }

    override fun handleMouseClicked(button: Int): Boolean {
        if (KeybindRecordingBus.isRecording) return false
        // Only side and extra mouse buttons can trigger the TGOneConfig keybind
        //~ if < 1.21.11 && > 1.8.9 'InputConstants.MOUSE_BUTTON_4' -> 'GLFW.GLFW_MOUSE_BUTTON_4'
        if (button >= InputConstants.MOUSE_BUTTON_4 &&
            KeybindManager.isTriggeredByMouse(TGOneConfigConfig.oneConfigKeybind, button)
        ) {
            return handleTGOneConfigKeybind()
        }
        return false
    }

    //? if > 1.8.9 {
    //~ if >= 26.1 'render' -> 'extractRenderState'
    override fun extractRenderState(ctx: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, tickDelta: Float) {
    //?} else
    //override fun render(mouseX: Int, mouseY: Int, tickDelta: Float) {
        // a screen drawn over this one may render it as its parent so skip that pass or it closes the screen above us
        if (Platform.screen().current<Any?>() !== this) return
        if (closeRequested && System.currentTimeMillis() - closeRequestedAt >= closeAnimationMs) {
            //? if < 1.21.8 && > 1.8.9
            //renderBackground(ctx, mouseX, mouseY, tickDelta)
            Platform.screen().close()
            return
        }
        // newer versions prepare them in extractBeneathBlur, as the background is drawn first
        //? if < 1.21.11
        //if (client.level == null) prepareHudsOffWorld()
        HudEditorViewport.update(Platform.screen().windowWidth(), Platform.screen().windowHeight())
        //? if > 1.8.9 {
        //~ if >= 26.1 'render' -> 'extractRenderState'
        super.extractRenderState(ctx, mouseX, mouseY, tickDelta)
        //?} else
        //super.render(mouseX, mouseY, tickDelta)
    }

    @Composable
    override fun compose() {
        DisposableEffect(Unit) {
            // a failed scene is disposed and rebuilt while the screen stays open, which is not a close
            onDispose {
                if (Platform.screen().current<Any?>() !== this@HudEditorUIScreen) {
                    HudManager.onEditorScreenRemoved()
                }
            }
        }

        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) { visible = true }

        val requestClose: () -> Unit = { visible = false }
        val requestOpen: () -> Unit = { visible = true }
        SideEffect {
            requestCloseCallback = requestClose
            requestOpenCallback = requestOpen
        }

        val exitMs = guiCloseAnimationMillis().toInt().coerceAtLeast(1)
        RetainedVisibility(
            visible = visible,
            enter = tween(200, easing = EaseOutCubic),
            exit = tween(exitMs, easing = EaseIn),
            modifier = Modifier.fillMaxSize(),
            hiddenScale = 0.92f,
            openKey = HudManager.editorOpenRevision.intValue,
        ) { _ ->
            CompositionLocalProvider(
                LocalLifecycleOwner provides ShellController.shared.session,
                LocalViewModelStoreOwner provides ShellController.shared.session,
                LocalShell provides ShellController.shared,
            ) {
                Theme(pixelGrid = true) {
                    HudDesignStudio(visible)
                }
            }
        }
    }
}
