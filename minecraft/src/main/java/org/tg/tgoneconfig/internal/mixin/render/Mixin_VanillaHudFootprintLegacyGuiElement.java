package org.tg.tgoneconfig.internal.mixin.render;

//? if = 1.8.9 {
/*import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.render.texture.TextureAtlasSprite;
import org.tg.tgoneconfig.internal.LegacyVanillaHudSampler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiElement.class)
public abstract class Mixin_VanillaHudFootprintLegacyGuiElement {
    @Inject(method = "fill", at = @At("HEAD"), cancellable = true)
    private static void tgoneconfig$measureFill(int x1, int y1, int x2, int y2, int color, CallbackInfo ci) {
        if (LegacyVanillaHudSampler.isMeasuring() && LegacyVanillaHudSampler.record(x1, y1, x2, y2)) ci.cancel();
    }

    @Inject(method = "fillGradient", at = @At("HEAD"), cancellable = true)
    private void tgoneconfig$measureGradient(int x1, int y1, int x2, int y2, int color1, int color2, CallbackInfo ci) {
        if (LegacyVanillaHudSampler.isMeasuring() && LegacyVanillaHudSampler.record(x1, y1, x2, y2)) ci.cancel();
    }

    @Inject(method = "drawTexture(IIIIII)V", at = @At("HEAD"), cancellable = true)
    private void tgoneconfig$measureTexture(int x, int y, int u, int v, int width, int height, CallbackInfo ci) {
        if (LegacyVanillaHudSampler.isMeasuring() && LegacyVanillaHudSampler.record(x, y, x + width, y + height)) ci.cancel();
    }

    @Inject(method = "drawTexture(FFIIII)V", at = @At("HEAD"), cancellable = true)
    private void tgoneconfig$measureTextureFloat(float x, float y, int u, int v, int width, int height, CallbackInfo ci) {
        if (LegacyVanillaHudSampler.isMeasuring() && LegacyVanillaHudSampler.record(x, y, x + width, y + height)) ci.cancel();
    }

    @Inject(method = "drawSprite", at = @At("HEAD"), cancellable = true)
    private void tgoneconfig$measureSprite(int x, int y, TextureAtlasSprite sprite, int width, int height, CallbackInfo ci) {
        if (LegacyVanillaHudSampler.isMeasuring() && LegacyVanillaHudSampler.record(x, y, x + width, y + height)) ci.cancel();
    }

    @Inject(method = "drawTexture(IIFFIIFF)V", at = @At("HEAD"), cancellable = true)
    private static void tgoneconfig$measureScaledTexture(int x, int y, float u, float v, int width, int height, float scaleU, float scaleV, CallbackInfo ci) {
        if (LegacyVanillaHudSampler.isMeasuring() && LegacyVanillaHudSampler.record(x, y, x + width, y + height)) ci.cancel();
    }

    @Inject(method = "drawTexture(IIFFIIIIFF)V", at = @At("HEAD"), cancellable = true)
    private static void tgoneconfig$measureRegionTexture(int x, int y, float u, float v, int textureWidth, int textureHeight, int width, int height, float scaleU, float scaleV, CallbackInfo ci) {
        if (LegacyVanillaHudSampler.isMeasuring() && LegacyVanillaHudSampler.record(x, y, x + width, y + height)) ci.cancel();
    }
}
*///?}
