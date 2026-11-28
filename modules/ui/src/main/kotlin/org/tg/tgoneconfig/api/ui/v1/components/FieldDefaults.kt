@file:OptIn(ExperimentalTGOneConfigUiApi::class)

package org.tg.tgoneconfig.api.ui.v1.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.a11y.Motion
import org.tg.tgoneconfig.api.ui.v1.theme.TGOneConfigTheme

@ExperimentalTGOneConfigUiApi
public enum class FieldState {
    Rest,

    Hovered,

    Focused,

    Invalid,
    ;

    public companion object {
        @JvmStatic
        public fun of(hovered: Boolean, focused: Boolean, invalid: Boolean = false): FieldState = when {
            invalid -> Invalid
            focused -> Focused
            hovered -> Hovered
            else -> Rest
        }
    }
}

@ExperimentalTGOneConfigUiApi
public object FieldDefaults {
    public val fill: Color
        @Composable get() = TGOneConfigTheme.current.componentBackground

    public val searchFill: Color
        @Composable get() = TGOneConfigTheme.current.chipBackground

    public val border: Color
        @Composable get() = TGOneConfigTheme.current.borderColor

    public val hoverBorder: Color
        @Composable get() = TGOneConfigTheme.current.textColorSecondary

    public val focusBorder: Color
        @Composable get() = TGOneConfigTheme.accent

    public val errorBorder: Color
        @Composable get() = TGOneConfigTheme.current.error

    public val shape: Shape
        @Composable get() = TGOneConfigTheme.current.shapes.field

    public val contentSpacing: Dp = 10.dp

    public fun horizontalPadding(size: ControlSize): Dp = when (size) {
        ControlSize.Compact, ControlSize.Regular, ControlSize.Large -> 12.dp
        ControlSize.ExtraLarge -> 16.dp
    }

    @Composable
    public fun borderColor(state: FieldState): Color = when (state) {
        FieldState.Rest -> border
        FieldState.Hovered -> hoverBorder
        FieldState.Focused -> focusBorder
        FieldState.Invalid -> errorBorder
    }
}

@ExperimentalTGOneConfigUiApi
@Composable
public fun Modifier.fieldFrame(
    state: FieldState,
    fill: Color = FieldDefaults.fill,
    shape: Shape = FieldDefaults.shape,
    glass: Boolean = true,
    hoverPull: Boolean = false,
    enabled: Boolean = true,
): Modifier {
    val target = FieldDefaults.borderColor(if (enabled || state == FieldState.Invalid) state else FieldState.Rest)
    val border by Motion.animateColorAsState(target, Motion.colorSpec(), "fieldBorder")
    val shownFill by Motion.animateColorAsState(fill, Motion.colorSpec(), "fieldFill")
    return glassFrame(shape, { shownFill }, { border }, hover = hoverPull && enabled, glass = glass)
}
