package org.tg.tgoneconfig.internal.ui.components.settings

import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.tg.tgoneconfig.api.config.v1.Property
import org.tg.tgoneconfig.api.ui.v1.components.KeybindField
import org.tg.tgoneconfig.api.ui.v1.keybind.KeyModifiers
import org.tg.tgoneconfig.api.ui.v1.keybind.KeybindManager
import org.tg.tgoneconfig.api.ui.v1.keybind.TGOneConfigKeybind
import org.tg.tgoneconfig.internal.ui.api.settings.KeybindOptionData

@Suppress("UNCHECKED_CAST")
internal fun writeKeybind(prop: Property<*>, keys: IntArray?, mouse: IntArray?, mods: Byte): TGOneConfigKeybind {
    val old = prop.get() as? TGOneConfigKeybind
    // copyWith keeps the runtime subtype so a rebound BindNotInScreen is not demoted to the base class
    val newKeybind = old?.copyWith(keys, mouse, mods) ?: TGOneConfigKeybind(keys, mouse, mods, 0L) { true }
    (prop as Property<Any>).set(newKeybind)
    val applied = prop.get() as? TGOneConfigKeybind ?: newKeybind
    applied.keyCodes = keys
    applied.mouseBtns = mouse
    applied.mods = mods
    KeybindManager.replace(old, applied)
    KeybindConflicts.revision.intValue++
    return applied
}

internal fun Property<*>.isKeybind(): Boolean =
    runCatching { get() is TGOneConfigKeybind }.getOrDefault(false) || getMetadata<Any?>("default") is TGOneConfigKeybind

@Suppress("UNCHECKED_CAST")
fun resetKeybindOption(prop: Property<*>) {
    val def = prop.getMetadata<Any?>("default") as? TGOneConfigKeybind
    if (def == null && prop.getMetadata<Boolean>("defaultNull") == true) {
        val old = prop.get() as? TGOneConfigKeybind
        (prop as Property<Any?>).setAsReferential(null)
        if (old != null) KeybindManager.unregister(old)
        KeybindConflicts.revision.intValue++
    } else if (def == null) {
        writeKeybind(prop, null, null, KeyModifiers.NONE)
    } else if (prop.get() == null) {
        (prop as Property<Any?>).setAsReferential(def.copyWith(def.keyCodes?.clone(), def.mouseBtns?.clone(), def.mods))
        (prop.get() as? TGOneConfigKeybind)?.let(KeybindManager::register)
        KeybindConflicts.revision.intValue++
    } else {
        writeKeybind(prop, def.keyCodes?.clone(), def.mouseBtns?.clone(), def.mods)
    }
    bumpResetEpoch(prop)
}

fun unbindKeybindOption(prop: Property<*>) {
    writeKeybind(prop, null, null, KeyModifiers.NONE)
    bumpResetEpoch(prop)
}

@Composable
fun KeybindOption(data: KeybindOptionData) {
    KeybindOption(data, true)
}

@Composable
fun KeybindOption(data: KeybindOptionData, enabled: Boolean) {
    val singleKey = data.prop.getMetadata<Boolean>("singleKey") == true
    var currentKeybind by remember(data.prop) {
        mutableStateOf(data.prop.get() as? TGOneConfigKeybind, neverEqualPolicy())
    }
    val conflict = data.prop in KeybindConflicts.conflictingProps()

    KeybindField(
        keybind = currentKeybind,
        onChange = { next ->
            currentKeybind = writeKeybind(data.prop, next?.keyCodes, next?.mouseBtns, next?.mods ?: KeyModifiers.NONE)
        },
        modifier = Modifier.widthIn(max = LocalOptionWidth.current),
        enabled = enabled,
        conflict = conflict,
        singleKey = singleKey,
    )
}
