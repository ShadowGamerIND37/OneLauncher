package org.tg.tgoneconfig.api.ui.v1

import org.tg.tgoneconfig.api.ui.v1.page.TGOneConfigPage

/**
 * Platform bridge that builds and shows the TGOneConfig UI screen
 */
public interface TGOneConfigUIBridge {
    /** Creates the TGOneConfig UI screen opened on [route] or on the configured opening page when null */
    public fun createScreen(route: Any?): Any

    /** Shows the TGOneConfig UI on [route] navigating in place when it is already open */
    public fun open(route: Any?)

    public fun isTGOneConfigScreen(screen: Any?): Boolean = false

    @ExperimentalTGOneConfigUiApi
    public fun openPage(page: TGOneConfigPage, argument: Any?) {
        open(page)
    }

    @ExperimentalTGOneConfigUiApi
    public fun takePageArgument(): Any? = null

    @ExperimentalTGOneConfigUiApi
    public fun ensureInitialized() {}
}
