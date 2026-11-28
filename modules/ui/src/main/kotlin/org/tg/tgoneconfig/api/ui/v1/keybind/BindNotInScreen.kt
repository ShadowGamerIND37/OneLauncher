package org.tg.tgoneconfig.api.ui.v1.keybind

import org.tg.tgoneconfig.api.platform.v1.Platform
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi

public class BindNotInScreen(
    keyCodes: IntArray?,
    mouseBtns: IntArray?,
    mods: Byte,
    durationNanos: Long,
    action: (Boolean) -> Boolean,
) : TGOneConfigKeybind(keyCodes, mouseBtns, mods, durationNanos, action) {
    override fun test(downKeys: Set<Int>, downMouse: Set<Int>, currentMods: Byte): Boolean {
        return super.test(downKeys, downMouse, currentMods) && Platform.screen().current<Any?>() == null
    }

    @OptIn(ExperimentalTGOneConfigUiApi::class)
    override fun copyWith(keyCodes: IntArray?, mouseBtns: IntArray?, mods: Byte): TGOneConfigKeybind =
        BindNotInScreen(keyCodes, mouseBtns, mods, durationNanos, action).also {
            it.name = name
            it.category = category
            it.defaultKeybind = defaultKeybind
            it.firesWhileTyping = firesWhileTyping
            it.mirrorToVanilla = mirrorToVanilla
        }
}
