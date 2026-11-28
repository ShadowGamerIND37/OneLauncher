package org.tg.tgoneconfig.api.ui.v1.internal

import org.polyfrost.compose.render.PolyBitmap
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.InternalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.components.PlayerSkinLayers
import org.tg.tgoneconfig.api.ui.v1.components.PlayerSkinLayersResult
import java.util.UUID

@ExperimentalTGOneConfigUiApi
@InternalTGOneConfigUiApi
@Suppress("DEPRECATION")
public interface TGOneConfigUIPlayerHeadBridge {
    public val revision: Int

    public fun head(id: UUID?): PolyBitmap?

    public fun defaultHead(id: UUID): PolyBitmap?

    public fun localPlayerId(): UUID?

    public fun localPlayerName(): String?

    public fun skinTexture(id: UUID): String?

    public fun skinLayers(id: UUID): PlayerSkinLayers?

    public fun skinLayers(texture: String): PlayerSkinLayersResult
}

@ExperimentalTGOneConfigUiApi
private val loadedHeadBridge: TGOneConfigUIPlayerHeadBridge? by lazy {
    loadBridge(TGOneConfigUIPlayerHeadBridge::class.java, "player heads fall back to a placeholder")
}

@ExperimentalTGOneConfigUiApi
internal fun playerHeadBridge(): TGOneConfigUIPlayerHeadBridge? = loadedHeadBridge
