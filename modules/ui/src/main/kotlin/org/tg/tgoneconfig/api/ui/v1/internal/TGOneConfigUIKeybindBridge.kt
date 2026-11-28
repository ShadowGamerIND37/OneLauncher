package org.tg.tgoneconfig.api.ui.v1.internal

import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.InternalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.keybind.ConfigKeybind
import org.tg.tgoneconfig.api.ui.v1.keybind.TGOneConfigKeybind

@ExperimentalTGOneConfigUiApi
@InternalTGOneConfigUiApi
public interface TGOneConfigUIKeybindBridge {
    public fun configKeybinds(): List<ConfigKeybind>
}

@ExperimentalTGOneConfigUiApi
@InternalTGOneConfigUiApi
public object KeybindInterop {
    @JvmStatic
    public fun configKeybind(keybind: TGOneConfigKeybind, title: String, path: String, modId: String, modTitle: String): ConfigKeybind =
        ConfigKeybind(keybind, title, path, modId, modTitle)
}

@ExperimentalTGOneConfigUiApi
private val loadedKeybindBridge: TGOneConfigUIKeybindBridge? by lazy {
    loadBridge(TGOneConfigUIKeybindBridge::class.java, "config keybinds cannot be listed")
}

@ExperimentalTGOneConfigUiApi
internal fun keybindBridge(): TGOneConfigUIKeybindBridge? = loadedKeybindBridge
