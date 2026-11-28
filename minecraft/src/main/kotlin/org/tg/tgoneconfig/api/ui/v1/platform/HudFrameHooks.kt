@file:OptIn(ExperimentalTGOneConfigUiApi::class)

package org.tg.tgoneconfig.api.ui.v1.platform

import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.Minecraft
import org.apache.logging.log4j.LogManager
import org.tg.compose.minecraft.hud.HudInput
import org.tg.tgoneconfig.api.hud.v1.HudClickInput
import org.tg.tgoneconfig.api.hud.v1.HudManager
import org.tg.tgoneconfig.api.hud.v1.HudMouseButton
import org.tg.tgoneconfig.api.platform.v1.Platform
import org.tg.tgoneconfig.api.platform.v1.ScreenPlatform
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi

internal object HudFrameHooks {
    private val LOGGER = LogManager.getLogger("TG OneConfig/HudFrame")
    private var pointerFailed = false

    @JvmStatic
    fun beginFrame(partialTick: Float) {
        HudManager.partialTick = partialTick
        try {
            feedPointer()
        } catch (e: Throwable) {
            if (e is VirtualMachineError) throw e
            if (!pointerFailed) {
                pointerFailed = true
                LOGGER.error("Failed to read the pointer for HUD click regions", e)
            }
        }
    }

    @JvmStatic
    fun consumeButton(button: Int, pressed: Boolean): Boolean =
        HudClickInput.consumeButton(
            when (button) {
                InputConstants.MOUSE_BUTTON_LEFT -> HudMouseButton.Left.ordinal
                InputConstants.MOUSE_BUTTON_RIGHT -> HudMouseButton.Right.ordinal
                InputConstants.MOUSE_BUTTON_MIDDLE -> HudMouseButton.Middle.ordinal
                else -> -1
            },
            pressed,
        )

    private fun feedPointer() {
        val anyScreen = Minecraft.getInstance().isWindowActive && currentScreen() != null
        HudClickInput.updatePointer(HudInput.pointerX, HudInput.pointerY, HudInput.pointerAllowed, anyScreen)
    }

    private fun currentScreen(): Any? =
        try {
            Platform.screen().current<Any?>()
        } catch (_: Throwable) {
            null
        }
}
