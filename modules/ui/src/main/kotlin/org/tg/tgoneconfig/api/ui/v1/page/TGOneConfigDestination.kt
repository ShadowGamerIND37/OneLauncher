@file:OptIn(ExperimentalTGOneConfigUiApi::class)

package org.tg.tgoneconfig.api.ui.v1.page

import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi

@ExperimentalTGOneConfigUiApi
public sealed interface TGOneConfigDestination {
    @ExperimentalTGOneConfigUiApi
    public data class Page(public val id: String) : TGOneConfigDestination

    @ExperimentalTGOneConfigUiApi
    public data class ModConfig(public val target: ModConfigTarget) : TGOneConfigDestination

    @ExperimentalTGOneConfigUiApi
    public data class Custom(public val page: TGOneConfigPage) : TGOneConfigDestination
}
