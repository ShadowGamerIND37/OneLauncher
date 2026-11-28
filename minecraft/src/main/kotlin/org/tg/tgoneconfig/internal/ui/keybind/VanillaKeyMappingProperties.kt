@file:OptIn(ExperimentalTGOneConfigUiApi::class)

package org.tg.tgoneconfig.internal.ui.keybind

import com.mojang.blaze3d.platform.InputConstants
import java.util.IdentityHashMap
import net.minecraft.client.KeyMapping
import org.apache.logging.log4j.LogManager
import org.tg.tgoneconfig.api.config.v1.Node
import org.tg.tgoneconfig.api.config.v1.Property
import org.tg.tgoneconfig.api.config.v1.Tree
import org.tg.tgoneconfig.api.config.v1.Visualizer
import org.tg.tgoneconfig.api.config.v1.dsl.visualizer
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.keybind.KeyModifiers
import org.tg.tgoneconfig.api.ui.v1.keybind.KeybindMetadata
import org.tg.tgoneconfig.api.ui.v1.keybind.TGOneConfigKeybind
import org.tg.tgoneconfig.api.ui.v1.keybind.internal.MinecraftKeybindCodec

//? if > 1.8.9 {
import org.tg.tgoneconfig.internal.mixin.keybind.KeyMappingAccessor
//?} else
//import org.tg.tgoneconfig.internal.legacy.KeyCodes

object VanillaKeyMappingProperties {
    private val LOGGER = LogManager.getLogger("TG OneConfig/Keybinds")
    private val properties = IdentityHashMap<KeyMapping, VanillaKeyMappingProperty>()

    @JvmStatic
    @Synchronized
    fun propertyFor(mapping: KeyMapping): Property<TGOneConfigKeybind?> =
        properties.getOrPut(mapping) { VanillaKeyMappingProperty(mapping) }

    @JvmStatic
    fun onKeyChanged(mapping: KeyMapping) {
        val property = synchronized(this) { properties[mapping] } ?: return
        pull(property)
    }

    @JvmStatic
    fun onOptionsSaved() {
        val all = synchronized(this) {
            if (properties.isEmpty()) return
            properties.values.toList()
        }
        for (property in all) pull(property)
    }

    private fun pull(property: VanillaKeyMappingProperty) {
        try {
            property.pullFromVanilla()
        } catch (t: Throwable) {
            LOGGER.error("Failed to sync keybind property '{}' from Minecraft's Controls", property.id, t)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private val KEYBIND_TYPE = TGOneConfigKeybind::class.java as Class<TGOneConfigKeybind?>

    private class VanillaKeyMappingProperty(private val mapping: KeyMapping) :
        Property<TGOneConfigKeybind?>("vanilla.${mapping.name}", mapping.name, null, KEYBIND_TYPE) {
        private var value: TGOneConfigKeybind? = read(mapping)
        private var incoming: TGOneConfigKeybind? = null
        private var writing = false
        private var overwriting = false

        init {
            visualizer = Visualizer.KeybindVisualizer::class.java
            addMetadata("titleKey", mapping.name)
            addMetadata("singleKey", true)
            addMetadata("default", toKeybind(mapping.defaultKey))
            addMetadata(KeybindMetadata.NO_MIRROR, true)
        }

        override fun get(): TGOneConfigKeybind? = value

        override fun overwrite(with: Node, preserveMissingOptions: Boolean, skipOverwritten: Boolean, root: Tree?) {
            overwriting = true
            try {
                super.overwrite(with, preserveMissingOptions, skipOverwritten, root)
            } finally {
                overwriting = false
                value = read(mapping)
            }
        }

        override fun set0(value: TGOneConfigKeybind?) {
            if (overwriting) return
            if (value != null && value === incoming) {
                this.value = value
                return
            }
            writing = true
            try {
                write(mapping, value)
            } finally {
                writing = false
            }
            this.value = read(mapping)
        }

        fun pullFromVanilla() {
            if (writing || incoming != null) return
            val now = read(mapping)
            if (sameInput(value, now)) return
            incoming = now
            try {
                setReferential(now)
            } finally {
                incoming = null
            }
        }
    }

    private fun sameInput(a: TGOneConfigKeybind?, b: TGOneConfigKeybind): Boolean {
        if (a == null || !a.isBound) return !b.isBound
        return b.isBound && a.boundCode == b.boundCode && a.isMousePrimary == b.isMousePrimary
    }

    private fun read(mapping: KeyMapping): TGOneConfigKeybind {
        //? if > 1.8.9 {
        val key = (mapping as KeyMappingAccessor).`tgoneconfig$getKey`()
        //?} else
        //val key = KeyCodes.fromLegacy(mapping.keyCode)
        return toKeybind(key)
    }

    private fun toKeybind(key: InputConstants.Key): TGOneConfigKeybind {
        if (key == InputConstants.UNKNOWN) return unbound()
        val value = key.value
        return when {
            key.type == InputConstants.Type.MOUSE -> TGOneConfigKeybind(null, intArrayOf(value), KeyModifiers.NONE, 0L, NO_OP)
            value >= 0 -> TGOneConfigKeybind(intArrayOf(value), null, KeyModifiers.NONE, 0L, NO_OP)
            else -> unbound()
        }
    }

    private fun unbound(): TGOneConfigKeybind = TGOneConfigKeybind(null, null, KeyModifiers.NONE, 0L, NO_OP)

    private fun write(mapping: KeyMapping, bind: TGOneConfigKeybind?) {
        val key = when {
            bind == null || !bind.isBound -> InputConstants.UNKNOWN
            bind.isMousePrimary -> MinecraftKeybindCodec.mouse(bind.boundCode)
            else -> MinecraftKeybindCodec.keysym(bind.boundCode)
        }
        mapping.setKey(key)
        KeyMapping.resetMapping()
        minecraftOrNull()?.options?.save()
    }

    private val NO_OP: (Boolean) -> Boolean = { true }
}
