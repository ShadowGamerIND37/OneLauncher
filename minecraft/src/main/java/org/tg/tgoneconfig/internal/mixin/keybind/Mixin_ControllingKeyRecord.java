package org.tg.tgoneconfig.internal.mixin.keybind;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

//? if >= 1.21.10 {
import net.minecraft.client.Options;
import net.minecraft.client.input.KeyEvent;
import org.tg.tgoneconfig.internal.ui.keybind.TGOneConfigKeybindRecorder;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//?}

@Pseudo
@Mixin(targets = "com.blamejared.controlling.platform.IPlatformHelper")
public interface Mixin_ControllingKeyRecord {
    //? if >=1.21.10 {
    @Inject(method = "handleKeyPress", at = @At("HEAD"), cancellable = true, remap = false)
    private void tgoneconfig$recordKeys(@Coerce Object screen, Options options, KeyEvent event, CallbackInfo ci) {
        if (!(screen instanceof TGOneConfigKeybindRecorder rec) || !rec.tgoneconfig$isOurs()) return;
        if (event.isEscape()) rec.tgoneconfig$recordEscape();
        else rec.tgoneconfig$recordKey(event.key());
        ci.cancel();
    }
    //?}
}
