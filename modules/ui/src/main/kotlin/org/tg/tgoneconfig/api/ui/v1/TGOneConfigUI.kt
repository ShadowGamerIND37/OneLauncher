@file:OptIn(ExperimentalTGOneConfigUiApi::class)

package org.tg.tgoneconfig.api.ui.v1

import org.apache.logging.log4j.LogManager
import org.tg.tgoneconfig.api.ui.v1.internal.loadBridge
import org.tg.tgoneconfig.api.ui.v1.keybind.KeybindManager
import org.tg.tgoneconfig.api.ui.v1.page.TGOneConfigDestination
import org.tg.tgoneconfig.api.ui.v1.page.TGOneConfigPage

/**
 * Utility for opening the TGOneConfig UI
 *
 * A route is any destination registered in the TGOneConfig navigation graph such as a page a mod added itself
 *
 * Opening on a route that is not registered logs an error and falls back to the mods page
 *
 * Kotlin
 * ```
 * TGOneConfigUI.open()
 * TGOneConfigUI.open(MyModRoute)
 * ```
 *
 * Java
 * ```
 * TGOneConfigUI.open();
 * TGOneConfigUI.open(MyModRoute.INSTANCE);
 * ```
 */
public object TGOneConfigUI {
    private val LOGGER = LogManager.getLogger("TG OneConfig/UI")

    /**
     * Bridge into the TGOneConfig UI screen if present
     */
    private val bridge: TGOneConfigUIBridge? by lazy {
        loadBridge(TGOneConfigUIBridge::class.java, "the TGOneConfig UI cannot be opened")
    }

    @JvmStatic
    public fun open() {
        show(null)
    }

    @ExperimentalTGOneConfigUiApi
    @JvmStatic
    public fun open(destination: TGOneConfigDestination? = null) {
        show(routeOf(destination))
    }

    @ExperimentalTGOneConfigUiApi
    @JvmStatic
    @JvmOverloads
    public fun open(page: TGOneConfigPage, argument: Any? = null) {
        if (argument == null) {
            open(TGOneConfigDestination.Custom(page))
            return
        }
        val bridge = bridge
        if (bridge == null) {
            LOGGER.warn("Cannot open the TGOneConfig UI: no TGOneConfigUIBridge is available")
            return
        }
        bridge.openPage(page, argument)
    }

    @ExperimentalTGOneConfigUiApi
    @JvmStatic
    public val currentPageArgument: Any?
        get() = bridge?.takePageArgument()

    @ExperimentalTGOneConfigUiApi
    @JvmStatic
    public fun open(pageId: String) {
        open(TGOneConfigDestination.Page(pageId))
    }

    /**
     * Opens the TGOneConfig UI on [route] or on the page set by the user's opening behavior when null
     *
     * [route] must be a destination registered in the TGOneConfig navigation graph
     *
     * An unregistered route falls back to the mods page
     *
     * If the UI is already open it navigates to [route] in place
     */
    @Deprecated("Open a TGOneConfigDestination, a TGOneConfigPage or a page id instead")
    @JvmStatic
    public fun open(route: Any? = null) {
        show(normalizeRoute(route))
    }

    private fun show(route: Any?) {
        val bridge = bridge
        if (bridge == null) {
            LOGGER.warn("Cannot open the TGOneConfig UI: no TGOneConfigUIBridge is available")
            return
        }
        bridge.open(route)
    }

    private fun routeOf(destination: TGOneConfigDestination?): Any? = when (destination) {
        null -> null
        is TGOneConfigDestination.Page -> destination.id
        is TGOneConfigDestination.ModConfig -> destination.target
        is TGOneConfigDestination.Custom -> destination.page
    }

    private fun normalizeRoute(route: Any?): Any? = when (route) {
        is TGOneConfigDestination -> routeOf(route)
        else -> route
    }

    /**
     * Builds the TGOneConfig UI screen opened on [route] without showing it for callers that set the screen
     * themselves
     *
     * Returns null when the TGOneConfig UI is unavailable
     *
     * [route] must be a destination registered in the TGOneConfig navigation graph
     *
     * An unregistered route falls back to the mods page
     *
     * When null the screen opens on the page set by the user's opening behavior
     */
    @JvmStatic
    @JvmOverloads
    public fun createScreen(route: Any? = null): Any? = bridge?.createScreen(normalizeRoute(route))

    @ExperimentalTGOneConfigUiApi
    @JvmStatic
    public fun isTGOneConfigScreen(screen: Any?): Boolean = bridge?.isTGOneConfigScreen(screen) ?: false

    @ExperimentalTGOneConfigUiApi
    @JvmStatic
    public fun ensureInitialized() {
        try {
            Class.forName(KeybindManager::class.java.name, true, KeybindManager::class.java.classLoader)
        } catch (t: Throwable) {
            LOGGER.warn("Failed to arm the TGOneConfig keybind handlers", t)
        }
        bridge?.ensureInitialized()
    }
}
