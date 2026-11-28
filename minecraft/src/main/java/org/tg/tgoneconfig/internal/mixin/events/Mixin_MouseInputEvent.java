package org.tg.tgoneconfig.internal.mixin.events;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import org.tg.tgoneconfig.api.event.v1.EventManager;
import org.tg.tgoneconfig.api.event.v1.events.MouseInputEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >= 1.21.10 {
import net.minecraft.client.input.MouseButtonInfo;
//?}

//? if > 1.8.9 {
import net.minecraft.client.MouseHandler;
//?}

//? if = 1.8.9 {
/*import org.lwjgl.input.Mouse;
import org.tg.compose.minecraft.legacy.LegacyInputDrain;
import org.tg.tgoneconfig.internal.legacy.LegacyInputEvents;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
*///?}

//? if > 1.8.9 {
@Mixin(MouseHandler.class)
//?} else
//@Mixin(value = Mouse.class, remap = false)
public class Mixin_MouseInputEvent {
    //? if >= 1.21.10 {
    @Inject(method = "onButton", at = @At("HEAD"))
    private void mouseCallback(long window, MouseButtonInfo buttonInfo, int action, CallbackInfo ci) {
        EventManager.INSTANCE.post(new MouseInputEvent(buttonInfo.button(), tgoneconfig$state(action)));
    }

    @Inject(method = "onMove", at = @At("HEAD"))
    //~ if sdl 'double y,' -> 'double y, double xrel, double yrel,'
    private void mouseMoveCallback(long handle, double x, double y, double xrel, double yrel, CallbackInfo ci) {
        //? if >= 26.2 {
        if (Minecraft.getInstance().gui.screen() != null) {
        //?} else {
        /*if (Minecraft.getInstance().screen != null) {
        *///?}
            MouseInputEvent.Moved.post((float) x, (float) y);
        }
    }

    //?} elif > 1.8.9 {
    /*@Inject(method = "onPress", at = @At("HEAD"))
    private void mouseCallback(long handle, int button, int action, int mods, CallbackInfo ci) {
        EventManager.INSTANCE.post(new MouseInputEvent(button, tgoneconfig$state(action)));
    }

    @Inject(method = "onMove", at = @At("HEAD"))
    private void mouseMoveCallback(long handle, double x, double y, CallbackInfo ci) {
        if (Minecraft.getInstance().screen != null) {
            MouseInputEvent.Moved.post((float) x, (float) y);
        }
    }
    *///?} else {
    /*@Inject(method = "next", at = @At("RETURN"), remap = false, cancellable = true)
    private static void mouseCallback(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && !LegacyInputDrain.active && LegacyInputEvents.postMouse()) cir.setReturnValue(Mouse.next());
    }
    *///? }

    @Unique
    private static int tgoneconfig$state(int action) {
        if (action == InputConstants.PRESS) return MouseInputEvent.PRESSED;
        if (action == InputConstants.RELEASE) return MouseInputEvent.RELEASED;
        return action;
    }
}
