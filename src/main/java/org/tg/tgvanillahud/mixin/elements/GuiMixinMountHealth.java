package org.tg.tgvanillahud.mixin.elements;

import org.spongepowered.asm.mixin.Mixin;

//? if <1.21.6 && >1.8.9 {
/*import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.tg.tgvanillahud.hud.Huds;
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
public class GuiMixinMountHealth {
    //? if <1.21.6 && >1.8.9 {
    /*@WrapMethod(method = "renderVehicleHealth")
    private void tgvanillahud$mount(GuiGraphicsExtractor graphics, Operation<Void> original) {
        if (!Huds.INSTANCE.getHunger().shouldDraw()) return;

        HudTransform.beginIcons(graphics, Huds.INSTANCE.getHunger());
        original.call(graphics);
        HudTransform.endIcons(graphics);
    }
    *///?}
}
