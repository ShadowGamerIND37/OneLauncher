package org.tg.tgvanillahud.mixin;

//? if = 1.8.9 {
/*import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.gui.Font;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import org.tg.tgvanillahud.compat.LegacyDrawContext;
import org.tg.tgvanillahud.render.HudCapture;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemRenderer.class)
public abstract class CaptureItemMixin {
    @WrapMethod(method = "renderGuiItem")
    private void tgvanillahud$item(ItemStack stack, int x, int y, Operation<Void> original) {
        if (stack != null && HudCapture.item(LegacyDrawContext.INSTANCE, stack, x, y)) return;
        original.call(stack, x, y);
    }

    @WrapMethod(method = "renderGuiItemDecorations")
    private void tgvanillahud$decorations(Font font, ItemStack stack, int x, int y, String text, Operation<Void> original) {
        if (HudCapture.itemDecorations()) return;
        original.call(font, stack, x, y, text);
    }
}
*///?} else {
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

// only 1.8.9 needs this hook, see DrawCaptureMixin
@Mixin(Minecraft.class)
public abstract class CaptureItemMixin {
}
//?}
