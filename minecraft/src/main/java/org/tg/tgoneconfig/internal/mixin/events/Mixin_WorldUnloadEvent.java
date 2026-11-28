/*
 * This file is part of TGOneConfig.
 * TGOneConfig - Next Generation Config Library for Minecraft: Java Edition
 * Copyright (C) 2021~2024 TG.
 *   <https://github.com/ShadowGamerIND37> <https://github.com/TG/>
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 *   TGOneConfig is licensed under the terms of version 3 of the GNU Lesser
 * General Public License as published by the Free Software Foundation, AND
 * under the Additional Terms Applicable to TGOneConfig, as published by TG,
 * either version 1.0 of the Additional Terms, or (at your option) any later
 * version.
 *
 *   This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 * License.  If not, see <https://www.gnu.org/licenses/>. You should
 * have also received a copy of the Additional Terms Applicable
 * to TGOneConfig, as published by TG. If not, see
 * <https://github.com/ShadowGamerIND37/legal/tgoneconfig/additional-terms>
 */

package org.tg.tgoneconfig.internal.mixin.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.tg.tgoneconfig.api.event.v1.EventManager;
import org.tg.tgoneconfig.api.event.v1.events.WorldEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class Mixin_WorldUnloadEvent {

    @Shadow public ClientLevel level;

    @Inject(
            //~ if = 1.8.9 'setLevel' -> 'setWorld(Lnet/minecraft/client/multiplayer/ClientLevel;Ljava/lang/String;)V'
            method = "setLevel"
            , at = @At("HEAD"))
    private void onWorldUnloadCallback(CallbackInfo ci) {
        tgoneconfig$postWorldUnload();
    }

    //? if > 1.8.9 {
    //? if >= 1.21.11 {
    @Inject(
            method = "disconnect(Lnet/minecraft/client/gui/screens/Screen;ZZ)V"
            , at = @At("HEAD"))
    //?} else {
    /*@Inject(
            method = "disconnect(Lnet/minecraft/client/gui/screens/Screen;Z)V"
            , at = @At("HEAD"))
    *///?}
    private void onDisconnectCallback(CallbackInfo ci) {
        tgoneconfig$postWorldUnload();
    }

    @Inject(
            method = "clearClientLevel"
            , at = @At("HEAD"))
    private void onClearClientLevelCallback(CallbackInfo ci) {
        tgoneconfig$postWorldUnload();
    }
    //?}

    @Unique
    private void tgoneconfig$postWorldUnload() {
        if (this.level != null) {
            EventManager.INSTANCE.post(new WorldEvent.Unload(this.level));
        }
    }

}
