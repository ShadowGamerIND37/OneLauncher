@file:OptIn(ExperimentalTGOneConfigUiApi::class)

package org.tg.tgoneconfig.api.ui.v1.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.a11y.A11yRole
import org.tg.tgoneconfig.api.ui.v1.a11y.Motion
import org.tg.tgoneconfig.api.ui.v1.a11y.a11y
import org.tg.tgoneconfig.api.ui.v1.theme.TGOneConfigTheme

@ExperimentalTGOneConfigUiApi
@Composable
public fun ImageViewer(
    count: Int,
    index: Int,
    onIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onDismiss: (() -> Unit)? = null,
    thumbnail: (@Composable BoxScope.(Int) -> Unit)? = null,
    thumbnailLabel: (Int) -> String = { layoutString("tgoneconfig.ui.image.thumbnail", "Image %s", it + 1) },
    itemKey: ((Int) -> Any)? = null,
    tileModifier: (Int) -> Modifier = { Modifier },
    tileShape: Shape? = null,
    filmstripPreviousLabel: String = layoutString("tgoneconfig.ui.filmstrip.previous", "Earlier items"),
    filmstripNextLabel: String = layoutString("tgoneconfig.ui.filmstrip.next", "Later items"),
    image: @Composable (Int) -> Unit,
) {
    val theme = TGOneConfigTheme.current
    val spacing = theme.spacing
    val focus = remember { FocusRequester() }
    val shown = if (count > 0) index.coerceIn(0, count - 1) else 0
    val latestShown by rememberUpdatedState(shown)
    val latestCount by rememberUpdatedState(count)
    val latestChange by rememberUpdatedState(onIndexChange)
    if (onDismiss != null) EscapeCloses(onDismiss)
    LaunchedEffect(Unit) { runCatching { focus.requestFocus() } }
    val position = layoutString("tgoneconfig.ui.image.position", "%s of %s", shown + 1, count)
    val keyNav = Modifier.onKeyEvent { event ->
        if (event.type != KeyEventType.KeyDown || latestCount <= 0) return@onKeyEvent false
        val target = when (event.key) {
            Key.DirectionLeft -> latestShown - 1
            Key.DirectionRight -> latestShown + 1
            Key.MoveHome -> 0
            Key.MoveEnd -> latestCount - 1
            else -> return@onKeyEvent false
        }
        if (target !in 0 until latestCount || target == latestShown) return@onKeyEvent false
        latestChange(target)
        true
    }
    val focusTarget = Modifier
        .focusRequester(focus)
        .a11y(position, A11yRole.Image, null, null, theme.shapes.large, true)
        .focusable()
    if (thumbnail == null) {
        ImageViewerStage(count, shown, position, onIndexChange, modifier.then(keyNav).then(focusTarget), image)
        return
    }
    Column(modifier.then(keyNav), verticalArrangement = Arrangement.spacedBy(spacing.l)) {
        ImageViewerStage(count, shown, position, onIndexChange, Modifier.weight(1f).fillMaxWidth().then(focusTarget), image)
        Filmstrip(
            count,
            shown,
            onIndexChange,
            thumbnailLabel,
            itemKey = itemKey,
            tileModifier = tileModifier,
            shape = tileShape,
            previousLabel = filmstripPreviousLabel,
            nextLabel = filmstripNextLabel,
            thumbnail = thumbnail,
        )
    }
}

@Composable
private fun ImageViewerStage(
    count: Int,
    shown: Int,
    position: String,
    onIndexChange: (Int) -> Unit,
    modifier: Modifier,
    image: @Composable (Int) -> Unit,
) {
    val spacing = TGOneConfigTheme.current.spacing
    Box(modifier, contentAlignment = Alignment.Center) {
        if (count > 0) {
            Crossfade(shown, Modifier.fillMaxSize(), Motion.tweenSpec(), label = "imageViewer") { at ->
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { image(at) }
            }
        }
        if (shown > 0) {
            IconButton(
                "left-arrow",
                layoutString("tgoneconfig.ui.image.previous", "Previous image"),
                { onIndexChange(shown - 1) },
                Modifier.align(Alignment.CenterStart).padding(spacing.l),
                style = IconButtonStyle.Overlay,
                size = ControlSize.Large,
            )
        }
        if (shown < count - 1) {
            IconButton(
                "right-arrow",
                layoutString("tgoneconfig.ui.image.next", "Next image"),
                { onIndexChange(shown + 1) },
                Modifier.align(Alignment.CenterEnd).padding(spacing.l),
                style = IconButtonStyle.Overlay,
                size = ControlSize.Large,
            )
        }
        if (count > 1) {
            Badge(position, Modifier.align(Alignment.BottomCenter).padding(spacing.l), overImage = true)
        }
    }
}
