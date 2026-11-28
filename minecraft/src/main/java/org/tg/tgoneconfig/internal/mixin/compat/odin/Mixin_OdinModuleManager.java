package org.tg.tgoneconfig.internal.mixin.compat.odin;

//? odin_compat {
/*import com.odtheking.odin.features.ModuleManager;
import org.tg.tgoneconfig.internal.compat.OdinCompat;
import org.tg.tgoneconfig.internal.ui.hud.CompatOverlayRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(ModuleManager.class)
public class Mixin_OdinModuleManager {

    @Inject(method = "registerModules", at = @At("TAIL"), require = 0)
    private void tgoneconfig$registerOdinTree(CallbackInfo ci) {
        OdinCompat.registerTree();
    }

    @Inject(method = "render", at = @At("HEAD"), require = 0, cancellable = true)
    private void tgoneconfig$registerOdinHuds(CallbackInfo ci) {
        OdinCompat.ensureRegistered();
        if (CompatOverlayRenderer.oneConfigScreenOpen()) {
            ci.cancel();
        }
    }
}
*///? }
