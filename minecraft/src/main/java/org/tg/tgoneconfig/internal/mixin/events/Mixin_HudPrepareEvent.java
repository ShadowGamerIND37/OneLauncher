package org.tg.tgoneconfig.internal.mixin.events;

//? if > 1.8.9 {
import com.llamalad7.mixinextras.sugar.Local;
import org.tg.tgoneconfig.internal.TGOneConfig;
import org.tg.tgoneconfig.internal.VanillaHudSampler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >= 26.1 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?} else {
/*import net.minecraft.client.gui.GuiGraphics;
*///?}

//? if >= 26.2 {
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
//?}

//? if < 26.2 {
/*import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
*///?}

//? if >= 26.2 {
@Mixin(Gui.class)
//? } else {
/*@Mixin(GameRenderer.class)
*///? }
public class Mixin_HudPrepareEvent {
    //? if >= 26.2 {
    @Inject(method = "extractRenderState", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/Hud;extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V"))
    //? } elif >= 26.1 {
    /*@Inject(method = "extractGui", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/Gui;extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V"))
    *///? } else {
    /*@Inject(method = "render", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/Gui;render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V"))
    *///? }
    private void prepareHudCallback(CallbackInfo ci) {
        TGOneConfig.prepareHud();
    }

    //? if >= 26.2 {
    @Shadow @Final private Hud hud;

    @Inject(method = "extractRenderState", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/Hud;extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V",
            shift = At.Shift.AFTER))
    private void afterHudCallback(CallbackInfo ci, @Local(name = "graphics") GuiGraphicsExtractor graphics) {
        VanillaHudSampler.sample(hud);
        TGOneConfig.submitHud(graphics);
    }
    //? } elif >= 26.1 {
    /*@Inject(method = "extractGui", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/Gui;extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V",
            shift = At.Shift.AFTER))
    private void afterHudCallback(CallbackInfo ci, @Local(name = "graphics") GuiGraphicsExtractor graphics) {
        VanillaHudSampler.sample(Minecraft.getInstance().gui);
        TGOneConfig.submitHud(graphics);
    }
    *///? } else {
    /*@Inject(method = "render", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/Gui;render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V",
            shift = At.Shift.AFTER))
    private void afterHudCallback(CallbackInfo ci, @Local GuiGraphics graphics) {
        VanillaHudSampler.sample(Minecraft.getInstance().gui);
        TGOneConfig.submitHud(graphics);
    }
    *///? }
}
//?} else {
/*import net.minecraft.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SharedConstants.class)
public class Mixin_HudPrepareEvent {}
*///?}
