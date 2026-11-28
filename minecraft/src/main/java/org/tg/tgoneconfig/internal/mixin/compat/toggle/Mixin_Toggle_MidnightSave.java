package org.tg.tgoneconfig.internal.mixin.compat.toggle;

import org.tg.tgoneconfig.internal.compat.toggle.ModGates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "eu.midnightdust.lib.config.MidnightConfig", remap = false)
// on its own so that nothing else in here failing to fit can take the save guard down with it
public class Mixin_Toggle_MidnightSave {

    @Inject(method = "write", at = @At("HEAD"), cancellable = true, require = 0)
    private static void tgoneconfig$keepMaskOffDisk(String modid, CallbackInfo ci) {
        if (ModGates.blocksSave(modid)) ci.cancel();
    }
}
