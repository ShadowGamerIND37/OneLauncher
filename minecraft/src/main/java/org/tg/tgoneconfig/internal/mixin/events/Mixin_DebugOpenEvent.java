package org.tg.tgoneconfig.internal.mixin.events;

import net.minecraft.client.Minecraft;
import org.tg.tgoneconfig.api.event.v1.EventManager;
import org.tg.tgoneconfig.api.event.v1.events.HudEvent;
import org.tg.tgoneconfig.internal.TGOneConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >= 1.21.10 {
import net.minecraft.client.gui.components.debug.DebugScreenEntryList;
//?}

//? if < 1.21.10 && > 1.8.9 {
/*import net.minecraft.client.gui.components.DebugScreenOverlay;
*///?}

//? if = 1.8.9 {
/*import org.objectweb.asm.Opcodes;
*///?}

//? if < 1.21.10 {
/*//~ if = 1.8.9 'DebugScreenOverlay' -> 'Minecraft'
@Mixin(DebugScreenOverlay.class)
public abstract class Mixin_DebugOpenEvent {
    //? if > 1.8.9 {
    @Inject(method = "toggleOverlay", at = @At("TAIL"))
    //?} else
    //@Inject(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;debugEnabled:Z", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER))
    private void onDebugOpen(CallbackInfo ci) {
        if (TGOneConfig.isDebugScreenVisible()) {
            EventManager.INSTANCE.post(HudEvent.Debug.OPENED);
        } else EventManager.INSTANCE.post(HudEvent.Debug.CLOSED);
    }
}
*///? } else {
@Mixin(DebugScreenEntryList.class)
public abstract class Mixin_DebugOpenEvent {
    //? if < 1.21.11 {
    /*@Inject(method = "toggleF3Visible", at = @At("TAIL"))
    *///? } else {
    @Inject(method = "toggleDebugOverlay", at = @At("TAIL"))
    //? }
    private void onDebugOpen(CallbackInfo ci) {
        if (TGOneConfig.isDebugScreenVisible()) {
            EventManager.INSTANCE.post(HudEvent.Debug.OPENED);
        } else EventManager.INSTANCE.post(HudEvent.Debug.CLOSED);
    }
}
//? }
