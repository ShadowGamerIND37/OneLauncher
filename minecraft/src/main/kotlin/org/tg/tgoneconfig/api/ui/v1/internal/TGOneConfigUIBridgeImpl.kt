package org.tg.tgoneconfig.api.ui.v1.internal

import java.util.concurrent.atomic.AtomicBoolean
import net.minecraft.client.Minecraft
import org.apache.logging.log4j.LogManager
import org.tg.tgoneconfig.api.platform.v1.Platform
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.TGOneConfigUIBridge
import org.tg.tgoneconfig.api.ui.v1.page.TGOneConfigPage
import org.tg.tgoneconfig.internal.TGOneConfig
import org.polyfrost.compose.minecraft.screen.ComposeScreen
import org.tg.tgoneconfig.internal.ui.compose.impls.TGOneConfigUIScreen
import org.tg.tgoneconfig.internal.ui.navigation.PageArguments
import org.tg.tgoneconfig.internal.ui.navigation.PageRoute
import org.tg.tgoneconfig.internal.ui.navigation.graph.ModsGraph
import org.tg.tgoneconfig.internal.ui.navigation.resolveOpenTarget
import org.tg.tgoneconfig.internal.ui.shell.ShellController

private val LOGGER = LogManager.getLogger("TG OneConfig/UI")

class TGOneConfigUIBridgeImpl : TGOneConfigUIBridge {
    override fun createScreen(route: Any?): Any {
        val target = resolveOpenTarget(route)
        clearArgument(target)
        return TGOneConfigUIScreen.forRoute(target)
    }

    override fun open(route: Any?) {
        val target = resolveOpenTarget(route)
        clearArgument(target)
        show(target)
    }

    private fun clearArgument(target: Any?) {
        if (target is PageRoute) PageArguments.clear(target.pageId)
    }

    private fun show(target: Any?) {
        if (target == null) {
            TGOneConfigUIScreen.openLastSession()
            return
        }
        // isReady stays true after the menu closes so the screen check is what tells open from closed
        val navigation = ShellController.shared.navigation
        if (navigation.isReady && Platform.screen().current<Any?>() is TGOneConfigUIScreen) {
            try {
                navigation.navigate(target)
            } catch (t: Throwable) {
                LOGGER.error("Cannot open {} in the TGOneConfig UI, opening the mods page instead", target, t)
                navigation.navigate(ModsGraph)
            }
        } else {
            Platform.screen().display(TGOneConfigUIScreen.forRoute(target))
        }
    }

    @OptIn(ExperimentalTGOneConfigUiApi::class)
    override fun openPage(page: TGOneConfigPage, argument: Any?) {
        val target = resolveOpenTarget(page)
        if (argument != null && target == PageRoute(page.id)) {
            PageArguments.offer(page.id, argument)
        } else {
            clearArgument(target)
        }
        show(target)
    }

    @OptIn(ExperimentalTGOneConfigUiApi::class)
    override fun takePageArgument(): Any? = PageArguments.take()

    override fun isTGOneConfigScreen(screen: Any?): Boolean = screen is TGOneConfigUIScreen

    private val startupChecked = AtomicBoolean(false)

    @OptIn(ExperimentalTGOneConfigUiApi::class)
    @Suppress("SENSELESS_COMPARISON")
    override fun ensureInitialized() {
        if (Minecraft.getInstance() == null || !startupChecked.compareAndSet(false, true)) return
        try {
            Class.forName(ComposeScreen::class.java.name, true, ComposeScreen::class.java.classLoader)
        } catch (t: Throwable) {
            LOGGER.warn("Failed to arm the TGOneConfig screen input handlers", t)
        }
        Minecraft.getInstance().schedule(Runnable { finishSkippedStartup() })
    }

    private fun finishSkippedStartup() {
        if (TGOneConfig.isInitialized()) return
        LOGGER.warn("TG OneConfig did not start with the game; starting it now so its screens work")
        try {
            TGOneConfig.INSTANCE.onInitializeClient()
        } catch (t: Throwable) {
            LOGGER.error("TG OneConfig failed to start late", t)
        }
    }
}
