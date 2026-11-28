@file:JvmName("HudColors")
@file:OptIn(ExperimentalTGOneConfigUiApi::class)

package org.tg.tgoneconfig.api.hud.v1

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import org.tg.compose.render.PolyColor
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi

@ExperimentalTGOneConfigUiApi
public fun PolyColor.toComposeColor(): Color = Color(argb)

@ExperimentalTGOneConfigUiApi
public fun Color.toPolyColor(): PolyColor = PolyColor(toArgb())

@ExperimentalTGOneConfigUiApi
public fun Int.argbToColor(): Color = Color(this)
