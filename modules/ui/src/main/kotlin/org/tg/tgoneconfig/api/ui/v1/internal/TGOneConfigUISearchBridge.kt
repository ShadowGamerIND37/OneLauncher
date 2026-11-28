package org.tg.tgoneconfig.api.ui.v1.internal

import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.InternalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.search.SearchDocument
import org.tg.tgoneconfig.api.ui.v1.search.SearchMetadata
import org.tg.tgoneconfig.api.ui.v1.search.SearchProvider
import org.tg.tgoneconfig.api.ui.v1.search.SearchScope

@ExperimentalTGOneConfigUiApi
@InternalTGOneConfigUiApi
public interface TGOneConfigUISearchBridge {
    public fun register(provider: SearchProvider)

    public fun unregister(provider: SearchProvider): Boolean

    public fun documentIds(): Set<String>

    public fun document(id: String): SearchDocument?
}

@ExperimentalTGOneConfigUiApi
@InternalTGOneConfigUiApi
public object SearchInterop {
    @JvmStatic
    public fun scope(key: String): SearchScope = SearchScope(key)

    @JvmStatic
    public fun metadata(
        title: String?,
        id: String?,
        description: String?,
        section: String?,
        category: String?,
        subcategory: String?,
        tags: List<String>,
        modTitle: String?,
        modDescription: String?,
        path: String?,
    ): SearchMetadata = SearchMetadata(title, id, description, section, category, subcategory, tags, modTitle, modDescription, path)

    @JvmStatic
    public fun document(id: String, scopes: Set<SearchScope>, metadata: SearchMetadata): SearchDocument = SearchDocument(id, scopes, metadata)
}

@ExperimentalTGOneConfigUiApi
private val loadedSearchBridge: TGOneConfigUISearchBridge? by lazy {
    loadBridge(TGOneConfigUISearchBridge::class.java, "TG OneConfig search providers cannot be registered")
}

@ExperimentalTGOneConfigUiApi
internal fun searchBridge(): TGOneConfigUISearchBridge? = loadedSearchBridge

@ExperimentalTGOneConfigUiApi
internal fun requireSearchBridge(): TGOneConfigUISearchBridge =
    loadedSearchBridge ?: error("No TGOneConfigUISearchBridge is available; TGOneConfig search providers cannot be registered")
