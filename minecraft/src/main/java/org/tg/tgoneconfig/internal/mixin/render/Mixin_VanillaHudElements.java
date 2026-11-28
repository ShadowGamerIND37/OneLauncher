//~ gui_graphics
package org.tg.tgoneconfig.internal.mixin.render;

//? if > 1.8.9 {
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
//? if >= 26.2 {
import net.minecraft.client.gui.Hud;
//?} else {
/*import net.minecraft.client.gui.Gui;
*///?}
//? if >= 26.3 {
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
//?} else if >= 1.21.6 {
/*import com.mojang.blaze3d.pipeline.RenderPipeline;
*///?} else if >= 1.21.4 {
/*import java.util.function.Function;
*///?}
//? if >= 1.21.11 {
import net.minecraft.resources.Identifier;
//?} else {
/*import net.minecraft.resources.ResourceLocation;
*///?}
import org.tg.tgoneconfig.api.ui.v1.platform.VanillaHudElement;
import org.tg.tgoneconfig.api.ui.v1.platform.VanillaHudHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

//? if >= 26.2 {
@Mixin(Hud.class)
//?} else {
/*@Mixin(Gui.class)
*///?}
public abstract class Mixin_VanillaHudElements {
    @Shadow
    private Component overlayMessageString;

    @Shadow
    private int overlayMessageTime;

    @Shadow
    private Component title;

    @Shadow
    private int titleTime;

    @Shadow
    private int toolHighlightTimer;

    @Shadow
    private ItemStack lastToolHighlight;

    @Unique
    private static final String CROSSHAIR =
        //~ if >= 26.1 'renderCrosshair' -> 'extractCrosshair'
        "extractCrosshair";

    @Unique
    private static final String BLIT_SPRITE =
        //? if >= 26.3 {
        "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V";
        //?} else if >= 1.21.11 {
        /*"Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V";
        *///?} else if >= 1.21.6 {
        /*"Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V";
        *///?} else if >= 1.21.4 {
        /*"Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V";
        *///?} else {
        /*"Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V";
        *///?}

    @Unique
    private static final String BLIT_SPRITE_REGION =
        //? if >= 26.3 {
        "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIIIIII)V";
        //?} else if >= 1.21.11 {
        /*"Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIIIIII)V";
        *///?} else if >= 1.21.6 {
        /*"Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V";
        *///?} else if >= 1.21.4 {
        /*"Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V";
        *///?} else {
        /*"Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V";
        *///?}

    @Unique
    private static final int BLIT_X =
        //? if >= 1.21.4 {
        2;
        //?} else {
        /*1;
        *///?}

    @Unique
    private static final int BLIT_Y = BLIT_X + 1;

    @Unique
    private boolean tgoneconfig$crosshairReached;

    @Unique
    private boolean tgoneconfig$attackIndicatorReached;

    @Unique
    private boolean tgoneconfig$attackIndicatorVanilla;

    @Unique
    private static final String EFFECTS_DRAW_START =
        //? if >= 1.21.4 {
        "Lcom/google/common/collect/Ordering;natural()Lcom/google/common/collect/Ordering;";
        //?} else {
        /*"Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V";
        *///?}

    @Unique
    private static final String DISPLAY_SCOREBOARD_SIDEBAR =
        //? if >= 26.2 {
        "Lnet/minecraft/client/gui/Hud;displayScoreboardSidebar(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/scores/Objective;)V";
        //?} else {
        /*"Lnet/minecraft/client/gui/Gui;displayScoreboardSidebar(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/scores/Objective;)V";
        *///?}

    @WrapWithCondition(method = CROSSHAIR, at = @At(value = "INVOKE", target = BLIT_SPRITE, ordinal = 0))
    private boolean tgoneconfig$crosshair(
        GuiGraphicsExtractor graphics,
        //? if >= 1.21.6 {
        RenderPipeline pipeline,
        //?} else if >= 1.21.4 {
        /*Function<?, ?> renderType,
        *///?}
        //? if >= 1.21.11 {
        Identifier sprite,
        //?} else {
        /*ResourceLocation sprite,
        *///?}
        int x, int y, int width, int height
    ) {
        tgoneconfig$crosshairReached = true;
        return !VanillaHudHooks.intercept(VanillaHudElement.CROSSHAIR, graphics);
    }

    @WrapWithCondition(method = CROSSHAIR, at = {
        @At(value = "INVOKE", target = BLIT_SPRITE, ordinal = 1),
        @At(value = "INVOKE", target = BLIT_SPRITE, ordinal = 2),
    })
    private boolean tgoneconfig$attackIndicator(
        GuiGraphicsExtractor graphics,
        //? if >= 1.21.6 {
        RenderPipeline pipeline,
        //?} else if >= 1.21.4 {
        /*Function<?, ?> renderType,
        *///?}
        //? if >= 1.21.11 {
        Identifier sprite,
        //?} else {
        /*ResourceLocation sprite,
        *///?}
        int x, int y, int width, int height
    ) {
        tgoneconfig$attackIndicatorReached = true;
        tgoneconfig$attackIndicatorVanilla = !VanillaHudHooks.intercept(VanillaHudElement.ATTACK_INDICATOR, graphics);
        return tgoneconfig$attackIndicatorVanilla;
    }

    @WrapWithCondition(method = CROSSHAIR, at = @At(value = "INVOKE", target = BLIT_SPRITE_REGION))
    private boolean tgoneconfig$attackIndicatorProgress(
        GuiGraphicsExtractor graphics,
        //? if >= 1.21.6 {
        RenderPipeline pipeline,
        //?} else if >= 1.21.4 {
        /*Function<?, ?> renderType,
        *///?}
        //? if >= 1.21.11 {
        Identifier sprite,
        //?} else {
        /*ResourceLocation sprite,
        *///?}
        int spriteWidth, int spriteHeight, int u, int v, int x, int y, int width, int height
    ) {
        return tgoneconfig$attackIndicatorVanilla;
    }

    @ModifyArgs(method = CROSSHAIR, at = {
        @At(value = "INVOKE", target = BLIT_SPRITE, ordinal = 1),
        @At(value = "INVOKE", target = BLIT_SPRITE, ordinal = 2),
    })
    private void tgoneconfig$moveAttackIndicator(Args args) {
        tgoneconfig$move(args, BLIT_X, BLIT_Y);
    }

    @ModifyArgs(method = CROSSHAIR, at = @At(value = "INVOKE", target = BLIT_SPRITE_REGION))
    private void tgoneconfig$moveAttackIndicatorProgress(Args args) {
        tgoneconfig$move(args, BLIT_X + 4, BLIT_Y + 4);
    }

    @Unique
    private static void tgoneconfig$move(Args args, int xIndex, int yIndex) {
        int x = args.get(xIndex);
        int y = args.get(yIndex);
        args.set(xIndex, VanillaHudHooks.moveX(x, y));
        args.set(yIndex, VanillaHudHooks.moveY(x, y));
    }

    @Inject(method = CROSSHAIR, at = @At("RETURN"))
    private void tgoneconfig$crosshairHidden(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!tgoneconfig$crosshairReached) {
            VanillaHudHooks.interceptHidden(VanillaHudElement.CROSSHAIR, graphics);
        }
        if (!tgoneconfig$attackIndicatorReached) {
            VanillaHudHooks.interceptHidden(VanillaHudElement.ATTACK_INDICATOR, graphics);
        }
        tgoneconfig$crosshairReached = false;
        tgoneconfig$attackIndicatorReached = false;
    }

    //~ if >= 26.1 'renderEffects' -> 'extractEffects'
    @Inject(method = "extractEffects", at = @At(value = "INVOKE", target = EFFECTS_DRAW_START, ordinal = 0), cancellable = true)
    private void tgoneconfig$statusEffects(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (VanillaHudHooks.intercept(VanillaHudElement.STATUS_EFFECTS, graphics)) ci.cancel();
    }

    //~ if >= 26.1 'renderScoreboardSidebar' -> 'extractScoreboardSidebar'
    @Inject(method = "extractScoreboardSidebar", at = @At(value = "INVOKE", target = DISPLAY_SCOREBOARD_SIDEBAR), cancellable = true)
    private void tgoneconfig$scoreboard(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (VanillaHudHooks.intercept(VanillaHudElement.SCOREBOARD, graphics)) ci.cancel();
    }

    //~ if >= 26.1 'renderOverlayMessage' -> 'extractOverlayMessage'
    @Inject(method = "extractOverlayMessage", at = @At("HEAD"), cancellable = true)
    private void tgoneconfig$actionBar(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (overlayMessageString == null || overlayMessageTime <= 0) return;
        if (VanillaHudHooks.intercept(VanillaHudElement.ACTION_BAR, graphics)) ci.cancel();
    }

    //~ if >= 26.1 'renderTitle' -> 'extractTitle'
    @Inject(method = "extractTitle", at = @At("HEAD"), cancellable = true)
    private void tgoneconfig$title(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (title == null || titleTime <= 0) return;
        if (VanillaHudHooks.intercept(VanillaHudElement.TITLE, graphics)) ci.cancel();
    }

    //~ if >= 26.1 'renderSelectedItemName' -> 'extractSelectedItemName'
    @Inject(method = "extractSelectedItemName", at = @At("HEAD"), cancellable = true)
    private void tgoneconfig$heldItemName(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        if (toolHighlightTimer <= 0 || lastToolHighlight.isEmpty()) return;
        if (VanillaHudHooks.intercept(VanillaHudElement.HELD_ITEM_NAME, graphics)) ci.cancel();
    }

    //~ if >= 26.1 'renderVignette' -> 'extractVignette'
    @Inject(method = "extractVignette", at = @At("HEAD"), cancellable = true)
    private void tgoneconfig$vignette(GuiGraphicsExtractor graphics, Entity entity, CallbackInfo ci) {
        if (VanillaHudHooks.intercept(VanillaHudElement.VIGNETTE, graphics)) ci.cancel();
    }
}
//?} else {
/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.GameGui;
import org.tg.tgoneconfig.api.ui.v1.platform.VanillaHudElement;
import org.tg.tgoneconfig.api.ui.v1.platform.VanillaHudHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameGui.class)
public abstract class Mixin_VanillaHudElements {
    @ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GameGui;hasCrosshair()Z"))
    private boolean tgoneconfig$crosshair(boolean original) {
        if (!original) {
            VanillaHudHooks.interceptHidden(VanillaHudElement.CROSSHAIR, null);
            return false;
        }
        return !VanillaHudHooks.intercept(VanillaHudElement.CROSSHAIR, null);
    }
}
*///?}
