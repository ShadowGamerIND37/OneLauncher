package org.tg.tgoneconfig.internal.ui.api

object ModFavorites : ModIdStore("favorite-mods") {
    fun isFavorite(id: String): Boolean = id in this
}
