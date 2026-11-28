package org.tg.tgoneconfig.api.ui.v1.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.internal.componentsBridge

@Deprecated("Returns a placeholder width outside an option row. Use currentOptionWidthOrNull.", ReplaceWith("currentOptionWidthOrNull"))
@ExperimentalTGOneConfigUiApi
public val currentOptionWidth: Dp
    @Composable get() = componentsBridge().optionWidth()

@ExperimentalTGOneConfigUiApi
public val currentOptionWidthOrNull: Dp?
    @Composable get() = componentsBridge().optionWidthOrNull()
