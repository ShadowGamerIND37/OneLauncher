package org.tg.tgoneconfig.internal.mixin.compat.toggle;

import org.tg.tgoneconfig.internal.compat.toggle.ModGates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "com.misterpemodder.shulkerboxtooltip.impl.network.ClientNetworking", remap = false)
public class Mixin_Toggle_ShulkerBoxTooltipJoin {

    // joining a world copies the saved config back over the one in use
    @Inject(method = "onJoinServer", at = @At("TAIL"), require = 0)
    private static void tgoneconfig$maskAgain(CallbackInfo ci) {
        ModGates.remaskIfOverwritten("com.misterpemodder.shulkerboxtooltip.ShulkerBoxTooltip");
    }
}
