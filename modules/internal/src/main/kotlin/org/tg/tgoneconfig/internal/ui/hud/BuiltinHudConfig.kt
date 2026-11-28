package org.tg.tgoneconfig.internal.ui.hud

import org.tg.tgoneconfig.api.config.v1.Config
import org.tg.tgoneconfig.api.hud.v1.HudManager
import org.tg.tgoneconfig.api.ui.v1.ModCardType
import org.tg.tgoneconfig.api.ui.v1.ModCardTypeResolver
import org.tg.tgoneconfig.api.ui.v1.ModCardTypes
import org.tg.tgoneconfig.internal.ui.api.ConfigData
import org.tg.tgoneconfig.internal.ui.api.ConfigRegistry
import org.tg.tgoneconfig.internal.ui.api.ConfigSource
import org.tg.tgoneconfig.internal.ui.search.HudDocumentSource
import org.tg.tgoneconfig.internal.ui.search.HudModCardDocumentSource
import org.tg.tgoneconfig.internal.ui.search.SearchCorpus

internal const val BUILTIN_HUD_CONFIG_ID = "tgoneconfig.builtin"
internal const val BUILTIN_HUD_ICON = "/assets/tgoneconfig/brand/tgoneconfig-icon.svg"
internal const val HUD_MOD_CARD_TYPE_ID = "tgoneconfig.huds"

private object BuiltinHudConfigData : ConfigData {
    override val id = BUILTIN_HUD_CONFIG_ID
    override val title = "TG OneConfig"
    override val icon = BUILTIN_HUD_ICON
    override val source = ConfigSource.OC
    override val category = Config.Category.OTHER
}

object BuiltinHudRegistrar {
    private val hudTypeResolver = ModCardTypeResolver { id ->
        if (isHudModCard(id)) HUD_MOD_CARD_TYPE_ID else null
    }

    @JvmStatic
    fun register() {
        ConfigRegistry.register(BuiltinHudConfigData)
        HudManager.providers().forEach { hud ->
            if (hud.configId == null) hud.configId = BUILTIN_HUD_CONFIG_ID
        }
        SearchCorpus.invalidate(HudDocumentSource, HudModCardDocumentSource)
        ModCardTypes.register(ModCardType(HUD_MOD_CARD_TYPE_ID, "HUDs", "hud", priority = 100))
        ModCardTypes.registerResolver(hudTypeResolver)
    }
}
