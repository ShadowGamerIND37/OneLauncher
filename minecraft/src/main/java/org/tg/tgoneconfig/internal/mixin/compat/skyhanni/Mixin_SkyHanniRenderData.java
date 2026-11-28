package org.tg.tgoneconfig.internal.mixin.compat.skyhanni;

//? if skyblock {
import org.tg.tgoneconfig.internal.compat.SkyHanniHudCompat;
import org.tg.tgoneconfig.internal.ui.hud.CompatOverlayRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "at.hannibal2.skyhanni.data.RenderData", remap = false)
public class Mixin_SkyHanniRenderData {

    @Inject(method = "postRenderOverlay", at = @At("HEAD"), cancellable = true, require = 0)
    private static void tgoneconfig$hudCompat(CallbackInfo ci) {
        SkyHanniHudCompat.ensureRegistered();
        if (CompatOverlayRenderer.oneConfigScreenOpen() && !SkyHanniHudCompat.isRedrawing()) {
            ci.cancel();
        }
    }
}
//?}
