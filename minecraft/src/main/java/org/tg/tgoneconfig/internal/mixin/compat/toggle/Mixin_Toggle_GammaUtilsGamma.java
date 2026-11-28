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
@Mixin(targets = "io.github.sjouwer.gammautils.config.ModConfig$GammaSettings", remap = false)
public class Mixin_Toggle_GammaUtilsGamma {
    @Unique
    private static final ModToggle tgoneconfig$toggle = ModToggles.handle("gammautils");

    @Inject(method = {"isDynamicEnabled", "isStatusEffectEnabled"}, at = @At("HEAD"), cancellable = true, require = 0)
    private void tgoneconfig$force1(CallbackInfoReturnable<Boolean> cir) {
        if (!tgoneconfig$toggle.isEnabled()) cir.setReturnValue(false);
    }

    @Inject(method = "getValue", at = @At("RETURN"), cancellable = true, require = 0)
    private void tgoneconfig$vanillaRange(CallbackInfoReturnable<Double> cir) {
        if (tgoneconfig$toggle.isEnabled()) return;
        cir.setReturnValue(Math.max(0.0, Math.min(1.0, cir.getReturnValue())));
    }
}
