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

import org.tg.tgoneconfig.api.event.v1.EventManager;
import org.tg.tgoneconfig.api.event.v1.events.WindowFocusEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >= 26.1 {
import com.mojang.blaze3d.platform.Window;
//?}

//? if < 26.1 {
/*import net.minecraft.client.Minecraft;
*///?}

//? if = 1.8.9 {
/*import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Shadow;
*///?}

//? if >= 26.1 {
@Mixin(Window.class)
public class Mixin_ModernWindowFocusEvent {
    @Inject(method = "onFocus", at = @At("TAIL"))
    //~ if sdl 'long window, boolean focused' -> 'boolean focused'
    private void onGameFocused(boolean focused, CallbackInfo ci) {
        if (focused) EventManager.INSTANCE.post(WindowFocusEvent.Gained.INSTANCE);
        else EventManager.INSTANCE.post(WindowFocusEvent.Lost.INSTANCE);
    }
}
//?} elif > 1.8.9 {
/*@Mixin(Minecraft.class)
public class Mixin_ModernWindowFocusEvent {
    @Inject(method = "setWindowActive", at = @At("HEAD"))
    private void onGameFocused(boolean focused, CallbackInfo ci) {
        if (focused) EventManager.INSTANCE.post(WindowFocusEvent.Gained.INSTANCE);
        else EventManager.INSTANCE.post(WindowFocusEvent.Lost.INSTANCE);
    }
}
*///?} else {
/*@Mixin(targets = "pl.tomgirl.pylon.window.DisplaySdl", remap = false)
public class Mixin_ModernWindowFocusEvent {
    @Shadow
    private boolean focused;

    @Inject(
        method = "pollEvents",
        at = @At(
            value = "FIELD",
            target = "Lpl/tomgirl/pylon/window/DisplaySdl;focused:Z",
            opcode = Opcodes.PUTFIELD,
            shift = At.Shift.AFTER
        ),
        remap = false
    )
    private void tgoneconfig$onFocusChanged(CallbackInfo ci) {
        if (focused) EventManager.INSTANCE.post(WindowFocusEvent.Gained.INSTANCE);
        else EventManager.INSTANCE.post(WindowFocusEvent.Lost.INSTANCE);
    }
}
*///?}
