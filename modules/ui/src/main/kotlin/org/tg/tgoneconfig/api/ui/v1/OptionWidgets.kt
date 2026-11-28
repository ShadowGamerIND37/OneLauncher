package org.tg.tgoneconfig.api.ui.v1

import org.tg.tgoneconfig.api.config.v1.Property
import org.tg.tgoneconfig.api.ui.v1.internal.extensionsBridge

@ExperimentalTGOneConfigUiApi
public object OptionWidgets {
    @JvmStatic
    public fun refresh(property: Property<*>) {
        extensionsBridge()?.refreshOption(property)
    }
}
