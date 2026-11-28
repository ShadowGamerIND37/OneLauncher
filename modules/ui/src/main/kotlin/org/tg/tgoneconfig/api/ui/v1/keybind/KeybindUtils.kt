package org.tg.tgoneconfig.api.ui.v1.keybind

import androidx.compose.ui.input.pointer.PointerKeyboardModifiers
import androidx.compose.ui.input.pointer.isCtrlPressed
import androidx.compose.ui.input.pointer.isMetaPressed
import org.tg.tgoneconfig.api.platform.v1.DesktopHelper

public object KeybindUtils {
    @JvmStatic
    public val actionModifier: Byte
        get() = if (DesktopHelper.isMac) KeyModifiers.META else KeyModifiers.CTRL

    /** Whether the platform action modifier is currently pressed */
    public fun isActionModifierPressed(modifiers: PointerKeyboardModifiers): Boolean =
        if (DesktopHelper.isMac) modifiers.isMetaPressed else modifiers.isCtrlPressed
}
