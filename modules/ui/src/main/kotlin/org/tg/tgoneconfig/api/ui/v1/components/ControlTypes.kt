package org.tg.tgoneconfig.api.ui.v1.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.theme.TGOneConfigShapes
import org.tg.tgoneconfig.api.ui.v1.theme.TGOneConfigTheme

@ExperimentalTGOneConfigUiApi
public enum class ControlSize(
    public val height: Dp,
) {
    Compact(28.dp),

    Regular(32.dp),

    Large(38.dp),

    ExtraLarge(45.dp),
}

@ExperimentalTGOneConfigUiApi
public enum class ControlStyle {
    Primary,

    Secondary,

    Ghost,

    Danger,

    DangerFilled,

    Tinted,

    Glass,

    Footer,
}

@ExperimentalTGOneConfigUiApi
public enum class ShapeToken {
    Small,

    Medium,

    Large,

    Field,

    Pill,

    Circle,
    ;

    public fun resolve(shapes: TGOneConfigShapes): Shape = when (this) {
        Small -> shapes.small
        Medium -> shapes.medium
        Large -> shapes.large
        Field -> shapes.field
        Pill -> shapes.pill
        Circle -> shapes.circle
    }

    public val shape: Shape
        @Composable get() = resolve(TGOneConfigTheme.current.shapes)
}

@ExperimentalTGOneConfigUiApi
@Immutable
public class ControlMetrics(
    public val height: Dp,
    public val horizontalPadding: Dp,
    public val fontSize: TextUnit,
    public val iconSize: Dp,
    public val shape: ShapeToken,
)

@ExperimentalTGOneConfigUiApi
public object ControlDefaults {
    public val size: ControlSize = ControlSize.Regular

    public val disabledAlpha: Float
        @Composable get() = TGOneConfigTheme.current.disabledAlpha

    private val compact = ControlMetrics(ControlSize.Compact.height, 10.dp, 13.sp, 14.dp, ShapeToken.Medium)
    private val regular = ControlMetrics(ControlSize.Regular.height, 16.dp, 14.sp, 16.dp, ShapeToken.Medium)
    private val large = ControlMetrics(ControlSize.Large.height, 16.dp, 14.sp, 16.dp, ShapeToken.Medium)
    private val extraLarge = ControlMetrics(ControlSize.ExtraLarge.height, 18.dp, 16.sp, 20.dp, ShapeToken.Medium)

    public fun metrics(size: ControlSize): ControlMetrics = when (size) {
        ControlSize.Compact -> compact
        ControlSize.Regular -> regular
        ControlSize.Large -> large
        ControlSize.ExtraLarge -> extraLarge
    }

    public val Framed: ControlIndication = ControlIndication(hoverAlpha = 0f, hoverBorder = true)

    public val Bare: ControlIndication = ControlIndication()

    public val Item: ControlIndication = ControlIndication(hoverAlpha = 0.10f, pressedAlpha = 0.16f, layer = OverlayLayer.Behind)

    public val Media: ControlIndication = ControlIndication(tone = OverlayTone.Darken)

    public val PressOnly: ControlIndication = ControlIndication(hoverAlpha = 0f)

    public val Underlay: ControlIndication = ControlIndication(layer = OverlayLayer.Behind)
}
