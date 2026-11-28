package org.tg.tgoneconfig.api.ui.v1.internal

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.window.PopupPositionProvider
import net.kyori.adventure.text.Component
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.InternalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.components.ColorPickerModel
import org.tg.tgoneconfig.api.ui.v1.components.UiSound
import org.tg.tgoneconfig.api.ui.v1.theme.TGOneConfigUITheme
import org.tg.tgoneconfig.api.ui.v1.theme.ThemeBuilder

@ExperimentalTGOneConfigUiApi
@InternalTGOneConfigUiApi
public interface TGOneConfigUIComponentsBridge {
    @Composable
    public fun currentTheme(): TGOneConfigUITheme

    @Composable
    public fun accent(): Color

    @Composable
    public fun ProvideTheme(theme: TGOneConfigUITheme?, scaleToUi: Boolean, content: @Composable () -> Unit)

    @Composable
    public fun themeShape(radius: Dp): Shape

    @Composable
    public fun themeIcon(name: String): String

    public fun customTheme(base: TGOneConfigUITheme, id: String, name: String, builder: ThemeBuilder): TGOneConfigUITheme

    public fun playSound(sound: UiSound)

    public fun keepSearchFocus(modifier: Modifier): Modifier

    public fun polyfrostedDark(): TGOneConfigUITheme

    public fun polyfrostedLight(): TGOneConfigUITheme

    public fun glassDark(): TGOneConfigUITheme

    public fun glassLight(): TGOneConfigUITheme

    public fun minecraftDark(): TGOneConfigUITheme

    public fun minecraftLight(): TGOneConfigUITheme

    public fun activeTheme(): TGOneConfigUITheme?

    public fun activateTheme(theme: TGOneConfigUITheme)

    public fun ensureThemeLoaded()

    @Composable
    public fun concentric(shape: Shape, inset: Dp, minRadius: Dp): Shape

    @Composable
    public fun Icon(iconName: String, color: Color, modifier: Modifier, fitAspectRatio: Boolean, tint: (() -> Color)?)

    @Composable
    public fun Text(
        text: String,
        modifier: Modifier,
        color: Color,
        style: TextStyle,
        maxLines: Int,
        overflow: TextOverflow,
        softWrap: Boolean,
        textAlign: TextAlign?,
        colorProducer: ColorProducer?,
        onTextLayout: ((TextLayoutResult) -> Unit)?,
    )

    @Composable
    public fun Text(
        text: AnnotatedString,
        modifier: Modifier,
        color: Color,
        style: TextStyle,
        maxLines: Int,
        overflow: TextOverflow,
        softWrap: Boolean,
        textAlign: TextAlign?,
        colorProducer: ColorProducer?,
        onTextLayout: ((TextLayoutResult) -> Unit)?,
    )

    @Composable
    public fun Text(
        text: Component,
        modifier: Modifier,
        color: Color,
        style: TextStyle,
        maxLines: Int,
        overflow: TextOverflow,
        softWrap: Boolean,
        textAlign: TextAlign?,
        colorProducer: ColorProducer?,
        onTextLayout: ((TextLayoutResult) -> Unit)?,
    )

    @Composable
    public fun Chip(label: String, selected: Boolean, icon: String?, onClick: () -> Unit)

    @Composable
    public fun IconButton(
        iconName: String,
        modifier: Modifier,
        foreground: Color,
        hoveredForeground: Color,
        enabled: Boolean,
        hoverBorder: Boolean,
        onClick: () -> Unit,
    )

    @Composable
    public fun Tooltip(
        text: @Composable () -> Unit,
        modifier: Modifier,
        anchor: Alignment,
        alignment: Alignment,
        offset: DpOffset,
        content: @Composable () -> Unit,
    )

    public fun onClick(modifier: Modifier, interactionSource: MutableInteractionSource, enabled: Boolean, onClick: () -> Unit): Modifier

    public fun reducedMotion(): Boolean

    @Composable
    public fun popupSurface(modifier: Modifier, shape: Shape): Modifier

    @Composable
    public fun popupSurface(modifier: Modifier, shape: Shape, blur: Boolean, glass: Boolean): Modifier

    @Composable
    public fun Popup(
        positionProvider: PopupPositionProvider,
        onDismissRequest: (() -> Unit)?,
        focusable: Boolean,
        content: @Composable () -> Unit,
    )

    @Composable
    public fun fadingEdges(modifier: Modifier, scrollState: ScrollState, color: Color, length: Dp): Modifier

    public fun createColorPicker(initialColor: Color, hasAlpha: Boolean, chroma: Boolean, chromaSpeed: Float, gradient: Color?): ColorPickerModel

    public fun updateColorPicker(model: ColorPickerModel, color: Color, chroma: Boolean, chromaSpeed: Float, gradient: Color?)

    @Composable
    public fun ColorPickerPanel(
        model: ColorPickerModel,
        onColorChanged: (Color) -> Unit,
        onDismiss: () -> Unit,
        modifier: Modifier,
        chromaCapable: Boolean,
        gradientCapable: Boolean,
    )

    public fun colorToHex(color: Color): String

    @Composable
    public fun optionWidth(): Dp

    @Composable
    public fun pageSearchQuery(): String

    @Composable
    public fun optionWidthOrNull(): Dp?

    @Composable
    public fun pageSearchQueryOrNull(): String?
}

@ExperimentalTGOneConfigUiApi
private val loadedBridge: TGOneConfigUIComponentsBridge? by lazy {
    loadBridge(TGOneConfigUIComponentsBridge::class.java, "TG OneConfig UI components cannot be used")
}

@ExperimentalTGOneConfigUiApi
internal fun componentsBridge(): TGOneConfigUIComponentsBridge =
    loadedBridge ?: error("No TGOneConfigUIComponentsBridge is available; TGOneConfig UI components cannot be used")
