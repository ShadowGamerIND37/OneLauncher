package org.tg.tgoneconfig.internal.mixin.compat.toggle;

import org.tg.tgoneconfig.api.toggle.v1.ModToggle;
import org.tg.tgoneconfig.api.ui.v1.ModToggles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "org.codeberg.chromatic.vignettetweaks.config.VignetteConfig$Air", remap = false)
public class Mixin_Toggle_VignetteAir {
    @Unique
    private static final ModToggle tgoneconfig$toggle = ModToggles.handle("vignettetweaks");

    @Inject(method = "evaluate", at = @At("HEAD"), cancellable = true, require = 0)
    private static void tgoneconfig$noTint(CallbackInfoReturnable<Object> cir) {
        if (!tgoneconfig$toggle.isEnabled()) cir.setReturnValue(null);
    }
}
