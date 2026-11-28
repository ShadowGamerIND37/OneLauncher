package org.tg.tgvanillahud.mixin.elements;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//? if > 1.8.9
import net.minecraft.client.DeltaTracker;
//? if > 1.8.9
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.tg.tgvanillahud.hud.HotbarHud;
import org.tg.tgvanillahud.hud.Huds;
import org.tg.tgvanillahud.hook.TextShadowHook;
import org.tg.tgvanillahud.render.HudTransform;
//? if > 1.8.9
import org.tg.tgvanillahud.util.DemoData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;

//? if >=1.21.6 {
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
//?}
//? if >=1.21.2 <1.21.6 {
/*import net.minecraft.client.renderer.RenderType;
import java.util.function.Function;
*///?}

import net.minecraft.resources.Identifier;

//? if = 1.8.9 {
/*import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Window;
import org.tg.tgvanillahud.compat.LegacyDrawContext;
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
public abstract class GuiMixinHotbar {
    //? if > 1.8.9 {
    @WrapMethod(
            //? if < 26 {
            /*method = "renderItemHotbar"
            *///?} else {
            method = "extractItemHotbar"
            //?}
    )
    private void tgvanillahud$hotbar(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, Operation<Void> original) {
        HotbarHud hud = Huds.INSTANCE.getHotbar();
        if (hud.isDrawnAsVanilla()) {
            original.call(graphics, deltaTracker);
            return;
        }
        if (!hud.shouldDraw()) return;

        HudTransform.begin(graphics, hud);
        tgvanillahud$setup(deltaTracker, hud);
        original.call(graphics, deltaTracker);
        tgvanillahud$active = false;
        HudTransform.end(graphics);
    }

    @Invoker("getCameraPlayer")
    abstract Player tgvanillahud$getCameraPlayer();

    @Unique private static final Identifier VANILLAHUD$SELECTION = Identifier.withDefaultNamespace("hud/hotbar_selection");

    // half an item icon so the counter rotation can pivot on the icon centre
    @Unique private static final float VANILLAHUD$ITEM_HALF = 8f;

    @Unique private boolean tgvanillahud$active;
    @Unique private float tgvanillahud$animSlot;
    @Unique private boolean tgvanillahud$animInit;

    @Unique
    private void tgvanillahud$setup(DeltaTracker deltaTracker, HotbarHud hud) {
        Player player = tgvanillahud$getCameraPlayer();
        int selected;
        if (player == null || hud.showsDemo()) {
            selected = 0;
        } else {
            //? if >=1.21.5 {
            selected = player.getInventory().getSelectedSlot();
            //?} else {
            /*selected = player.getInventory().selected;
            *///?}
        }
        if (!tgvanillahud$animInit) {
            tgvanillahud$animSlot = (float) selected;
            tgvanillahud$animInit = true;
        }
        if (!hud.getAnimation()) {
            tgvanillahud$animSlot = (float) selected;
        } else {
            float t = Math.min(1f, deltaTracker.getRealtimeDeltaTicks() * 0.6f);
            tgvanillahud$animSlot += ((float) selected - tgvanillahud$animSlot) * t;
        }

        tgvanillahud$active = true;
    }

    @WrapOperation(
            //? if < 26 {
            /*method = "renderItemHotbar",
            *///?} else {
            method = "extractItemHotbar",
            //?}
            //? if >=1.21.6 {
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V")
            //?} else if >=1.21.2 {
            /*at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/Identifier;IIII)V")
            *///?} else {
            /*at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lnet/minecraft/resources/Identifier;IIII)V")
            *///?}
    )
    private void tgvanillahud$blit(GuiGraphicsExtractor graphics,
            //? if >=1.21.6 {
            RenderPipeline pipeline,
            //?} else if >=1.21.2 {
            /*Function<Identifier, RenderType> pipeline,
            *///?}
            Identifier sprite,
            int x, int y, int width, int height, Operation<Void> original) {
        if (tgvanillahud$active && VANILLAHUD$SELECTION.equals(sprite)) {
            x = graphics.guiWidth() / 2 - 92 + Math.round(tgvanillahud$animSlot * 20f);
        }
        original.call(graphics,
                //? if >=1.21.2 {
                pipeline,
                //?}
                sprite, x, y, width, height);
    }

    // items and their overlays are counter rotated so only the slot frames follow the element rotation
    @WrapOperation(
            //? if < 26 {
            /*method = "renderItemHotbar",
            *///?} else {
            method = "extractItemHotbar",
            //?}
            //? if >=26.2 {
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Hud;extractSlot(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V")
            //?} else if >=26 {
            /*at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;extractSlot(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V")
            *///?} else {
            /*at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V")
            *///?}
    )
    private void tgvanillahud$slot(
            //? if >=26.2 {
            Hud self,
            //?} else {
            /*Gui self,
            *///?}
            GuiGraphicsExtractor graphics, int x, int y, DeltaTracker deltaTracker,
            Player player, ItemStack stack, int seed, Operation<Void> original) {
        HotbarHud hud = Huds.INSTANCE.getHotbar();
        if (hud.isDrawnAsVanilla()) {
            original.call(self, graphics, x, y, deltaTracker, player, stack, seed);
            return;
        }
        HudTransform.beginUpright(graphics, hud,
                (float) x + VANILLAHUD$ITEM_HALF, (float) y + VANILLAHUD$ITEM_HALF);
        TextShadowHook.push(hud.getShowShadow());
        try {
            original.call(self, graphics, x, y, deltaTracker, player, hud.showsDemo() ? DemoData.demoHotbarItem(seed - 1) : stack, seed);
        } finally {
            TextShadowHook.pop();
        }
        HudTransform.endUpright(graphics, hud);
    }
    //?} else {
    /*@Unique private boolean tgvanillahud$active;
    @Unique private float tgvanillahud$animSlot;
    @Unique private boolean tgvanillahud$animInit;
    @Unique private long tgvanillahud$animNanos;

    // half an item icon so the counter rotation can pivot on the icon centre
    @Unique private static final float VANILLAHUD$ITEM_HALF = 8f;

    @WrapMethod(method = "renderHotbar")
    private void tgvanillahud$hotbar(Window window, float tickDelta, Operation<Void> original) {
        HotbarHud hud = Huds.INSTANCE.getHotbar();
        if (hud.isDrawnAsVanilla()) {
            original.call(window, tickDelta);
            return;
        }
        if (!hud.shouldDraw()) return;

        HudTransform.begin(LegacyDrawContext.INSTANCE, hud);
        tgvanillahud$setup(hud);
        original.call(window, tickDelta);
        tgvanillahud$active = false;
        HudTransform.end(LegacyDrawContext.INSTANCE);
    }

     // 1.8.9 has no delta tracker to ask, so the slide is paced off wall clock time converted
     // into ticks to match the easing the newer versions use.
    @Unique
    private void tgvanillahud$setup(HotbarHud hud) {
        Player player = Minecraft.getInstance().player;
        int selected = player == null || hud.showsDemo() ? 0 : player.inventory.selectedSlot;

        long now = System.nanoTime();
        float elapsedTicks = tgvanillahud$animNanos == 0L ? 0f : (now - tgvanillahud$animNanos) / 50_000_000f;
        tgvanillahud$animNanos = now;

        if (!tgvanillahud$animInit) {
            tgvanillahud$animSlot = (float) selected;
            tgvanillahud$animInit = true;
        }
        if (!hud.getAnimation()) {
            tgvanillahud$animSlot = (float) selected;
        } else {
            float t = Math.min(1f, elapsedTicks * 0.6f);
            tgvanillahud$animSlot += ((float) selected - tgvanillahud$animSlot) * t;
        }

        tgvanillahud$active = true;
    }

    // the second blit in renderHotbar is the selected slot frame
    @WrapOperation(
            method = "renderHotbar",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GameGui;drawTexture(IIIIII)V", ordinal = 1)
    )
    private void tgvanillahud$selection(GameGui self, int x, int y, int u, int v, int width, int height,
                                      Operation<Void> original) {
        if (tgvanillahud$active) {
            x = LegacyDrawContext.INSTANCE.guiWidth() / 2 - 92 + Math.round(tgvanillahud$animSlot * 20f);
        }
        original.call(self, x, y, u, v, width, height);
    }

    // items and their overlays are counter rotated so only the slot frames follow the rotation
    @WrapOperation(
            method = "renderHotbar",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GameGui;renderItemSlot(IIIFLnet/minecraft/world/entity/player/Player;)V")
    )
    private void tgvanillahud$slot(GameGui self, int slot, int x, int y, float tickDelta, Player player,
                                 Operation<Void> original) {
        HotbarHud hud = Huds.INSTANCE.getHotbar();
        if (hud.isDrawnAsVanilla()) {
            original.call(self, slot, x, y, tickDelta, player);
            return;
        }
        HudTransform.beginUpright(LegacyDrawContext.INSTANCE, hud,
                (float) x + VANILLAHUD$ITEM_HALF, (float) y + VANILLAHUD$ITEM_HALF);
        TextShadowHook.push(hud.getShowShadow());
        try {
            original.call(self, slot, x, y, tickDelta, player);
        } finally {
            TextShadowHook.pop();
        }
        HudTransform.endUpright(LegacyDrawContext.INSTANCE, hud);
    }
    *///?}
}
