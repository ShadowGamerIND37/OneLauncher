package org.tg.tgoneconfig.api.ui.v1.search

import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.internal.requireSearchBridge
import org.tg.tgoneconfig.api.ui.v1.internal.searchBridge

@ExperimentalTGOneConfigUiApi
public object SearchProviders {
    @JvmStatic
    public fun register(provider: SearchProvider) {
        requireSearchBridge().register(provider)
    }

    @JvmStatic
    public fun unregister(provider: SearchProvider): Boolean = searchBridge()?.unregister(provider) ?: false

    @JvmStatic
    public fun documentIds(): Set<String> = searchBridge()?.documentIds() ?: emptySet()

    @JvmStatic
    public fun document(id: String): SearchDocument? = searchBridge()?.document(id)
}
