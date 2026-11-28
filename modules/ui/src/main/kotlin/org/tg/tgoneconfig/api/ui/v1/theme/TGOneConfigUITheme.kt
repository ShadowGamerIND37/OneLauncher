package org.tg.tgoneconfig.api.ui.v1.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontFamily
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.InternalTGOneConfigUiApi

@ExperimentalTGOneConfigUiApi
public abstract class TGOneConfigUITheme @InternalTGOneConfigUiApi constructor() {
    public abstract val name: String

    public abstract val pageBackground: Color
    public abstract val sidebarBackground: Color
    public abstract val chipBackground: Color
    public abstract val modCardBackground: Color
    public abstract val componentBackground: Color
    public abstract val popupBackground: Color

    public abstract val borderColor: Color
    public abstract val textColor: Color
    public abstract val textColorSecondary: Color
    public abstract val accentTextColor: Color

    public abstract val shadowColor: Color
    public abstract val controlThumbColor: Color
    public abstract val favoriteColor: Color

    public abstract val shadowEnabled: Boolean

    public abstract val backgroundShape: Shape
    public abstract val sideBarNavigationEntryShape: Shape
    public abstract val modCardShape: Shape
    public abstract val checkBoxShape: Shape
    public abstract val buttonShape: Shape
    public abstract val popupShape: Shape
    public abstract val circleShape: Shape

    public abstract val fontFamily: FontFamily
    public abstract val logoPath: String

    public open val style: ThemeStyle get() = ThemeStyle.Flat

    public open val isLight: Boolean get() = textColor.luminance() < 0.5f

    public open val translucentSelection: Boolean get() = false

    public val pageSurface: Color get() = pageBackground.compositeOver(if (isLight) Color.White else Color.Black)

    public open val error: Color get() = Color(0xFFE35B5B).legibleOn(pageSurface)

    public open val warning: Color get() = Color(0xFFFFAB1D).legibleOn(pageSurface)

    public open val success: Color get() = Color(0xFF3CAF77).legibleOn(pageSurface)

    public open val info: Color get() = Color(0xFF5B9BE3).legibleOn(pageSurface)

    public open val onError: Color get() = readableOn(error)

    public open val onSuccess: Color get() = readableOn(success)

    public open val mutedText: Color
        get() = if (textColorSecondary.contrastAgainst(pageSurface) >= 4.5f) textColorSecondary else textColor

    public open val scrimColor: Color get() = Color.Black.copy(alpha = if (isLight) 0.35f else 0.5f)

    public open val focusRingColor: Color get() = textColor

    public open val disabledAlpha: Float get() = 0.5f

    public open val hoverOverlayAlpha: Float get() = if (isLight) 0.06f else 0.08f

    public open val pressedOverlayAlpha: Float get() = if (isLight) 0.12f else 0.14f

    public open val controlTrackColor: Color get() = Color(0xFF74777F)

    public open val accentBrush: Brush get() = SolidColor(accentTextColor)

    public open val onMedia: Color get() = Color.White

    public open val onMediaShadow: Color get() = Color.Black.copy(alpha = 0.6f)

    public open val checkerLight: Color get() = if (isLight) Color(0xFFFFFFFF) else Color(0xFF9A9A9A)

    public open val checkerDark: Color get() = if (isLight) Color(0xFFD0D0D0) else Color(0xFF6A6A6A)

    public open val shapes: TGOneConfigShapes
        get() = TGOneConfigShapes(
            small = checkBoxShape,
            medium = buttonShape,
            large = modCardShape,
            field = sideBarNavigationEntryShape,
            pill = if (style == ThemeStyle.Pixel) RectangleShape else RoundedCornerShape(percent = 50),
            circle = circleShape,
        )

    public open val typography: TGOneConfigTypography get() = TGOneConfigTypography(fontFamily)

    public open val spacing: TGOneConfigSpacing get() = TGOneConfigSpacing.Default

    private fun readableOn(fill: Color): Color =
        if (Color.White.contrastAgainst(fill) >= Color.Black.contrastAgainst(fill)) Color.White else Color.Black
}
