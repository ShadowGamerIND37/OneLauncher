package org.tg.tgoneconfig.internal.ui.keybind

import org.tg.tgoneconfig.internal.ui.api.CollapseStore

object KeybindGroupCollapseStore : CollapseStore("keybind-collapsed", "keybind groups") {
    override fun defaultCollapsed(id: String) = id.startsWith("minecraft-keybinds.")
}
