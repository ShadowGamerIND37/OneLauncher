package org.tg.tgoneconfig.internal.ui.shell

import org.tg.tgoneconfig.api.hud.v1.HudManager
import org.tg.tgoneconfig.internal.ui.hud.screens.HudDesignSession
import org.tg.tgoneconfig.internal.ui.keybind.KeybindRecordingBus

object HudEditorRoute

enum class SearchFocus {
    SelectAll,

    /** Focuses the search field and leaves its text and cursor alone */
    Focus,
    Unfocus,
}

object ShellState {
    var searchQuery: String
        get() = ShellController.shared.search.query
        set(value) {
            ShellController.shared.search.update(value)
        }

    @JvmStatic
    fun handleSearchKeybind(pressed: Boolean): Boolean {
        if (KeybindRecordingBus.isRecording) return false
        if (HudDesignSession.handleSearch(pressed)) return true
        if (!pressed || !HudManager.isConfigUiOpen) return false
        ShellController.shared.search.requestFocus(SearchFocus.SelectAll)
        return true
    }

    @JvmStatic
    fun handlePaletteKeybind(pressed: Boolean): Boolean {
        if (KeybindRecordingBus.isRecording) return false
        if (!pressed || !HudManager.isConfigUiOpen || HudManager.isEditorOpen) return false
        val shell = ShellController.shared
        if (shell.layout.minimal) return false
        shell.palette.toggle()
        return true
    }
}
