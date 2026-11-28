package org.tg.tgoneconfig.internal.mixin.compat.toggle;

import org.tg.tgoneconfig.api.ui.v1.ModToggles;
import org.tg.tgoneconfig.internal.compat.toggle.ModGates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "org.codeberg.awruff.tipper.Tipper", remap = false)
public class Mixin_Toggle_Tipper {

    // the chat filter is the one part of the mod that ignores its own switch
    @Inject(method = "getChatFilter", at = @At("HEAD"), cancellable = true, require = 0)
    private static void tgoneconfig$noFilter(CallbackInfoReturnable<Object> cir) {
        if (ModGates.tipperDisabled()) cir.setReturnValue(null);
    }
}
