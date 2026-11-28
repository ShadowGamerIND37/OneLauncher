package org.tg.tgoneconfig.api.ui.v1.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.internal.componentsBridge

@ExperimentalTGOneConfigUiApi
@Composable
public fun Shape.concentric(inset: Dp): Shape = componentsBridge().concentric(this, inset, 0.dp)

@ExperimentalTGOneConfigUiApi
@Composable
public fun Shape.concentric(inset: Dp, minRadius: Dp): Shape = componentsBridge().concentric(this, inset, minRadius)
