package org.tg.tgoneconfig.api.ui.v1.keybind

import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi

@ExperimentalTGOneConfigUiApi
public class ConfigKeybind internal constructor(
    public val keybind: TGOneConfigKeybind,
    public val title: String,
    public val path: String,
    public val modId: String,
    public val modTitle: String,
) {
    override fun toString(): String = "ConfigKeybind(modId=$modId, path=$path)"
}
