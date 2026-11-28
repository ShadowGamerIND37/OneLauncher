package org.tg.tgoneconfig.internal.ui.api

object ModHidden : ModIdStore("hidden-mods") {
    fun isHidden(id: String): Boolean = id in this
}
