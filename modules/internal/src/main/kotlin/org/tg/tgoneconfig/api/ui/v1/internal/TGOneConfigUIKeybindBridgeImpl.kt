package org.tg.tgoneconfig.api.ui.v1.internal

import org.tg.tgoneconfig.api.ui.v1.keybind.ConfigKeybind
import org.tg.tgoneconfig.api.ui.v1.keybind.TGOneConfigKeybind
import org.tg.tgoneconfig.internal.ui.components.asRenderText
import org.tg.tgoneconfig.internal.ui.components.localizedTitle
import org.tg.tgoneconfig.internal.ui.keybind.collectAllKeybindGroups

class TGOneConfigUIKeybindBridgeImpl : TGOneConfigUIKeybindBridge {
    override fun configKeybinds(): List<ConfigKeybind> = collectAllKeybindGroups().flatMap { group ->
        val modTitle = group.modTitle.asRenderText()
        group.entries.mapNotNull { entry ->
            val keybind = entry.prop.get() as? TGOneConfigKeybind ?: return@mapNotNull null
            val title = entry.prop.localizedTitle().asRenderText().ifBlank { entry.path }
            KeybindInterop.configKeybind(keybind, title, entry.path, group.modId, modTitle)
        }
    }
}
