@file:OptIn(ExperimentalTGOneConfigUiApi::class)
@file:JvmName("VanillaKeybinds")

package org.tg.tgoneconfig.api.ui.v1.platform

import net.minecraft.client.KeyMapping
import org.tg.tgoneconfig.api.config.v1.Property
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.keybind.KeybindHelper
import org.tg.tgoneconfig.api.ui.v1.keybind.KeybindMetadata
import org.tg.tgoneconfig.api.ui.v1.keybind.TGOneConfigKeybind
import org.tg.tgoneconfig.internal.ui.keybind.VanillaKeyMappingProperties

@ExperimentalTGOneConfigUiApi
fun KeybindHelper.Companion.vanillaProperty(keyMapping: KeyMapping): Property<TGOneConfigKeybind?> =
    VanillaKeyMappingProperties.propertyFor(keyMapping)
