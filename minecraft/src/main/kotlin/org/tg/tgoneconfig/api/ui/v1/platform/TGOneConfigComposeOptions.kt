@file:OptIn(ExperimentalTGOneConfigUiApi::class)

package org.tg.tgoneconfig.api.ui.v1.platform

import androidx.compose.runtime.Composable
import org.tg.compose.host.ComposeOptions
import org.tg.compose.host.SceneKind
import org.tg.compose.host.WheelHandler
import org.tg.compose.minecraft.hud.HudLayer
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.a11y.A11y
import org.tg.tgoneconfig.api.ui.v1.a11y.Motion
import org.tg.tgoneconfig.api.ui.v1.components.ContextMenuRoot
import org.tg.tgoneconfig.api.ui.v1.components.FeedbackHosts
import org.tg.tgoneconfig.api.ui.v1.components.ProvideTGOneConfigTextContextMenu
import org.tg.tgoneconfig.api.ui.v1.components.WithoutFeedbackHosts
import org.tg.tgoneconfig.api.ui.v1.components.releaseFeedback
import org.tg.tgoneconfig.api.ui.v1.theme.ProvideTGOneConfigTheme
import org.tg.tgoneconfig.internal.ui.components.AnimationsMotionScale
import org.tg.tgoneconfig.internal.ui.components.WheelGlideRouter
import org.tg.tgoneconfig.internal.ui.compose.impls.TGOneConfigUIScreen
import org.tg.tgoneconfig.internal.ui.keybind.KeybindRecordingBus

object TGOneConfigComposeOptions : ComposeOptions() {
    override val animationScale: Float get() = AnimationsMotionScale.scaleFactor

    override val reducedMotion: Boolean get() = Motion.reduced

    @Composable
    override fun Decorate(owner: Any, content: @Composable () -> Unit) {
        if (owner is HudLayer) {
            WithoutFeedbackHosts { ProvideTGOneConfigTheme { content() } }
        } else {
            ProvideTGOneConfigTextContextMenu { ContextMenuRoot { FeedbackHosts(owner) { content() } } }
        }
    }

    override fun onSceneCreated(owner: Any, kind: SceneKind) {
        if (kind == SceneKind.HudLayer) TGOneConfigUIScreen.stopWarmUp()
    }

    override fun onInputKind(owner: Any, keyboard: Boolean) = A11y.reportInput(keyboard)

    override fun onRemoved(owner: Any) = releaseFeedback(owner)

    override val isCapturingInput: Boolean get() = KeybindRecordingBus.isRecording

    override fun consumeEscape(owner: Any): Boolean = KeybindRecordingBus.consumeEscape()

    override fun consumeMouse(owner: Any, button: Int, pressed: Boolean): Boolean = KeybindRecordingBus.consumeMouse(button, pressed)

    override fun createWheelHandler(kind: SceneKind): WheelHandler = WheelGlideRouter()
}
