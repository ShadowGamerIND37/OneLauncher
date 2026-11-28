package org.tg.tgoneconfig.internal.mixin.fixes;

//? if >= 26.3 {
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.platform.MacosUtil;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.nbt.CompoundTag;
import org.tg.tgoneconfig.internal.utils.LegacyVideoModeFormat;
import org.tg.tgoneconfig.internal.utils.MacExclusiveFullscreenReset;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(Options.class)
public class Mixin_LegacyExclusiveFullscreen {
    @Shadow public String fullscreenVideoModeString;
    @Shadow @Final private OptionInstance<Boolean> exclusiveFullscreen;
    @Shadow @Final private File optionsFile;

    @Unique private boolean tgoneconfig$exclusiveSaved;

    @ModifyExpressionValue(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options;dataFix(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;"))
    private CompoundTag tgoneconfig$readExclusiveSaved(CompoundTag options) {
        tgoneconfig$exclusiveSaved = options.contains("exclusiveFullscreen");
        return options;
    }

    @Inject(method = "load", at = @At("RETURN"))
    private void tgoneconfig$exclusiveForLegacyMode(CallbackInfo ci) {
        if (MacosUtil.IS_MACOS) tgoneconfig$undoMacExclusive();
        else if (!tgoneconfig$exclusiveSaved && LegacyVideoModeFormat.isLegacy(fullscreenVideoModeString)) exclusiveFullscreen.set(true);
    }

    @Unique
    private void tgoneconfig$undoMacExclusive() {
        if (!MacExclusiveFullscreenReset.pending(optionsFile)) return;
        exclusiveFullscreen.set(false);
        MacExclusiveFullscreenReset.reset(optionsFile);
    }
}
//?}
