package org.tg.tgvanillahud.mixin.elements;

//? if = 1.8.9 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.overlay.PlayerTabOverlay;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.tg.tgvanillahud.compat.LegacyDrawContext;
import org.tg.tgvanillahud.render.HudTransform;
*///?}

import org.tg.tgvanillahud.hud.Huds;
import org.tg.tgvanillahud.hud.TabListHud;
import org.tg.tgvanillahud.hud.VanillaHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

//? if <1.21.4 && >1.8.9 {
/*import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.tg.tgvanillahud.render.HudTransform;
*///?}

//? if >=26.2 {
import net.minecraft.client.gui.Hud;
//?} elif > 1.8.9 {
/*import net.minecraft.client.gui.Gui;
*///?} else {
/*import net.minecraft.client.gui.GameGui;
*///?}

//? if >=26.2 {
@Mixin(Hud.class)
//?} elif > 1.8.9 {
/*@Mixin(Gui.class)
*///?} else {
/*@Mixin(GameGui.class)
*///?}
public class GuiMixinTabList {
    //? if > 1.8.9 {
    //? if <1.21.4 && >1.8.9 {
    /*@WrapMethod(method = "renderTabList")
    private void tgvanillahud$tabList(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker,
                                    Operation<Void> original) {
        if (!Huds.INSTANCE.getTabList().shouldDraw()) return;

        HudTransform.begin(graphics, Huds.INSTANCE.getTabList());
        original.call(graphics, deltaTracker);
        HudTransform.end(graphics);
    }
    *///?}

    @Unique
    private boolean tgvanillahud$keyHeld;
    @Unique
    private boolean tgvanillahud$toggled;

    @ModifyExpressionValue(
            //? if <26 {
            /*method = "renderTabList",
            *///?} else {
            method = "extractTabList",
            //?}
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyMapping;isDown()Z")
    )
    private boolean tgvanillahud$displayMode(boolean down) {
        TabListHud hud = Huds.INSTANCE.getTabList();
        if (hud.isDrawnAsVanilla()) return down;
        if (VanillaHud.previewing()) {
            hud.resetOpen();
            return true;
        }
        boolean open;
        if (hud.getDisplayMode() == 0) {
            open = down;
        } else {
            if (down && !tgvanillahud$keyHeld) tgvanillahud$toggled = !tgvanillahud$toggled;
            tgvanillahud$keyHeld = down;
            open = tgvanillahud$toggled;
        }
        hud.updateOpen(open);
        return open || hud.isRendering();
    }
    //?} else {
    /*// 1.8.9 only asks the overlay to render while the list is open, so that call is the
    // transform bracket and the key check below is the open signal the slide animation needs.
    @Unique
    private boolean tgvanillahud$keyHeld;
    @Unique
    private boolean tgvanillahud$toggled;

    @ModifyExpressionValue(method = "render", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/options/KeyBinding;isPressed()Z"))
    private boolean tgvanillahud$displayMode(boolean down) {
        TabListHud hud = Huds.INSTANCE.getTabList();
        if (hud.isDrawnAsVanilla()) return down;
        if (VanillaHud.previewing()) {
            hud.resetOpen();
            return true;
        }
        boolean open;
        if (hud.getDisplayMode() == 0) {
            open = down;
        } else {
            if (down && !tgvanillahud$keyHeld) tgvanillahud$toggled = !tgvanillahud$toggled;
            tgvanillahud$keyHeld = down;
            open = tgvanillahud$toggled;
        }
        hud.updateOpen(open);
        return open || hud.isRendering();
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/overlay/PlayerTabOverlay;render(ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreboardObjective;)V"))
    private void tgvanillahud$tabList(PlayerTabOverlay overlay, int width, Scoreboard scoreboard,
                                    ScoreboardObjective objective, Operation<Void> original) {
        TabListHud hud = Huds.INSTANCE.getTabList();
        if (!hud.shouldDraw()) return;

        HudTransform.begin(LegacyDrawContext.INSTANCE, hud);
        original.call(overlay, width, scoreboard, objective);
        HudTransform.end(LegacyDrawContext.INSTANCE);
    }
    *///?}
}
