package org.tg.tgoneconfig.internal.ui.components

internal fun iconButtonLabel(iconName: String): String = when (iconName) {
    "left-arrow" -> translated("tgoneconfig.button.back", "Back")
    "right-arrow" -> translated("tgoneconfig.button.forward", "Forward")
    "close" -> translated("tgoneconfig.button.close", "Close")
    "folder" -> translated("tgoneconfig.button.browse", "Browse")
    "trash" -> translated("tgoneconfig.list.remove", "Remove")
    else -> iconName.replace('-', ' ').replaceFirstChar { it.uppercase() }
}
