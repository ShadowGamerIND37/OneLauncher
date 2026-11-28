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
@Mixin(targets = "tektonikal.customblockhighlight.Renderer", remap = false)
public class Mixin_Toggle_BlockHighlightLegacy {
    @Unique
    private static final ModToggle tgoneconfig$toggle = ModToggles.handle("custom-block-highlight");

    @Inject(
        method = "mainLoop(Lnet/fabricmc/fabric/api/client/rendering/v1/level/LevelRenderContext;)V",
        at = @At("HEAD"), cancellable = true, require = 0
    )
    private static void tgoneconfig$skipRender(CallbackInfo ci) {
        if (!tgoneconfig$toggle.isEnabled()) ci.cancel();
    }

    @Inject(
        method = "mainLoop(Lnet/fabricmc/fabric/api/client/rendering/v1/WorldRenderContext;Lnet/minecraft/class_239;)Z",
        at = @At("HEAD"), cancellable = true, require = 0
    )
    private static void tgoneconfig$vanillaOutline(CallbackInfoReturnable<Boolean> cir) {
        if (!tgoneconfig$toggle.isEnabled()) cir.setReturnValue(true);
    }
}
