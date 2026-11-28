package org.tg.tgoneconfig.internal.mixin.compat.toggle;

//? if >= 1.21.1 {
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.tg.tgoneconfig.api.toggle.v1.ModToggle;
import org.tg.tgoneconfig.api.ui.v1.ModToggles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SkullBlockEntity.class)
public class Mixin_Toggle_SkinLayersSkulls {
    @Unique
    private static final ModToggle tgoneconfig$toggle = ModToggles.handle("skinlayers3d");

    @Inject(method = "getHeadLayers", at = @At("HEAD"), cancellable = true, require = 0, expect = 0, remap = false)
    private void tgoneconfig$noMesh(CallbackInfoReturnable<Object> cir) {
        if (!tgoneconfig$toggle.isEnabled()) cir.setReturnValue(null);
    }

    @Inject(method = "setInitialized", at = @At("HEAD"), cancellable = true, require = 0, expect = 0, remap = false)
    private void tgoneconfig$retryLater(boolean initialized, CallbackInfo ci) {
        if (initialized && !tgoneconfig$toggle.isEnabled()) ci.cancel();
    }
}
//?}
