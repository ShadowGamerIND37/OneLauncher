package org.tg.tgoneconfig.internal.mixin.events;

import net.minecraft.client.Minecraft;
import org.tg.tgoneconfig.api.event.v1.EventManager;
import org.tg.tgoneconfig.api.event.v1.events.InitializationEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if = 1.8.9 {
/*import org.tg.tgoneconfig.internal.legacy.command.ClientCommandInternals;
*///?}

@Mixin(Minecraft.class)
public class Mixin_InitializationEvent {

    //~ if = 1.8.9 '<init>' -> 'init'
    @Inject(method = "<init>", at = @At("RETURN"))
    private void completedInit(CallbackInfo ci) {
        EventManager.INSTANCE.post(InitializationEvent.INSTANCE);
        //? if = 1.8.9
        //ClientCommandInternals.initializeDispatcher();
    }

}
