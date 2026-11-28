@file:OptIn(ExperimentalTGOneConfigUiApi::class)

package org.tg.tgoneconfig.api.ui.v1

import org.tg.tgoneconfig.api.toggle.v1.ModToggle

@ExperimentalTGOneConfigUiApi
public interface ReadOnlyModToggle : ModToggle {
    public val lockedReason: String?

    override fun setEnabled(enabled: Boolean) {}
}

internal class LambdaReadOnlyModToggle(
    private val enabled: () -> Boolean,
    private val reason: () -> String?,
) : ReadOnlyModToggle {
    override val lockedReason: String? get() = reason()

    override fun isEnabled(): Boolean = enabled()
}
