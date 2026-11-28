package org.tg.tgoneconfig.internal.ui.hud

import org.tg.tgoneconfig.api.hud.v1.HudManager
import org.tg.tgoneconfig.api.hud.v1.HudPreset
import org.tg.tgoneconfig.api.platform.v1.ModInfo
import org.tg.tgoneconfig.internal.ui.api.ConfigData
import org.tg.tgoneconfig.internal.ui.api.ConfigRegistry
import org.tg.tgoneconfig.internal.ui.components.asRenderText

internal fun configForHud(configId: String): ConfigData? {
    val modId = configId.removeSuffix(".json").substringBefore('/')
    return ConfigRegistry.findById(configId)
        ?: ConfigRegistry.findById("$configId.json")
        ?: ConfigRegistry.configs.firstOrNull { it.id.removeSuffix(".json") == modId }
}

internal fun modNameFor(configId: String): String? {
    val modId = configId.removeSuffix(".json").substringBefore('/')
    ModInfo.loadedMods.firstOrNull { it.id == modId }?.name?.let { return it }
    return configForHud(configId)?.title?.asRenderText()
}

internal val HudPreset.libraryConfigId: String?
    get() = configId ?: members.firstNotNullOfOrNull { HudManager.getProvider(it)?.configId }
