package org.tg.tgoneconfig.internal.mixin.compat.toggle;

import org.tg.tgoneconfig.internal.compat.toggle.ModGates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "dev.terminalmc.effecttimerplus.config.Config", remap = false)
public class Mixin_Toggle_EffectTimerPlusSave {

    @Inject(method = "save", at = @At("HEAD"), cancellable = true, require = 0)
    private static void tgoneconfig$keepMaskOffDisk(CallbackInfo ci) {
        if (ModGates.blocksSave("effecttimerplus")) ci.cancel();
    }
}
