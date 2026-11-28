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
@Mixin(targets = "dev.lambdaurora.lambdynlights.DynamicLightsConfig", remap = false)
public class Mixin_Toggle_LambDynamicLights {
    @Unique
    private static final ModToggle tgoneconfig$toggle = ModToggles.handle("lambdynlights");

    @Inject(method = "getDynamicLightsMode", at = @At("HEAD"), cancellable = true, require = 0)
    private void tgoneconfig$off(CallbackInfoReturnable<Object> cir) {
        if (tgoneconfig$toggle.isEnabled()) return;
        Object off = ModGates.enumConstant("dev.lambdaurora.lambdynlights.DynamicLightsMode", "OFF");
        if (off != null) cir.setReturnValue(off);
    }
}
