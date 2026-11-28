package org.tg.tgoneconfig.api.ui.v1.internal

import org.tg.tgoneconfig.api.config.v1.Property
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.InternalTGOneConfigUiApi

@ExperimentalTGOneConfigUiApi
@InternalTGOneConfigUiApi
public interface TGOneConfigUIExtensionsBridge {
    public fun modOrder(): List<String>

    public fun replaceModOrder(ids: List<String>)

    public fun modSortModeId(): String

    public fun selectModSortMode(id: String)

    public fun refreshOption(property: Property<*>)
}

@ExperimentalTGOneConfigUiApi
private val loadedExtensionsBridge: TGOneConfigUIExtensionsBridge? by lazy {
    loadBridge(TGOneConfigUIExtensionsBridge::class.java, "the mod order and option widgets cannot be changed")
}

@ExperimentalTGOneConfigUiApi
internal fun extensionsBridge(): TGOneConfigUIExtensionsBridge? = loadedExtensionsBridge
