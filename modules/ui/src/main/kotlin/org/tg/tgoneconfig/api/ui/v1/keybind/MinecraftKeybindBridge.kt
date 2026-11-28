package org.tg.tgoneconfig.api.ui.v1.keybind

/**
 * Platform bridge that mirrors [TGOneConfigKeybind]s into Minecraft's native Controls menu
 */
public interface MinecraftKeybindBridge {
    /**
     * Register or refresh the vanilla mapping mirroring [bind]
     *
     * Called when a keybind is registered with the manager
     */
    public fun register(bind: TGOneConfigKeybind)

    /** Remove the vanilla mapping mirroring [bind] if any */
    public fun unregister(bind: TGOneConfigKeybind)

    /**
     * Push [bind]'s current keys/modifiers onto its vanilla mapping
     *
     * Called when TGOneConfig changes a keybind (such as a rebind in the TGOneConfig settings UI) so the
     * Controls menu reflects the new value
     */
    public fun sync(bind: TGOneConfigKeybind)
}
