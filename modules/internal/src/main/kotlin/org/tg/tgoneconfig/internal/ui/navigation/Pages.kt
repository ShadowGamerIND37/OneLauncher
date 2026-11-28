package org.tg.tgoneconfig.internal.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.apache.logging.log4j.LogManager
import org.tg.tgoneconfig.api.ui.v1.page.ModConfigTarget
import org.tg.tgoneconfig.api.ui.v1.page.TGOneConfigPage
import org.tg.tgoneconfig.api.ui.v1.page.TGOneConfigPages
import org.tg.tgoneconfig.api.ui.v1.page.PageGroup
import org.tg.tgoneconfig.api.ui.v1.page.PageText
import org.tg.tgoneconfig.internal.ui.components.resolve
import org.tg.tgoneconfig.internal.ui.components.translated
import org.tg.tgoneconfig.internal.ui.navigation.graph.ChangeLogGraph
import org.tg.tgoneconfig.internal.ui.navigation.graph.CreditsGraph
import org.tg.tgoneconfig.internal.ui.navigation.graph.KeybindsGraph
import org.tg.tgoneconfig.internal.ui.navigation.graph.ModConfigRoute
import org.tg.tgoneconfig.internal.ui.navigation.graph.ModsGraph
import org.tg.tgoneconfig.internal.ui.navigation.graph.PreferencesGraph
import org.tg.tgoneconfig.internal.ui.navigation.graph.ProfilesGraph
import org.tg.tgoneconfig.internal.ui.navigation.graph.ThemesGraph
import org.tg.tgoneconfig.internal.ui.screens.Changelog
import org.tg.tgoneconfig.internal.ui.screens.Credits
import org.tg.tgoneconfig.internal.ui.screens.Keybinds
import org.tg.tgoneconfig.internal.ui.screens.Mods
import org.tg.tgoneconfig.internal.ui.screens.Preferences
import org.tg.tgoneconfig.internal.ui.screens.Profiles
import org.tg.tgoneconfig.internal.ui.screens.Themes
import org.tg.tgoneconfig.internal.ui.shell.LocalShell
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

@Serializable
data class PageRoute(val pageId: String)

internal class BuiltinPage(
    key: String,
    englishTitle: String,
    icon: String,
    group: PageGroup,
    override val order: Int,
    val route: Any,
    private val content: @Composable () -> Unit,
) : TGOneConfigPage(
    "tgoneconfig:$key",
    PageText.translatable("tgoneconfig.page.$key.title", englishTitle),
    icon,
    group,
) {
    override val searchPlaceholder: PageText =
        PageText.translatable("tgoneconfig.page.$key.search", "Filter ${englishTitle.lowercase(Locale.ROOT)}")

    @Composable
    override fun Content() = content()
}

object BuiltinPages {
    internal val all: List<BuiltinPage> = listOf(
        BuiltinPage("mods", "Mods", "settings", PageGroup.MODS_AND_OPTIONS, 100, ModsGraph) { Mods() },
        BuiltinPage("profiles", "Profiles", "profiles", PageGroup.MODS_AND_OPTIONS, 200, ProfilesGraph) { Profiles() },
        BuiltinPage("keybinds", "Keybinds", "keyboard", PageGroup.MODS_AND_OPTIONS, 300, KeybindsGraph) { Keybinds() },
        BuiltinPage("themes", "Themes", "paintbrush", PageGroup.PERSONALIZATION, 100, ThemesGraph) { Themes() },
        BuiltinPage("preferences", "Preferences", "cog", PageGroup.PERSONALIZATION, 200, PreferencesGraph) { Preferences() },
        BuiltinPage("changelog", "Changelog", "refresh", PageGroup.ONECONFIG, 100, ChangeLogGraph) { Changelog() },
        BuiltinPage("credits", "Credits", "star", PageGroup.ONECONFIG, 200, CreditsGraph) { Credits() },
    )

    @Volatile
    private var registered = false

    @JvmStatic
    fun register() {
        if (registered) return
        synchronized(this) {
            if (registered) return
            for (page in all) {
                val existing = TGOneConfigPages.byId(page.id)
                if (existing === page) continue
                if (existing != null) {
                    LOGGER.warn("Replacing page {} from {} with the built-in TGOneConfig page because the tgoneconfig namespace is reserved", page.id, existing.javaClass.name)
                    TGOneConfigPages.unregister(page.id)
                }
                TGOneConfigPages.register(page)
            }
            registered = true
        }
    }
}

private val LOGGER = LogManager.getLogger("TG OneConfig/UI")

fun registeredPages(): List<TGOneConfigPage> {
    BuiltinPages.register()
    return TGOneConfigPages.all()
}

fun pageRoute(page: TGOneConfigPage): Any = (page as? BuiltinPage)?.route ?: PageRoute(page.id)

fun resolveOpenTarget(target: Any?): Any? = when (target) {
    is String -> {
        BuiltinPages.register()
        TGOneConfigPages.byId(target)?.let(::pageRoute) ?: run {
            LOGGER.error("Cannot open unknown TGOneConfig page {}, opening the mods page instead", target)
            ModsGraph
        }
    }
    is TGOneConfigPage -> {
        BuiltinPages.register()
        val registered = TGOneConfigPages.byId(target.id)
        when {
            registered === target -> pageRoute(target)
            registered != null -> {
                LOGGER.warn("TG OneConfig page {} was opened through an instance that is not the registered one, opening the registered page", target.id)
                pageRoute(registered)
            }
            else -> {
                LOGGER.error("Cannot open TGOneConfig page {} because it is not registered, opening the mods page instead", target.id)
                ModsGraph
            }
        }
    }
    is ModConfigTarget -> ModConfigRoute(target.modId, target.category)
    else -> target
}

object PageArguments {
    private class Delivery(val pageId: String, val argument: Any) {
        val offeredAt: Long = System.nanoTime()
    }

    private val PENDING_TTL_NANOS = TimeUnit.SECONDS.toNanos(10)

    private var revision by mutableIntStateOf(0)

    @Volatile
    private var pending: Delivery? = null

    private val retained = ConcurrentHashMap<String, Delivery>()
    private val live = ConcurrentHashMap<String, Int>()
    private val scope = ThreadLocal<String?>()

    fun offer(pageId: String, argument: Any) {
        pending = Delivery(pageId, argument)
        Snapshot.withMutableSnapshot { revision++ }
    }

    fun clear(pageId: String) {
        val wasPending = pending?.pageId == pageId
        if (wasPending) pending = null
        if (retained.remove(pageId) != null || wasPending) Snapshot.withMutableSnapshot { revision++ }
    }

    fun retained(pageId: String): Any? = retained[pageId]?.argument

    fun take(): Any? {
        val pageId = scope.get() ?: live.keys.singleOrNull() ?: return null
        return retained[pageId]?.argument
    }

    private fun claim(pageId: String): Delivery? {
        val offered = pending ?: return null
        if (System.nanoTime() - offered.offeredAt > PENDING_TTL_NANOS) {
            pending = null
            return null
        }
        if (offered.pageId != pageId) return null
        pending = null
        retained[pageId] = offered
        return offered
    }

    private class Latest(var delivery: Delivery?)

    @Composable
    fun Deliver(pageId: String, content: @Composable () -> Unit) {
        val round = revision
        val delivered = remember(pageId, round) { claim(pageId) }
        val latest = remember(pageId) { Latest(null) }
        if (delivered != null) latest.delivery = delivered
        else if (latest.delivery != null && retained[pageId] == null) latest.delivery = null
        DisposableEffect(pageId) {
            live.merge(pageId, 1, Int::plus)
            onDispose {
                live.compute(pageId) { _, count -> if (count == null || count <= 1) null else count - 1 }
                latest.delivery?.let { retained.remove(pageId, it) }
            }
        }
        key(latest.delivery) {
            val previous = scope.get()
            scope.set(pageId)
            content()
            scope.set(previous)
        }
    }
}

fun NavGraphBuilder.pageDestinations() {
    composable<PageRoute> { entry ->
        val id = entry.toRoute<PageRoute>().pageId
        val page = TGOneConfigPages.byId(id)
        if (page != null) {
            PageArguments.Deliver(id) { page.Content() }
            return@composable
        }
        val shell = LocalShell.current
        LaunchedEffect(id) {
            LOGGER.warn("TG OneConfig page {} is not registered, opening the mods page instead", id)
            shell.navigation.discard(PageRoute(id), ModsGraph)
        }
    }
}

fun NavBackStackEntry?.isOn(route: Any): Boolean {
    val entry = this ?: return false
    val destination = entry.destination
    if (route is PageRoute) {
        return destination.hasRoute(PageRoute::class) && runCatching { entry.toRoute<PageRoute>().pageId }.getOrNull() == route.pageId
    }
    return destination.hierarchy.any { it.hasRoute(route::class) }
}

fun NavBackStackEntry?.currentPage(): TGOneConfigPage? {
    val entry = this ?: return null
    if (entry.destination.hasRoute(PageRoute::class)) {
        val id = runCatching { entry.toRoute<PageRoute>().pageId }.getOrNull() ?: return null
        return TGOneConfigPages.byId(id)
    }
    return registeredPages().firstOrNull { it is BuiltinPage && entry.isOn(it.route) }
}

val TGOneConfigPage.inAboutMenu: Boolean get() = this is BuiltinPage && group == PageGroup.ONECONFIG

class SidebarEntry(val icon: String, val label: String, val route: Any)

class SidebarSection(val id: String, val title: String, val entries: List<SidebarEntry>)

fun sidebarSections(): List<SidebarSection> {
    val pages = registeredPages()
    return NavigationGroups.map { group ->
        SidebarSection(
            group.id,
            group.title.resolve(),
            group.routes.map { def ->
                val page = pages.firstOrNull { pageRoute(it) == def.route }
                val label = page?.title?.resolve()
                    ?: def.id.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                SidebarEntry(def.icon, label, def.route)
            },
        )
    }
}

fun NavBackStackEntry?.currentPageTitle(): String? =
    currentPage()?.title?.resolve()
        ?: sidebarSections().asSequence().flatMap { it.entries }.firstOrNull { isOn(it.route) }?.label

fun searchPlaceholder(entry: NavBackStackEntry?): String {
    if (entry?.destination?.hasRoute(ModConfigRoute::class) == true) return translated("tgoneconfig.header.filter.mod", "Filter this mod")
    return searchPlaceholder(entry.currentPage(), entry.currentPageTitle())
}

fun searchPlaceholder(page: TGOneConfigPage?, title: String?): String {
    page?.searchPlaceholder?.let { return it.resolve() }
    if (title == null) return translated("tgoneconfig.header.filter", "Filter")
    return translated("tgoneconfig.header.filter.page", "Filter %s", title)
}
