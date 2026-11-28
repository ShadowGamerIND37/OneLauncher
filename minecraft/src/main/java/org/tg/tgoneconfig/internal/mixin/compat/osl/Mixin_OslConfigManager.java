package org.tg.tgoneconfig.internal.mixin.compat.osl;

//? osl_config_compat {
/*import net.ornithemc.osl.config.api.ConfigManager;
import net.ornithemc.osl.config.api.config.Config;
import org.tg.tgoneconfig.internal.compat.OslConfigCompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = ConfigManager.class, remap = false)
public class Mixin_OslConfigManager {

    @Inject(method = "register", at = @At("HEAD"), remap = false, require = 0)
    private static void tgoneconfig$register(Config config, CallbackInfo ci) {
        try {
            OslConfigCompat.addConfig(config);
        } catch (Throwable ignored) {
        }
    }
}
*///? }
