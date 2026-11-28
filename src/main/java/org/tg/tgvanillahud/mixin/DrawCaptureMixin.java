package org.tg.tgvanillahud.mixin;

//? if >=1.21.8 {
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
//? if >=1.21.10
import com.mojang.renderpearl.api.textures.GpuTextureView;
//? if >=1.21.11
import com.mojang.renderpearl.api.textures.GpuSampler;
//? if <=1.21.8
/*import net.minecraft.client.Minecraft;*/
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.metadata.gui.GuiSpriteScaling;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.tg.tgvanillahud.hook.TextShadowHook;
import org.tg.tgvanillahud.render.HeldTooltipBounds;
import org.tg.tgvanillahud.render.HudCapture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = GuiGraphicsExtractor.class, priority = 1500)
public abstract class DrawCaptureMixin {
    //? if >1.21.8 {
    @Shadow
    private static GuiSpriteScaling getSpriteScaling(TextureAtlasSprite sprite) {
        throw new AssertionError();
    }
    //?} else {
    /*@Unique
    private static GuiSpriteScaling getSpriteScaling(TextureAtlasSprite sprite) {
        return Minecraft.getInstance().getGuiSprites().getSpriteScaling(sprite);
    }
    *///?}

    @Unique
    private GuiGraphicsExtractor tgvanillahud$self() {
        return (GuiGraphicsExtractor) (Object) this;
    }

    @WrapMethod(method = "blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;IIIII)V")
    private void tgvanillahud$sprite(RenderPipeline pipeline, TextureAtlasSprite sprite, int x, int y, int w, int h, int color, Operation<Void> original) {
        if (pipeline == RenderPipelines.GUI_TEXTURED && HudCapture.INSTANCE.getCapturing() &&
                getSpriteScaling(sprite) instanceof GuiSpriteScaling.Stretch &&
                HudCapture.sprite(tgvanillahud$self(), sprite, x, y, w, h, color)) return;
        original.call(pipeline, sprite, x, y, w, h, color);
    }

    @WrapMethod(method = "blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;IIIIIIIII)V")
    private void tgvanillahud$spritePart(RenderPipeline pipeline, TextureAtlasSprite sprite, int texW, int texH, int u, int v, int x, int y, int w, int h, int color, Operation<Void> original) {
        if (pipeline == RenderPipelines.GUI_TEXTURED && HudCapture.sprite(tgvanillahud$self(), sprite, texW, texH, u, v, w, h, x, y, w, h, color)) return;
        original.call(pipeline, sprite, texW, texH, u, v, x, y, w, h, color);
    }

    @WrapMethod(method = "innerBlit(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIIFFFFI)V")
    private void tgvanillahud$texture(RenderPipeline pipeline, Identifier id, int x0, int x1, int y0, int y1, float u0, float u1, float v0, float v1, int color, Operation<Void> original) {
        if (pipeline == RenderPipelines.GUI_TEXTURED && HudCapture.texture(tgvanillahud$self(), id, x0, x1, y0, y1, u0, u1, v0, v1, color)) return;
        original.call(pipeline, id, x0, x1, y0, y1, u0, u1, v0, v1, color);
    }

    @WrapMethod(method = "blitNineSlicedSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice;IIIII)V")
    private void tgvanillahud$nineSlice(RenderPipeline pipeline, TextureAtlasSprite sprite, GuiSpriteScaling.NineSlice scaling, int x, int y, int w, int h, int color, Operation<Void> original) {
        HudCapture.nineSlice = sprite;
        try {
            original.call(pipeline, sprite, scaling, x, y, w, h, color);
        } finally {
            HudCapture.nineSlice = null;
        }
    }

    //? if >=26 {
    @WrapMethod(method = "innerTiledBlit(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lcom/mojang/renderpearl/api/textures/GpuTextureView;Lcom/mojang/renderpearl/api/textures/GpuSampler;IIIIIIFFFFI)V")
    private void tgvanillahud$tiled(RenderPipeline pipeline, GpuTextureView texture, GpuSampler sampler, int tileW, int tileH, int x0, int y0, int x1, int y1, float u0, float u1, float v0, float v1, int color, Operation<Void> original) {
        if (pipeline == RenderPipelines.GUI_TEXTURED && HudCapture.tiled(tgvanillahud$self(), tileW, tileH, x0, y0, x1, y1, u0, u1, v0, v1, color)) return;
        original.call(pipeline, texture, sampler, tileW, tileH, x0, y0, x1, y1, u0, u1, v0, v1, color);
    }
    //?} elif >=1.21.11 {
    /*@WrapMethod(method = "submitTiledBlit(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lcom/mojang/renderpearl/api/textures/GpuTextureView;Lcom/mojang/renderpearl/api/textures/GpuSampler;IIIIIIFFFFI)V")
    private void tgvanillahud$tiled(RenderPipeline pipeline, GpuTextureView texture, GpuSampler sampler, int tileW, int tileH, int x0, int y0, int x1, int y1, float u0, float u1, float v0, float v1, int color, Operation<Void> original) {
        if (pipeline == RenderPipelines.GUI_TEXTURED && HudCapture.tiled(tgvanillahud$self(), tileW, tileH, x0, y0, x1, y1, u0, u1, v0, v1, color)) return;
        original.call(pipeline, texture, sampler, tileW, tileH, x0, y0, x1, y1, u0, u1, v0, v1, color);
    }
    *///?} elif >=1.21.10 {
    /*@WrapMethod(method = "submitTiledBlit(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lcom/mojang/renderpearl/api/textures/GpuTextureView;IIIIIIFFFFI)V")
    private void tgvanillahud$tiled(RenderPipeline pipeline, GpuTextureView texture, int tileW, int tileH, int x0, int y0, int x1, int y1, float u0, float u1, float v0, float v1, int color, Operation<Void> original) {
        if (pipeline == RenderPipelines.GUI_TEXTURED && HudCapture.tiled(tgvanillahud$self(), tileW, tileH, x0, y0, x1, y1, u0, u1, v0, v1, color)) return;
        original.call(pipeline, texture, tileW, tileH, x0, y0, x1, y1, u0, u1, v0, v1, color);
    }
    *///?}
    @WrapMethod(method = "fill(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;IIIII)V")
    private void tgvanillahud$fill(RenderPipeline pipeline, int x0, int y0, int x1, int y1, int color, Operation<Void> original) {
        if (HeldTooltipBounds.recording) HeldTooltipBounds.fill(tgvanillahud$self(), x0, y0, x1, y1, color);
        if (pipeline == RenderPipelines.GUI && HudCapture.fill(tgvanillahud$self(), x0, y0, x1, y1, color)) return;
        original.call(pipeline, x0, y0, x1, y1, color);
    }

    @WrapMethod(method = "enableScissor")
    private void tgvanillahud$clipStart(int x0, int y0, int x1, int y1, Operation<Void> original) {
        HudCapture.clipStart(tgvanillahud$self(), x0, y0, x1, y1);
        original.call(x0, y0, x1, y1);
    }

    @WrapMethod(method = "disableScissor")
    private void tgvanillahud$clipEnd(Operation<Void> original) {
        HudCapture.clipEnd();
        original.call();
    }

    //? if >=26 {
    @WrapMethod(method = "item(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V")
    //?} else
    /*@WrapMethod(method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V")*/
    private void tgvanillahud$item(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, Operation<Void> original) {
        if (HudCapture.item(tgvanillahud$self(), stack, x, y)) return;
        original.call(entity, level, stack, x, y, seed);
    }

    //? if >=26 {
    @WrapMethod(method = "text(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)V")
    //?} else
    /*@WrapMethod(method = "drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)V")*/
    private void tgvanillahud$string(Font font, String text, int x, int y, int color, boolean shadow, Operation<Void> original) {
        if (text != null && HeldTooltipBounds.recording) HeldTooltipBounds.text(tgvanillahud$self(), x, y, font.width(text), color);
        if (text != null && HudCapture.text(tgvanillahud$self(), text, x, y, color, TextShadowHook.apply(shadow))) return;
        original.call(font, text, x, y, color, shadow);
    }

    //? if >=26 {
    @WrapMethod(method = "text(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;IIIZ)V")
    //?} else
    /*@WrapMethod(method = "drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;IIIZ)V")*/
    private void tgvanillahud$sequence(Font font, FormattedCharSequence text, int x, int y, int color, boolean shadow, Operation<Void> original) {
        if (HeldTooltipBounds.recording) HeldTooltipBounds.text(tgvanillahud$self(), x, y, font.width(text), color);
        if (HudCapture.text(tgvanillahud$self(), text, x, y, color, TextShadowHook.apply(shadow))) return;
        original.call(font, text, x, y, color, shadow);
    }
}
//?} elif >=1.21.2 {
/*import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.metadata.gui.GuiSpriteScaling;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.tg.tgvanillahud.hook.TextShadowHook;
import org.tg.tgvanillahud.render.HudCapture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Function;

@Mixin(value = GuiGraphicsExtractor.class, priority = 1500)
public abstract class DrawCaptureMixin {
    @Unique
    private GuiGraphicsExtractor tgvanillahud$self() {
        return (GuiGraphicsExtractor) (Object) this;
    }

    @Unique
    private static boolean tgvanillahud$plain(Function<Identifier, RenderType> type, Identifier texture) {
        return type.apply(texture) == RenderType.guiTextured(texture);
    }

    @WrapMethod(method = "blitSprite(Ljava/util/function/Function;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;IIIII)V")
    private void tgvanillahud$sprite(Function<Identifier, RenderType> type, TextureAtlasSprite sprite, int x, int y, int w, int h, int color, Operation<Void> original) {
        if (HudCapture.INSTANCE.getCapturing() &&
                Minecraft.getInstance().getGuiSprites().getSpriteScaling(sprite) instanceof GuiSpriteScaling.Stretch &&
                tgvanillahud$plain(type, sprite.atlasLocation()) && HudCapture.sprite(tgvanillahud$self(), sprite, x, y, w, h, color)) return;
        original.call(type, sprite, x, y, w, h, color);
    }

    @WrapMethod(method = "blitSprite(Ljava/util/function/Function;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;IIIIIIIII)V")
    private void tgvanillahud$spritePart(Function<Identifier, RenderType> type, TextureAtlasSprite sprite, int texW, int texH, int u, int v, int x, int y, int w, int h, int color, Operation<Void> original) {
        if (HudCapture.INSTANCE.getCapturing() && tgvanillahud$plain(type, sprite.atlasLocation()) &&
                HudCapture.sprite(tgvanillahud$self(), sprite, texW, texH, u, v, w, h, x, y, w, h, color)) return;
        original.call(type, sprite, texW, texH, u, v, x, y, w, h, color);
    }

    @WrapMethod(method = "innerBlit(Ljava/util/function/Function;Lnet/minecraft/resources/Identifier;IIIIFFFFI)V")
    private void tgvanillahud$texture(Function<Identifier, RenderType> type, Identifier id, int x0, int x1, int y0, int y1, float u0, float u1, float v0, float v1, int color, Operation<Void> original) {
        if (HudCapture.INSTANCE.getCapturing() && tgvanillahud$plain(type, id) && HudCapture.texture(tgvanillahud$self(), id, x0, x1, y0, y1, u0, u1, v0, v1, color)) return;
        original.call(type, id, x0, x1, y0, y1, u0, u1, v0, v1, color);
    }

    @WrapMethod(method = "fill(Lnet/minecraft/client/renderer/RenderType;IIIIII)V")
    private void tgvanillahud$fill(RenderType type, int x0, int y0, int x1, int y1, int z, int color, Operation<Void> original) {
        if ((type == RenderType.gui() || type == RenderType.guiOverlay()) && HudCapture.fill(tgvanillahud$self(), x0, y0, x1, y1, color)) return;
        original.call(type, x0, y0, x1, y1, z, color);
    }

    @WrapMethod(method = "enableScissor")
    private void tgvanillahud$clipStart(int x0, int y0, int x1, int y1, Operation<Void> original) {
        HudCapture.clipStart(tgvanillahud$self(), x0, y0, x1, y1);
        original.call(x0, y0, x1, y1);
    }

    @WrapMethod(method = "disableScissor")
    private void tgvanillahud$clipEnd(Operation<Void> original) {
        HudCapture.clipEnd();
        original.call();
    }

    @WrapMethod(method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V")
    private void tgvanillahud$item(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int offset, Operation<Void> original) {
        if (HudCapture.item(tgvanillahud$self(), stack, x, y)) return;
        original.call(entity, level, stack, x, y, seed, offset);
    }

    @WrapMethod(method = "drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I")
    private int tgvanillahud$string(Font font, String text, int x, int y, int color, boolean shadow, Operation<Integer> original) {
        boolean drop = TextShadowHook.apply(shadow);
        if (text != null && HudCapture.text(tgvanillahud$self(), text, x, y, color, drop)) {
            int width = HudCapture.getTextWidth();
            return x + (width >= 0 ? width : font.width(text)) + (drop ? 1 : 0);
        }
        return original.call(font, text, x, y, color, shadow);
    }

    @WrapMethod(method = "drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;IIIZ)I")
    private int tgvanillahud$sequence(Font font, FormattedCharSequence text, int x, int y, int color, boolean shadow, Operation<Integer> original) {
        boolean drop = TextShadowHook.apply(shadow);
        if (HudCapture.text(tgvanillahud$self(), text, x, y, color, drop)) return x + font.width(text) + (drop ? 1 : 0);
        return original.call(font, text, x, y, color, shadow);
    }
}
*///?} elif > 1.8.9 {
/*import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.tg.tgvanillahud.hook.TextShadowHook;
import org.tg.tgvanillahud.render.HudCapture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = GuiGraphicsExtractor.class, priority = 1500)
public abstract class DrawCaptureMixin {
    @Unique
    private GuiGraphicsExtractor tgvanillahud$self() {
        return (GuiGraphicsExtractor) (Object) this;
    }

    @Unique
    private static int tgvanillahud$argb(float r, float g, float b, float a) {
        return ((int) (a * 255f) << 24) | ((int) (r * 255f) << 16) | ((int) (g * 255f) << 8) | (int) (b * 255f);
    }

    // this version tints through the shader colour
    @Unique
    private static int tgvanillahud$tint() {
        float[] c = RenderSystem.getShaderColor();
        return tgvanillahud$argb(c[0], c[1], c[2], c[3]);
    }

    @WrapMethod(method = "blitSprite(Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;IIIII)V")
    private void tgvanillahud$sprite(TextureAtlasSprite sprite, int x, int y, int z, int w, int h, Operation<Void> original) {
        if (HudCapture.INSTANCE.getCapturing() && HudCapture.sprite(tgvanillahud$self(), sprite, x, y, w, h, tgvanillahud$tint())) return;
        original.call(sprite, x, y, z, w, h);
    }

    @WrapMethod(method = "blitSprite(Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;IIIIIIIII)V")
    private void tgvanillahud$spritePart(TextureAtlasSprite sprite, int texW, int texH, int u, int v, int x, int y, int z, int w, int h, Operation<Void> original) {
        if (HudCapture.INSTANCE.getCapturing() && HudCapture.sprite(tgvanillahud$self(), sprite, texW, texH, u, v, w, h, x, y, w, h, tgvanillahud$tint())) return;
        original.call(sprite, texW, texH, u, v, x, y, z, w, h);
    }

    @WrapMethod(method = "blit(IIIIILnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V")
    private void tgvanillahud$atlasSprite(int x, int y, int z, int w, int h, TextureAtlasSprite sprite, Operation<Void> original) {
        if (HudCapture.INSTANCE.getCapturing() && HudCapture.sprite(tgvanillahud$self(), sprite, x, y, w, h, tgvanillahud$tint())) return;
        original.call(x, y, z, w, h, sprite);
    }

    @WrapMethod(method = "blit(IIIIILnet/minecraft/client/renderer/texture/TextureAtlasSprite;FFFF)V")
    private void tgvanillahud$atlasSpriteTinted(int x, int y, int z, int w, int h, TextureAtlasSprite sprite, float r, float g, float b, float a, Operation<Void> original) {
        if (HudCapture.INSTANCE.getCapturing() && HudCapture.sprite(tgvanillahud$self(), sprite, x, y, w, h, tgvanillahud$argb(r, g, b, a))) return;
        original.call(x, y, z, w, h, sprite, r, g, b, a);
    }

    @WrapMethod(method = "innerBlit(Lnet/minecraft/resources/Identifier;IIIIIFFFF)V")
    private void tgvanillahud$texture(Identifier id, int x0, int x1, int y0, int y1, int z, float u0, float u1, float v0, float v1, Operation<Void> original) {
        if (HudCapture.INSTANCE.getCapturing() && HudCapture.texture(tgvanillahud$self(), id, x0, x1, y0, y1, u0, u1, v0, v1, tgvanillahud$tint())) return;
        original.call(id, x0, x1, y0, y1, z, u0, u1, v0, v1);
    }

    @WrapMethod(method = "innerBlit(Lnet/minecraft/resources/Identifier;IIIIIFFFFFFFF)V")
    private void tgvanillahud$textureTinted(Identifier id, int x0, int x1, int y0, int y1, int z, float u0, float u1, float v0, float v1, float r, float g, float b, float a, Operation<Void> original) {
        if (HudCapture.INSTANCE.getCapturing() && HudCapture.texture(tgvanillahud$self(), id, x0, x1, y0, y1, u0, u1, v0, v1, tgvanillahud$argb(r, g, b, a))) return;
        original.call(id, x0, x1, y0, y1, z, u0, u1, v0, v1, r, g, b, a);
    }

    @WrapMethod(method = "fill(Lnet/minecraft/client/renderer/RenderType;IIIIII)V")
    private void tgvanillahud$fill(RenderType type, int x0, int y0, int x1, int y1, int z, int color, Operation<Void> original) {
        if ((type == RenderType.gui() || type == RenderType.guiOverlay()) && HudCapture.fill(tgvanillahud$self(), x0, y0, x1, y1, color)) return;
        original.call(type, x0, y0, x1, y1, z, color);
    }

    @WrapMethod(method = "enableScissor")
    private void tgvanillahud$clipStart(int x0, int y0, int x1, int y1, Operation<Void> original) {
        HudCapture.clipStart(tgvanillahud$self(), x0, y0, x1, y1);
        original.call(x0, y0, x1, y1);
    }

    @WrapMethod(method = "disableScissor")
    private void tgvanillahud$clipEnd(Operation<Void> original) {
        HudCapture.clipEnd();
        original.call();
    }

    @WrapMethod(method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V")
    private void tgvanillahud$item(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int offset, Operation<Void> original) {
        if (HudCapture.item(tgvanillahud$self(), stack, x, y)) return;
        original.call(entity, level, stack, x, y, seed, offset);
    }

    @WrapMethod(method = "drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I")
    private int tgvanillahud$string(Font font, String text, int x, int y, int color, boolean shadow, Operation<Integer> original) {
        boolean drop = TextShadowHook.apply(shadow);
        if (text != null && HudCapture.text(tgvanillahud$self(), text, x, y, color, drop)) {
            int width = HudCapture.getTextWidth();
            return x + (width >= 0 ? width : font.width(text)) + (drop ? 1 : 0);
        }
        return original.call(font, text, x, y, color, shadow);
    }

    @WrapMethod(method = "drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;IIIZ)I")
    private int tgvanillahud$sequence(Font font, FormattedCharSequence text, int x, int y, int color, boolean shadow, Operation<Integer> original) {
        boolean drop = TextShadowHook.apply(shadow);
        if (HudCapture.text(tgvanillahud$self(), text, x, y, color, drop)) return x + font.width(text) + (drop ? 1 : 0);
        return original.call(font, text, x, y, color, shadow);
    }
}
*///?} else {
/*import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.gui.GuiElement;
import org.tg.tgvanillahud.compat.LegacyDrawContext;
import org.tg.tgvanillahud.render.HudCapture;
import org.spongepowered.asm.mixin.Mixin;

// 1.8.9 has no draw context, so each kind of draw is caught where it reaches GL: these for quads,
// with the font, item, texture and colour hooks in the Capture mixins beside this one
@Mixin(value = GuiElement.class, priority = 1500)
public abstract class DrawCaptureMixin {
    @WrapMethod(method = "drawTexture(IIIIII)V")
    private void tgvanillahud$quad(int x, int y, int u, int v, int w, int h, Operation<Void> original) {
        float s = 1f / 256f;
        if (HudCapture.isActive() && HudCapture.texture(LegacyDrawContext.INSTANCE, x, y, w, h, u * s, v * s, (u + w) * s, (v + h) * s)) return;
        original.call(x, y, u, v, w, h);
    }

    @WrapMethod(method = "drawTexture(IIFFIIFF)V")
    private static void tgvanillahud$sized(int x, int y, float u, float v, int w, int h, float texW, float texH, Operation<Void> original) {
        if (HudCapture.isActive() && HudCapture.texture(LegacyDrawContext.INSTANCE, x, y, w, h, u / texW, v / texH, (u + w) / texW, (v + h) / texH)) return;
        original.call(x, y, u, v, w, h, texW, texH);
    }

    @WrapMethod(method = "drawTexture(IIFFIIIIFF)V")
    private static void tgvanillahud$scaled(int x, int y, float u, float v, int uW, int vH, int w, int h, float texW, float texH, Operation<Void> original) {
        if (HudCapture.isActive() && HudCapture.texture(LegacyDrawContext.INSTANCE, x, y, w, h, u / texW, v / texH, (u + uW) / texW, (v + vH) / texH)) return;
        original.call(x, y, u, v, uW, vH, w, h, texW, texH);
    }

    @WrapMethod(method = "fill(IIIII)V")
    private static void tgvanillahud$fill(int x0, int y0, int x1, int y1, int color, Operation<Void> original) {
        if (HudCapture.fill(LegacyDrawContext.INSTANCE, x0, y0, x1, y1, color)) return;
        original.call(x0, y0, x1, y1, color);
    }
}
*///?}
