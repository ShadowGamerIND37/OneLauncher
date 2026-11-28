package org.tg.tgoneconfig.api.ui.v1

import org.tg.tgoneconfig.api.ui.v1.internal.extensionsBridge

@ExperimentalTGOneConfigUiApi
public object ModCardOrder {
    @JvmStatic
    public fun current(): List<String> = extensionsBridge()?.modOrder() ?: emptyList()

    @JvmStatic
    public fun replace(ids: List<String>) {
        extensionsBridge()?.replaceModOrder(ids)
    }
}
