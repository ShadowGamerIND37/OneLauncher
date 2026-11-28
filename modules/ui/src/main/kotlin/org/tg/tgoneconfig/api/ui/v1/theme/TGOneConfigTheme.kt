@file:OptIn(ExperimentalVersionOverloading::class)

package org.tg.tgoneconfig.api.ui.v1.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.components.ContextMenuRoot
import org.tg.tgoneconfig.api.ui.v1.components.FeedbackHosts
import org.tg.tgoneconfig.api.ui.v1.components.ProvideTGOneConfigTextContextMenu
import org.tg.tgoneconfig.api.ui.v1.internal.componentsBridge
import java.util.concurrent.ConcurrentHashMap

@ExperimentalTGOneConfigUiApi
public object TGOneConfigTheme {
    @JvmStatic
    public val POLYFROSTED_DARK: TGOneConfigUITheme by lazy { componentsBridge().polyfrostedDark() }

    @JvmStatic
    public val POLYFROSTED_LIGHT: TGOneConfigUITheme by lazy { componentsBridge().polyfrostedLight() }

    @JvmStatic
    public val GLASS_DARK: TGOneConfigUITheme by lazy { componentsBridge().glassDark() }

    @JvmStatic
    public val GLASS_LIGHT: TGOneConfigUITheme by lazy { componentsBridge().glassLight() }

    @JvmStatic
    @Deprecated("Renamed to TGed", ReplaceWith("TG OneConfigTheme.POLYFROSTED_DARK"))
    public val POLY_GLASS_DARK: TGOneConfigUITheme get() = POLYFROSTED_DARK

    @JvmStatic
    @Deprecated("Renamed to TGed", ReplaceWith("TG OneConfigTheme.POLYFROSTED_LIGHT"))
    public val POLY_GLASS_LIGHT: TGOneConfigUITheme get() = POLYFROSTED_LIGHT

    @JvmStatic
    public val MINECRAFT_DARK: TGOneConfigUITheme by lazy { componentsBridge().minecraftDark() }

    @JvmStatic
    public val MINECRAFT_LIGHT: TGOneConfigUITheme by lazy { componentsBridge().minecraftLight() }

    public val current: TGOneConfigUITheme
        @Composable get() = componentsBridge().currentTheme()

    public val typography: TGOneConfigTypography
        @Composable get() = current.typography

    public val shapes: TGOneConfigShapes
        @Composable get() = current.shapes

    public val spacing: TGOneConfigSpacing
        @Composable get() = current.spacing

    public val accent: Color
        @Composable get() = componentsBridge().accent()

    @JvmStatic
    public val active: TGOneConfigUITheme?
        get() = componentsBridge().activeTheme()

    @JvmStatic
    public fun builtIn(): List<TGOneConfigUITheme> = listOf(
        POLYFROSTED_DARK, POLYFROSTED_LIGHT,
        GLASS_DARK, GLASS_LIGHT,
        MINECRAFT_DARK, MINECRAFT_LIGHT,
    )

    @JvmStatic
    public fun activate(theme: TGOneConfigUITheme) {
        componentsBridge().activateTheme(theme)
    }

    @JvmStatic
    public fun ensureLoaded() {
        componentsBridge().ensureThemeLoaded()
    }

    @Composable
    public fun shape(radius: Dp): Shape = componentsBridge().themeShape(radius)

    private val pixelIcons = ConcurrentHashMap<String, String>()

    @Composable
    public fun icon(name: String): String {
        val themed = componentsBridge().themeIcon(name)
        if (themed != name || current.style != ThemeStyle.Pixel) return themed
        return pixelIcons[name] ?: name
    }

    @JvmStatic
    public fun registerPixelIcon(icon: String, pixelIcon: String) {
        pixelIcons[icon] = pixelIcon
    }

    @JvmStatic
    public fun registerPixelIcons(icons: Map<String, String>) {
        pixelIcons.putAll(icons)
    }

    internal fun registeredPixelIcon(name: String): String? = pixelIcons[name]

    @JvmStatic
    public fun custom(base: TGOneConfigUITheme, id: String, name: String, block: ThemeBuilder.() -> Unit): TGOneConfigUITheme {
        require(id.isNotBlank()) { "A custom theme needs a non-blank id" }
        return componentsBridge().customTheme(base, id, name, ThemeBuilder().apply(block))
    }

    @JvmStatic
    public var logoOverride: String? by mutableStateOf(null)
        private set

    @JvmStatic
    public fun overrideLogo(path: String?) {
        logoOverride = path?.takeIf { it.isNotBlank() }
    }

    @JvmStatic
    public var brandIconOverride: String? by mutableStateOf(null)
        private set

    @JvmStatic
    public fun overrideBrandIcon(path: String?) {
        brandIconOverride = path?.takeIf { it.isNotBlank() }
    }
}

@ExperimentalTGOneConfigUiApi
@Composable
public fun ProvideTGOneConfigTheme(
    @IntroducedAt("1.8.0") theme: TGOneConfigUITheme? = null,
    @IntroducedAt("1.8.0") scaleToUi: Boolean = false,
    content: @Composable () -> Unit,
) {
    val nested = LocalThemeProvided.current
    if (nested && theme == null && !scaleToUi) {
        content()
        return
    }
    componentsBridge().ProvideTheme(theme, scaleToUi) {
        if (nested) {
            content()
        } else {
            CompositionLocalProvider(LocalThemeProvided provides true) {
                ProvideTGOneConfigTextContextMenu { ContextMenuRoot { FeedbackHosts(content) } }
            }
        }
    }
}

private val LocalThemeProvided = staticCompositionLocalOf { false }
