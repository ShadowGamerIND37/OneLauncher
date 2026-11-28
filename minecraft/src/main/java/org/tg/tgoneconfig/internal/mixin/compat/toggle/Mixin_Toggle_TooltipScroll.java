package org.tg.tgoneconfig.internal.mixin.compat.toggle;

import org.tg.tgoneconfig.api.toggle.v1.ModToggle;
import org.tg.tgoneconfig.api.ui.v1.ModToggles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "com.provismet.tooltipscroll.ScrollTracker", remap = false)
public class Mixin_Toggle_TooltipScroll {
    @Unique
    private static final ModToggle tgoneconfig$toggle = ModToggles.handle("tooltipscroll");

    @Inject(method = {"update", "alignToTop"}, at = @At("HEAD"), cancellable = true, require = 0)
    private static void tgoneconfig$skip1(CallbackInfo ci) {
        if (!tgoneconfig$toggle.isEnabled()) ci.cancel();
    }

    @Inject(method = {"getXOffset", "getYOffset"}, at = @At("HEAD"), cancellable = true, require = 0)
    private static void tgoneconfig$noOffset(CallbackInfoReturnable<Integer> cir) {
        if (!tgoneconfig$toggle.isEnabled()) cir.setReturnValue(0);
    }

    @Inject(method = "hasMoved", at = @At("HEAD"), cancellable = true, require = 0)
    private static void tgoneconfig$leaveTooltipInPlace(CallbackInfoReturnable<Boolean> cir) {
        if (!tgoneconfig$toggle.isEnabled()) cir.setReturnValue(true);
    }
}
