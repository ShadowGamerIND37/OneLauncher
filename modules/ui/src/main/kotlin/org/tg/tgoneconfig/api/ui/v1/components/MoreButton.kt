@file:OptIn(ExperimentalTGOneConfigUiApi::class)

package org.tg.tgoneconfig.api.ui.v1.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi

@ExperimentalTGOneConfigUiApi
public object MoreButtonDefaults {
    public val contentDescription: String
        @Composable get() = kitString("tgoneconfig.ui.more_actions", "More actions")
}

@ExperimentalTGOneConfigUiApi
@Composable
public fun MoreButton(
    entries: List<MenuEntry>,
    modifier: Modifier = Modifier,
    contentDescription: String = MoreButtonDefaults.contentDescription,
    style: IconButtonStyle = IconButtonStyle.Ghost,
    size: ControlSize = ControlSize.Compact,
    selectedWhenOpen: Boolean = false,
    sound: UiSound? = UiSound.Click,
    popupSounds: Boolean = LocalPopupSounds.current,
    tooltip: String? = contentDescription,
    menuSound: UiSound? = UiSound.Click,
) {
    var expanded by remember { mutableStateOf(false) }
    MoreButton(entries, expanded, { expanded = it }, modifier, contentDescription, style, size, selectedWhenOpen, sound, popupSounds, tooltip, menuSound)
}

@ExperimentalTGOneConfigUiApi
@Composable
public fun MoreButton(
    entries: List<MenuEntry>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String = MoreButtonDefaults.contentDescription,
    style: IconButtonStyle = IconButtonStyle.Ghost,
    size: ControlSize = ControlSize.Compact,
    selectedWhenOpen: Boolean = false,
    sound: UiSound? = UiSound.Click,
    popupSounds: Boolean = LocalPopupSounds.current,
    tooltip: String? = contentDescription,
    menuSound: UiSound? = UiSound.Click,
) {
    val trigger = rememberPopupTrigger()
    Box(modifier) {
        IconButton(
            "dots-vertical",
            contentDescription,
            { onExpandedChange(!expanded) },
            Modifier.popupTrigger(trigger),
            style = style,
            size = size,
            selected = selectedWhenOpen && expanded,
            active = expanded,
            tooltip = if (expanded) null else tooltip,
            sound = sound,
        )
        CompositionLocalProvider(LocalPopupSounds provides popupSounds, LocalMenuSound provides menuSound) {
            TriggeredDropdownMenu(expanded, { onExpandedChange(false) }, entries, trigger, placement = Placement.BelowEnd)
        }
    }
}
