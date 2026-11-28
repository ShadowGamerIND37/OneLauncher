package org.tg.tgoneconfig.api.ui.v1.page

import androidx.compose.runtime.Composable
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.internal.componentsBridge

@Deprecated("Returns an empty query outside a TGOneConfig page. Use currentPageSearchQueryOrNull.", ReplaceWith("currentPageSearchQueryOrNull"))
@ExperimentalTGOneConfigUiApi
public val currentPageSearchQuery: String
    @Composable get() = componentsBridge().pageSearchQuery()

@ExperimentalTGOneConfigUiApi
public val currentPageSearchQueryOrNull: String?
    @Composable get() = componentsBridge().pageSearchQueryOrNull()
