package org.tg.tgoneconfig.internal.mixin.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.LinkedHashSet;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import org.tg.tgoneconfig.api.ui.v1.keybind.internal.MinecraftKeybindBridgeImpl;
import org.tg.tgoneconfig.internal.ui.keybind.TGOneConfigKeybindRecorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if >= 1.21.10 {
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
//?}

//? if > 1.8.9 {
import net.minecraft.client.gui.screens.options.controls.KeyBindsList;
//?}

//? if >= 1.21.10 && < 26.3 {
/*import net.minecraft.client.Minecraft;
*///?}

//? if = 1.8.9 {
/*import org.lwjgl.input.Mouse;
import org.tg.tgoneconfig.internal.legacy.KeyCodes;
*///?}

//? if sdl {
import org.lwjgl.sdl.SDLMouse;
//?} else {
/*import org.lwjgl.glfw.GLFW;
import org.tg.tgoneconfig.api.platform.v1.Platform;
*///?}

@Mixin(KeyBindsScreen.class)
public class Mixin_TGOneConfigKeybindRebind implements TGOneConfigKeybindRecorder {
    @Shadow public KeyMapping selectedKey;
    //? if > 1.8.9
    @Shadow private KeyBindsList keyBindsList;

    @Unique private final LinkedHashSet<Integer> tgoneconfig$keys = new LinkedHashSet<>();
    @Unique private final LinkedHashSet<Integer> tgoneconfig$mouse = new LinkedHashSet<>();
    @Unique private boolean tgoneconfig$recording = false;
    @Unique private KeyMapping tgoneconfig$target = null;

    //? if >=1.21.10 {
    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void tgoneconfig$keyPressed(KeyEvent event, CallbackInfoReturnable<Boolean> cir) {
        if (!tgoneconfig$isOurs()) return;
        if (event.key() == InputConstants.KEY_ESCAPE) {
            tgoneconfig$recordEscape();
            cir.setReturnValue(true);
            return;
        }
        tgoneconfig$recordKey(event.key());
        cir.setReturnValue(true);
    }
    //?} else {
    /*@Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    //? if > 1.8.9 {
    private void tgoneconfig$keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
    //?} else
    //private void tgoneconfig$keyPressed(char keyChar, int keyCode, CallbackInfo ci) {
        if (!tgoneconfig$isOurs()) return;
        //? if = 1.8.9 {
        /^keyCode = KeyCodes.fromLegacy(keyCode).getValue();
        if (keyCode <= 0) {
            ci.cancel();
            return;
        }
        ^///?}
        if (keyCode == InputConstants.KEY_ESCAPE) {
            tgoneconfig$recordEscape();
            //$ if > 1.8.9 'cir.setReturnValue(true);' else 'ci.cancel();'
            cir.setReturnValue(true);
            return;
        }
        tgoneconfig$recordKey(keyCode);
        //$ if > 1.8.9 'cir.setReturnValue(true);' else 'ci.cancel();'
        cir.setReturnValue(true);
    }
    *///?}

    //? if >=1.21.10 {
    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void tgoneconfig$mouseClicked(MouseButtonEvent event, boolean doubleClick, CallbackInfoReturnable<Boolean> cir) {
        if (!tgoneconfig$isOurs()) return;
        tgoneconfig$recordMouse(event.button());
        cir.setReturnValue(true);
    }
    //?} else {
    /*@Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    //? if > 1.8.9 {
    private void tgoneconfig$mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
    //?} else
    //private void tgoneconfig$mouseClicked(int mouseX, int mouseY, int button, CallbackInfo ci) {
        if (!tgoneconfig$isOurs()) return;
        //? if = 1.8.9
        //button = KeyCodes.mouseFromLegacy(button);
        tgoneconfig$recordMouse(button);
        //$ if > 1.8.9 'cir.setReturnValue(true);' else 'ci.cancel();'
        cir.setReturnValue(true);
    }
    *///?}

    //? if >=26.1 {
    @Inject(method = "extractRenderState", at = @At("HEAD"))
    private void tgoneconfig$poll(CallbackInfo ci) {
        tgoneconfig$pollInputs();
    }
    //?} else {
    /*@Inject(method = "render", at = @At("HEAD"))
    //? if > 1.8.9 {
    private void tgoneconfig$poll(CallbackInfo ci) {
    //?} else
    //private void tgoneconfig$poll(int mouseX, int mouseY, float tickDelta, CallbackInfo ci) {
        tgoneconfig$pollInputs();
    }
    *///?}

    @Unique
    private void tgoneconfig$pollInputs() {
        MinecraftKeybindBridgeImpl bridge = MinecraftKeybindBridgeImpl.instance();
        if (bridge != null) bridge.setActiveRebind(tgoneconfig$target != null ? tgoneconfig$target : this.selectedKey);
        if (!tgoneconfig$recording) return;
        for (int k : tgoneconfig$keys) {
            //? if sdl {
            if (InputConstants.isKeyDown(k)) return;
            //?} else {
            /*//~ if < 1.21.10 'Minecraft.getInstance().getWindow()' -> 'Platform.compatibility().windowHandle()'
            if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow(), k)) return;
            *///?}
        }
        //? if sdl {
        int buttons = SDLMouse.SDL_GetMouseState(null, null);
        for (int b : tgoneconfig$mouse) {
            if (b > 0 && b <= Integer.SIZE && (buttons & (1 << (b - 1))) != 0) return;
        }
        //?} elif > 1.8.9 {
        /*long window = Platform.compatibility().windowHandle();
        for (int b : tgoneconfig$mouse) if (GLFW.glfwGetMouseButton(window, b) != InputConstants.RELEASE) return;
        *///?} else
        //for (int b : tgoneconfig$mouse) if (Mouse.isButtonDown(KeyCodes.mouseToLegacy(b))) return;
        tgoneconfig$commit();
    }

    @Override
    public boolean tgoneconfig$isOurs() {
        if (tgoneconfig$target != null) return true;
        KeyMapping sel = this.selectedKey;
        if (sel == null) return false;
        MinecraftKeybindBridgeImpl bridge = MinecraftKeybindBridgeImpl.instance();
        return bridge != null && bridge.bindFor(sel) != null;
    }

    @Unique
    private boolean tgoneconfig$begin() {
        if (tgoneconfig$target == null) {
            KeyMapping sel = this.selectedKey;
            MinecraftKeybindBridgeImpl bridge = MinecraftKeybindBridgeImpl.instance();
            if (sel == null || bridge == null || bridge.bindFor(sel) == null) return false;
            tgoneconfig$target = sel;
        }
        tgoneconfig$recording = true;
        return true;
    }

    @Override
    public void tgoneconfig$recordKey(int keyCode) {
        //? if sdl
        if (keyCode <= 0) return;
        if (!tgoneconfig$begin()) return;
        tgoneconfig$keys.add(keyCode);
        tgoneconfig$preview();
    }

    @Unique
    private void tgoneconfig$recordMouse(int button) {
        if (!tgoneconfig$begin()) return;
        tgoneconfig$mouse.add(button);
        tgoneconfig$preview();
    }

    @Override
    public void tgoneconfig$recordEscape() {
        MinecraftKeybindBridgeImpl bridge = MinecraftKeybindBridgeImpl.instance();
        KeyMapping target = tgoneconfig$target != null ? tgoneconfig$target : this.selectedKey;
        if (bridge != null && target != null) {
            bridge.clearPreview();
            bridge.menuUnbind(target);
        }
        tgoneconfig$reset();
        tgoneconfig$finishSelection();
    }

    @Unique
    private void tgoneconfig$preview() {
        MinecraftKeybindBridgeImpl bridge = MinecraftKeybindBridgeImpl.instance();
        if (bridge == null || tgoneconfig$target == null) return;
        bridge.setPreview(tgoneconfig$target, tgoneconfig$toArray(tgoneconfig$keys), tgoneconfig$toArray(tgoneconfig$mouse));
        //? if > 1.8.9
        if (this.keyBindsList != null) this.keyBindsList.resetMappingAndUpdateButtons();
    }

    @Unique
    private void tgoneconfig$commit() {
        KeyMapping sel = tgoneconfig$target;
        MinecraftKeybindBridgeImpl bridge = MinecraftKeybindBridgeImpl.instance();
        if (sel != null && bridge != null && (!tgoneconfig$keys.isEmpty() || !tgoneconfig$mouse.isEmpty())) {
            bridge.menuRebind(sel, tgoneconfig$toArray(tgoneconfig$keys), tgoneconfig$toArray(tgoneconfig$mouse), true);
        }
        if (bridge != null) bridge.clearPreview();
        tgoneconfig$reset();
        tgoneconfig$finishSelection();
    }

    @Unique
    private void tgoneconfig$finishSelection() {
        this.selectedKey = null;
        //? if > 1.8.9
        if (this.keyBindsList != null) this.keyBindsList.resetMappingAndUpdateButtons();
    }

    @Unique
    private void tgoneconfig$reset() {
        tgoneconfig$recording = false;
        tgoneconfig$keys.clear();
        tgoneconfig$mouse.clear();
        tgoneconfig$target = null;
    }

    @Unique
    private static int[] tgoneconfig$toArray(LinkedHashSet<Integer> set) {
        if (set.isEmpty()) return null;
        int[] out = new int[set.size()];
        int i = 0;
        for (int v : set) out[i++] = v;
        return out;
    }
}
