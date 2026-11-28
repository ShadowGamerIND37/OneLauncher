package org.tg.tgoneconfig.internal.mixin.compat.toggle;

import org.tg.tgoneconfig.api.ui.v1.ModToggles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "io.github.sjouwer.gammautils.GammaManager", remap = false)
public class Mixin_Toggle_GammaUtilsGammaManager {

    @Inject(method = {"setGamma(DZZZ)V"}, at = @At("HEAD"), cancellable = true, require = 0)
    private static void tgoneconfig$skip1(CallbackInfo ci) {
        if (!ModToggles.isEnabled("gammautils")) ci.cancel();
    }
}
