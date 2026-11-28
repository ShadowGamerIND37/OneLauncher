package org.tg.tgoneconfig.api.ui.v1

@Deprecated(
    "Moved to the mod-toggles module",
    ReplaceWith("ModToggle", "org.tg.tgoneconfig.api.toggle.v1.ModToggle"),
)
public interface ModToggle : org.tg.tgoneconfig.api.toggle.v1.ModToggle {
    override fun needsSetup(): Boolean = false
}
