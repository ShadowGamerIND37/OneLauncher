package org.tg.tgoneconfig.api.ui.v1.page

import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi

@ExperimentalTGOneConfigUiApi
public class ModConfigTarget internal constructor(public val modId: String, public val category: String?) {
    override fun equals(other: Any?): Boolean =
        this === other || (other is ModConfigTarget && other.modId == modId && other.category == category)

    override fun hashCode(): Int = 31 * modId.hashCode() + (category?.hashCode() ?: 0)

    override fun toString(): String = "ModConfigTarget(modId=$modId, category=$category)"
}
