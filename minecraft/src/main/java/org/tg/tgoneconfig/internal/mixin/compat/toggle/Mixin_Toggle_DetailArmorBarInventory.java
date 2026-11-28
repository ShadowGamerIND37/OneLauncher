package org.tg.tgoneconfig.internal.mixin.compat.toggle;

import org.tg.tgoneconfig.api.toggle.v1.ModToggle;
import org.tg.tgoneconfig.api.ui.v1.ModToggles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "com.redlimerl.detailab.render.InventoryArmorOverlayRenderer", remap = false)
public class Mixin_Toggle_DetailArmorBarInventory {
    @Unique
    private static final ModToggle tgoneconfig$toggle = ModToggles.handle("detailabreconst");

    @Inject(method = {"renderOverlay"}, at = @At("HEAD"), cancellable = true, require = 0)
    private void tgoneconfig$skip1(CallbackInfo ci) {
        if (!tgoneconfig$toggle.isEnabled()) ci.cancel();
    }
}
