package org.tg.tgoneconfig.internal.mixin.compat.toggle;

import org.tg.tgoneconfig.api.toggle.v1.ModToggle;
import org.tg.tgoneconfig.api.ui.v1.ModToggles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(
    targets = {"snownee.jade.impl.config.WailaConfig$General", "snownee.jade.impl.config.WailaConfig$ConfigGeneral"},
    remap = false
)
public class Mixin_Toggle_Jade {
    @Unique
    private static final ModToggle tgoneconfig$toggle = ModToggles.handle("jade");

    @Inject(method = {"shouldDisplayTooltip"}, at = @At("HEAD"), cancellable = true, require = 0)
    private void tgoneconfig$force1(CallbackInfoReturnable<Boolean> cir) {
        if (!tgoneconfig$toggle.isEnabled()) cir.setReturnValue(false);
    }
}
