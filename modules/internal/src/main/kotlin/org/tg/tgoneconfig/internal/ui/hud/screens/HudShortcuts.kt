package org.tg.tgoneconfig.internal.ui.hud.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.tg.tgoneconfig.api.platform.v1.DesktopHelper
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.components.MouseGesture
import org.tg.tgoneconfig.api.ui.v1.components.Shortcut
import org.tg.tgoneconfig.api.ui.v1.components.ShortcutGroup
import org.tg.tgoneconfig.api.ui.v1.components.ShortcutHint
import org.tg.tgoneconfig.api.ui.v1.components.ShortcutInput
import org.tg.tgoneconfig.api.ui.v1.components.ShortcutSheetDialog
import org.tg.tgoneconfig.api.ui.v1.components.shortcutKeys
import org.tg.tgoneconfig.api.ui.v1.keybind.TGOneConfigKeybind
import org.tg.tgoneconfig.internal.TGOneConfigConfig
import org.tg.tgoneconfig.internal.ui.components.translated

internal val hudActionKeyName: String
    get() = if (DesktopHelper.isMac) "Cmd" else "Ctrl"

internal fun hudShortcutsKeyName(): String? =
    listOf(TGOneConfigConfig.hudShortcutsKeybind, TGOneConfigConfig.hudShortcutsAltKeybind)
        .firstOrNull { it != null && it.isBound }
        ?.displayName()

private fun shortcutText(key: String, fallback: String) = translated("tgoneconfig.hud.shortcuts.$key", fallback)

@OptIn(ExperimentalTGOneConfigUiApi::class)
private fun keys(vararg labels: String): List<ShortcutInput> = labels.map { ShortcutInput.Key(it) }

@OptIn(ExperimentalTGOneConfigUiApi::class)
private fun bound(keybind: TGOneConfigKeybind?, action: String): Shortcut? {
    val name = keybind?.takeIf { it.isBound }?.displayName() ?: return null
    return Shortcut(action, shortcutKeys(name))
}

@OptIn(ExperimentalTGOneConfigUiApi::class)
private fun hudShortcutGroups(): List<ShortcutGroup> {
    val action = hudActionKeyName
    val click = ShortcutInput.Mouse(MouseGesture.Click, shortcutText("gesture.click", "Click"))
    val drag = ShortcutInput.Mouse(MouseGesture.Drag, shortcutText("gesture.drag", "Drag"))
    val moving = ShortcutGroup(
        shortcutText("moving", "Moving"),
        listOf(
            Shortcut(shortcutText("nudge", "Nudge by 1 pixel"), listOf(ShortcutInput.Arrows)),
            Shortcut(shortcutText("nudge_far", "Nudge by 10 pixels"), keys("Shift") + ShortcutInput.Arrows),
            Shortcut(shortcutText("no_snap", "Move without snapping"), keys("Alt") + drag),
            Shortcut(shortcutText("detach", "Pull out of a merged group"), keys(action) + drag),
            Shortcut(
                shortcutText("free_resize", "Resize width and height freely"),
                keys("Shift") + ShortcutInput.Mouse(MouseGesture.Drag, shortcutText("gesture.drag_corner", "Drag corner")),
            ),
            Shortcut(shortcutText("grid", "Show or hide the grid"), keys("G")),
            Shortcut(shortcutText("vanilla", "Show or hide vanilla outlines"), keys("V")),
        ),
    )
    val selecting = ShortcutGroup(
        shortcutText("selecting", "Selecting"),
        listOfNotNull(
            Shortcut(
                shortcutText("marquee", "Select several HUDs"),
                listOf(ShortcutInput.Mouse(MouseGesture.Drag, shortcutText("gesture.drag_empty", "Drag empty space"))),
            ),
            Shortcut(shortcutText("add_select", "Add to the selection"), keys("Shift") + click),
            Shortcut(shortcutText("toggle_select", "Add or remove one HUD"), keys(action) + click),
            Shortcut(
                shortcutText("measure", "Measure the gap to a HUD"),
                keys("Alt") + ShortcutInput.Mouse(MouseGesture.Hover, shortcutText("gesture.hover", "Hover")),
            ),
            bound(TGOneConfigConfig.hudSelectAllKeybind, shortcutText("select_all", "Select all")),
            Shortcut(shortcutText("deselect", "Clear the selection"), keys("Esc")),
        ),
    )
    val editing = ShortcutGroup(
        shortcutText("editing", "Editing"),
        listOfNotNull(
            bound(TGOneConfigConfig.hudSettingsKeybind, shortcutText("settings", "Open settings")),
            bound(TGOneConfigConfig.hudVisibilityKeybind, shortcutText("toggle_hide", "Show or hide in game")),
            bound(TGOneConfigConfig.hudLockKeybind, shortcutText("toggle_lock", "Lock or unlock")),
            bound(TGOneConfigConfig.hudCopyKeybind, translated("tgoneconfig.menu.text.copy", "Copy")),
            bound(TGOneConfigConfig.hudCutKeybind, translated("tgoneconfig.menu.text.cut", "Cut")),
            bound(TGOneConfigConfig.hudPasteKeybind, translated("tgoneconfig.menu.text.paste", "Paste")),
            bound(TGOneConfigConfig.hudDuplicateKeybind, translated("tgoneconfig.hud.menu.duplicate", "Duplicate")),
            bound(TGOneConfigConfig.hudResetKeybind, translated("tgoneconfig.hud.menu.reset_position", "Reset position")),
            bound(TGOneConfigConfig.hudDeleteKeybind, translated("tgoneconfig.hud.menu.delete", "Delete HUD")),
            bound(TGOneConfigConfig.hudUndoKeybind, translated("tgoneconfig.hud.menu.undo", "Undo")),
            bound(TGOneConfigConfig.hudRedoKeybind, translated("tgoneconfig.hud.menu.redo", "Redo")),
            bound(TGOneConfigConfig.hudShortcutsKeybind, shortcutText("open_sheet", "Show these shortcuts")),
        ),
    )
    return listOf(moving, selecting, editing)
}

@OptIn(ExperimentalTGOneConfigUiApi::class)
@Composable
internal fun HudShortcutsPill(keyName: String?) {
    ShortcutHint(shortcutText("pill", "Keyboard shortcuts"), keyName?.let { shortcutKeys(it) }.orEmpty())
}

@OptIn(ExperimentalTGOneConfigUiApi::class)
@Composable
internal fun HudShortcutsDialog(onDismiss: () -> Unit) {
    val groups = remember { hudShortcutGroups() }
    ShortcutSheetDialog(groups, onDismiss)
}
