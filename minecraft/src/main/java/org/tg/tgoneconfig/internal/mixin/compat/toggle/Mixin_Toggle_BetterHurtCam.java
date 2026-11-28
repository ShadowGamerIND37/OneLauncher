package org.tg.tgoneconfig.internal.mixin.compat.toggle;

import org.tg.tgoneconfig.api.toggle.v1.ModToggle;
import org.tg.tgoneconfig.api.ui.v1.ModToggles;
import org.tg.tgoneconfig.internal.compat.toggle.ModGates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "net.uku3lig.betterhurtcam.BetterHurtCam", remap = false)
public class Mixin_Toggle_BetterHurtCam {
    @Unique
    private static final ModToggle tgoneconfig$toggle = ModToggles.handle("betterhurtcam");

    @Inject(method = {"isEnabled", "isHeartBlinkEnabled"}, at = @At("HEAD"), cancellable = true, require = 0)
    private static void tgoneconfig$force1(CallbackInfoReturnable<Boolean> cir) {
        if (!tgoneconfig$toggle.isEnabled()) cir.setReturnValue(true);
    }

    @Inject(method = "getMultiplier", at = @At("HEAD"), cancellable = true, require = 0)
    private static void tgoneconfig$fullTilt(CallbackInfoReturnable<Double> cir) {
        if (!tgoneconfig$toggle.isEnabled()) cir.setReturnValue(1.0);
    }

    @Inject(method = "getType", at = @At("HEAD"), cancellable = true, require = 0)
    private static void tgoneconfig$vanillaTilt(CallbackInfoReturnable<Object> cir) {
        if (tgoneconfig$toggle.isEnabled()) return;
        Object vanilla = ModGates.enumConstant("net.uku3lig.betterhurtcam.HurtCamType", "YAW_BASED");
        if (vanilla != null) cir.setReturnValue(vanilla);
    }
}
