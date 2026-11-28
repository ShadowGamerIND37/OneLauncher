package org.tg.tgoneconfig.api.ui.v1.search

import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi

@ExperimentalTGOneConfigUiApi
public class SearchDocument internal constructor(public val id: String, public val scopes: Set<SearchScope>, public val metadata: SearchMetadata) {
    override fun toString(): String = "SearchDocument(id=$id, scopes=$scopes)"
}
