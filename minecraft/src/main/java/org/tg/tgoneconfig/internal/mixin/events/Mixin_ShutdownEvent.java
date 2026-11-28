package org.tg.tgoneconfig.internal.mixin.events;

import net.minecraft.client.Minecraft;
import org.tg.tgoneconfig.api.event.v1.EventManager;
import org.tg.tgoneconfig.api.event.v1.events.ShutdownEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class Mixin_ShutdownEvent {
    @Shadow
    private volatile boolean running;

    @Inject(method = "stop", at = @At("HEAD"))
    private void shutdownCallback(CallbackInfo ci) {
        if (!running) return;
        EventManager.INSTANCE.post(ShutdownEvent.INSTANCE);
    }

}
