package org.tg.tgoneconfig.api.ui.v1.page

import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi

@ExperimentalTGOneConfigUiApi
public class PageGroup internal constructor(public val id: String, public val title: PageText, public val order: Int) {
    override fun equals(other: Any?): Boolean = this === other || (other is PageGroup && other.id == id)

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String = "PageGroup(id=$id, order=$order)"

    public companion object {
        @JvmField
        public val MODS_AND_OPTIONS: PageGroup =
            PageGroup("tgoneconfig:mods_and_options", PageText.translatable("tgoneconfig.pagegroup.mods_and_options", "Mods & Options"), 100)

        @JvmField
        public val PERSONALIZATION: PageGroup =
            PageGroup("tgoneconfig:personalization", PageText.translatable("tgoneconfig.pagegroup.personalization", "Personalization"), 200)

        @JvmField
        public val ONECONFIG: PageGroup =
            PageGroup("tgoneconfig:tgoneconfig", PageText.translatable("tgoneconfig.pagegroup.tgoneconfig", "TG OneConfig"), 300)

        @JvmStatic
        public fun create(id: String, title: PageText, order: Int): PageGroup {
            TGOneConfigPages.requireNamespacedId(id)
            return PageGroup(id, title, order)
        }
    }
}
