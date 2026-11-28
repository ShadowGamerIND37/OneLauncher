package org.tg.tgoneconfig.internal.ui.compose

import org.tg.compose.host.ComposeHost
import org.tg.compose.host.GameInputLayer
import org.tg.compose.host.OverlayRenderer
import org.tg.compose.render.RenderContext
import org.tg.tgoneconfig.api.hud.v1.HudManager
import org.tg.tgoneconfig.api.notifications.v1.NotificationsManager
import org.tg.tgoneconfig.api.notifications.v1.NotificationsRenderer
import org.tg.tgoneconfig.api.ui.v1.keybind.KeyModifiers
import org.tg.tgoneconfig.api.ui.v1.platform.HudFrameHooks
import org.tg.tgoneconfig.internal.TGOneConfigConfig

//? if = 1.8.9 {
/*import org.tg.compose.host.LegacyInputObserver
import org.tg.tgoneconfig.internal.legacy.LegacyInputEvents
*///?}

object TGOneConfigComposeHooks {
    fun install() {
        ComposeHost.addGameInput(ToastInput)
        ComposeHost.addGameInput(HudClickInput)
        ComposeHost.addOverlay(Toasts)
        ComposeHost.onFontsChanged { HudManager.invalidate() }
        //? if = 1.8.9
        //ComposeHost.addLegacyInput(LegacyEvents)
    }

    val modifierTapKeys: Set<Int>?
        get() {
            val bind = TGOneConfigConfig.oneConfigKeybind
            if (bind.mouseBtns?.isNotEmpty() == true || bind.mods != KeyModifiers.NONE) return null
            return bind.keyCodes?.toSet()
        }

    @JvmField
    val drawHuds: (RenderContext, RenderContext) -> Unit = { stable, volatile ->
        HudManager.render(stable, HudManager.guiScreenWidth, HudManager.guiScreenHeight, volatile)
    }

    private object ToastInput : GameInputLayer {
        override val order: Int get() = GameInputLayer.ABOVE_HUD_LAYERS
        override fun consumeButton(button: Int, pressed: Boolean): Boolean = NotificationsRenderer.consumeMouseButton(button, pressed)
        override fun consumeScroll(x: Double, y: Double): Boolean = NotificationsRenderer.isPointerOverToast()
    }

    private object HudClickInput : GameInputLayer {
        override val order: Int get() = GameInputLayer.BELOW_HUD_LAYERS
        override fun consumeButton(button: Int, pressed: Boolean): Boolean = HudFrameHooks.consumeButton(button, pressed)
    }

    private object Toasts : OverlayRenderer {
        override val visible: Boolean get() = NotificationsManager.count > 0
        override fun prepare(guiWidth: Float, guiHeight: Float): Boolean = NotificationsRenderer.prepare(guiWidth, guiHeight)
        override fun draw(ctx: RenderContext) = NotificationsRenderer.draw(ctx)
    }

    //? if = 1.8.9 {
    /*private object LegacyEvents : LegacyInputObserver {
        override fun onDrainedMouse(): Boolean = LegacyInputEvents.postMouse()
        override fun onDrainedKeyboard() = LegacyInputEvents.postKeyboard()
    }
    *///?}
}
