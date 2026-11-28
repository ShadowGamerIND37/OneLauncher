package org.tg.tgoneconfig.internal.mixin.compat.toggle;

import com.llamalad7.mixinextras.sugar.Local;
import org.tg.tgoneconfig.api.toggle.v1.ModToggle;
import org.tg.tgoneconfig.api.ui.v1.ModToggles;
import org.tg.tgoneconfig.internal.compat.toggle.ModGates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "dev.isxander.zoomify.Zoomify", remap = false)
public class Mixin_Toggle_Zoomify {
    @Unique
    private static final ModToggle tgoneconfig$toggle = ModToggles.handle("zoomify");

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true, require = 0)
    private void tgoneconfig$stopZooming(CallbackInfo ci) {
        if (tgoneconfig$toggle.isEnabled()) return;
        ModGates.resetZoomify();
        ci.cancel();
    }

    @Inject(method = "getZoomDivisor", at = @At("HEAD"), cancellable = true, require = 0)
    private static void tgoneconfig$noZoom(CallbackInfoReturnable<Float> cir) {
        if (!tgoneconfig$toggle.isEnabled()) cir.setReturnValue(1.0F);
    }

    @Inject(method = "shouldRenderOverlay", at = @At("HEAD"), cancellable = true, require = 0)
    private static void tgoneconfig$vanillaSpyglass(CallbackInfoReturnable<Boolean> cir, @Local(argsOnly = true) boolean scoping) {
        if (!tgoneconfig$toggle.isEnabled()) cir.setReturnValue(scoping);
    }
}
