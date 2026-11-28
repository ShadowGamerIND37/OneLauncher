package org.tg.tgoneconfig.api.ui.v1.page

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.Snapshot
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi

@ExperimentalTGOneConfigUiApi
public object TGOneConfigPages {
    private val ID = Regex("[a-z0-9_.-]+:[a-z0-9_./-]+")

    private val lock = Any()

    private val pages = mutableStateListOf<TGOneConfigPage>()

    @JvmStatic
    public fun register(page: TGOneConfigPage) {
        synchronized(lock) {
            require(pages.none { it.id == page.id }) { "A page with id ${page.id} is already registered" }
            Snapshot.withMutableSnapshot { pages.add(page) }
        }
    }

    @JvmStatic
    public fun registerIfAbsent(page: TGOneConfigPage): Boolean = synchronized(lock) {
        if (pages.any { it.id == page.id }) return@synchronized false
        Snapshot.withMutableSnapshot { pages.add(page) }
        true
    }

    @JvmStatic
    public fun unregister(id: String): Boolean = synchronized(lock) { Snapshot.withMutableSnapshot { pages.removeAll { it.id == id } } }

    @JvmStatic
    public fun all(): List<TGOneConfigPage> =
        pages.toList().sortedWith(compareBy<TGOneConfigPage>({ it.group.order }, { it.group.id }, { it.order }))

    @JvmStatic
    public fun byId(id: String): TGOneConfigPage? = pages.toList().firstOrNull { it.id == id }

    @JvmStatic
    public fun modConfig(modId: String): ModConfigTarget = ModConfigTarget(modId, null)

    @JvmStatic
    public fun modConfig(modId: String, category: String?): ModConfigTarget = ModConfigTarget(modId, category)

    internal fun requireNamespacedId(id: String) {
        require(ID.matches(id)) { "Id $id must be namespaced like \"mymod:page\" using lowercase letters, digits and _ . - /" }
    }
}
