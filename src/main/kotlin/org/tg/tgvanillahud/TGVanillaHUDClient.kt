package org.tg.tgvanillahud

import net.fabricmc.api.ClientModInitializer
//? if > 1.8.9 {
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
//?} else {
/*import org.tg.tgoneconfig.api.event.v1.events.InitializationEvent
import org.tg.tgoneconfig.api.event.v1.events.TickEvent
import org.tg.tgoneconfig.api.event.v1.invoke.EventHandler
*///?}
import org.tg.tgoneconfig.api.event.v1.EventManager
import org.tg.tgoneconfig.api.event.v1.events.WorldEvent
import java.util.function.Consumer
import org.tg.tgoneconfig.api.hud.v1.HudGroups
import org.tg.tgoneconfig.api.hud.v1.HudManager
import org.tg.tgvanillahud.compat.HudElementCompat
import org.tg.tgvanillahud.hud.AirHud
import org.tg.tgvanillahud.hud.ArmorHud
import org.tg.tgvanillahud.hud.ExperienceBarHud
import org.tg.tgvanillahud.hud.ExperienceLevelHud
import org.tg.tgvanillahud.hud.HealthHud
import org.tg.tgvanillahud.hud.HotbarHud
import org.tg.tgvanillahud.hud.Huds
import org.tg.tgvanillahud.hud.HungerHud
import org.tg.tgvanillahud.hud.SubtitleHud
import org.tg.tgvanillahud.hud.TitleHud
import org.tg.tgvanillahud.hud.VanillaHud
import org.tg.tgvanillahud.render.HudCapture
import org.tg.tgvanillahud.util.ForceDefaultPosition
import org.tg.tgvanillahud.util.HudConfigMigrator

object TGVanillaHUDClient : ClientModInitializer {
    override fun onInitializeClient() {
        // before TGOneConfig loads the HUDs, or it saves what it read back over the migrated files
        HudConfigMigrator.migrate()

        //? if = 1.8.9 {
        /*// 1.8.9 runs entrypoints before Minecraft.init, so the HUDs cannot be built yet: the font
        // and the language files they measure against do not exist. Defer to the end of init, at
        // the lowest priority so TGOneConfig's own setup has already run.
        object : EventHandler<InitializationEvent>() {
            override fun handle(event: InitializationEvent): Boolean {
                setup()
                return true
            }

            override fun getEventClass(): Class<InitializationEvent> = InitializationEvent::class.java

            override fun getPriority(): Int = Int.MIN_VALUE
        }.register()
        *///?} else
        setup()
    }

    private fun setup() {
        Huds.all.forEach {
            HudManager.register(it, "tgvanillahud", "/assets/tgvanillahud/tgvanillahud_dark.svg")
        }

        HudGroups.registerDefault(
            "tgvanillahud-hotbar", "Hotbar",
            HotbarHud::class.java, HealthHud::class.java, ArmorHud::class.java, HungerHud::class.java, AirHud::class.java,
            ExperienceBarHud::class.java, ExperienceLevelHud::class.java,
        )
        HudGroups.registerDefault("tgvanillahud-title", "Title & Subtitle", TitleHud::class.java, SubtitleHud::class.java)

        //? if > 1.8.9 {
        ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick { onTick() })
        //?} else {
        /*// same per tick refresh the newer versions get from the Fabric end tick event: without it
        // the editor previews keep whatever they measured when the editor opened
        EventManager.register(TickEvent.End::class.java, Consumer { onTick() })
        *///?}

        // close every icon, or animated ones keep the atlas redrawing even after the world unloads
        EventManager.register(WorldEvent.Unload::class.java, Consumer { HudCapture.closeIcons() })

        HudElementCompat.init()
    }

    private fun onTick() {
        ForceDefaultPosition.tick()
        VanillaHud.markRefreshDue()
        if (HudManager.isEditing) VanillaHud.refreshAll()
    }
}
