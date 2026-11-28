package org.tg.tgoneconfig.internal.mixin.compat.toggle;

import org.tg.tgoneconfig.api.ui.v1.ModToggles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "com.misterpemodder.shulkerboxtooltip.ShulkerBoxTooltipClient", remap = false)
public class Mixin_Toggle_ShulkerBoxTooltip {

    @Inject(method = {"modifyStackTooltip"}, at = @At("HEAD"), cancellable = true, require = 0)
    private static void tgoneconfig$skip1(CallbackInfo ci) {
        if (!ModToggles.isEnabled("shulkerboxtooltip")) ci.cancel();
    }

    @Inject(method = "getProviderIfPreviewAvailable", at = @At("HEAD"), cancellable = true, require = 0)
    private static void tgoneconfig$noPreview(CallbackInfoReturnable<Object> cir) {
        if (!ModToggles.isEnabled("shulkerboxtooltip")) cir.setReturnValue(null);
    }

    @Inject(method = "isPreviewAvailable", at = @At("HEAD"), cancellable = true, require = 0)
    private static void tgoneconfig$previewUnavailable(CallbackInfoReturnable<Boolean> cir) {
        if (!ModToggles.isEnabled("shulkerboxtooltip")) cir.setReturnValue(false);
    }
}
