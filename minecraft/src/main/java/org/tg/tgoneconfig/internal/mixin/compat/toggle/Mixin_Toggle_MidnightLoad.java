package org.tg.tgoneconfig.internal.mixin.compat.toggle;

import org.tg.tgoneconfig.internal.compat.toggle.ModGates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "eu.midnightdust.lib.config.MidnightConfig", remap = false)
public class Mixin_Toggle_MidnightLoad {

    @Inject(method = "loadValuesFromJson", at = @At("TAIL"), require = 0)
    private void tgoneconfig$maskAgain(CallbackInfo ci) {
        ModGates.remask(((Object) this).getClass().getName());
    }
}
