package org.tg.tgoneconfig.internal.mixin.keybind;

import net.minecraft.client.Options;
import org.tg.tgoneconfig.internal.ui.keybind.MinecraftKeybindProfiles;
import org.tg.tgoneconfig.internal.ui.keybind.VanillaKeyMappingProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Options.class)
public class Mixin_OptionsSaveDetect {
    @Inject(method = "save", at = @At("RETURN"))
    private void tgoneconfig$captureSavedControls(CallbackInfo ci) {
        MinecraftKeybindProfiles.onOptionsSaved();
        VanillaKeyMappingProperties.onOptionsSaved();
    }
}
