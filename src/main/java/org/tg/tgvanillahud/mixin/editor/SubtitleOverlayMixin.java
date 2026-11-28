package org.tg.tgvanillahud.mixin.editor;

//? if > 1.8.9 {
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.audio.ListenerTransform;
import net.minecraft.client.Minecraft;
//? if > 1.8.9
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.SubtitleOverlay;
import org.tg.tgvanillahud.hud.Huds;
import org.tg.tgvanillahud.mixin.access.ISubtitleOverlay;
import org.tg.tgvanillahud.util.DemoData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.ArrayList;
import java.util.List;

@Mixin(SubtitleOverlay.class)
public abstract class SubtitleOverlayMixin {
    @Unique
    private static boolean tgvanillahud$editing() {
        return Huds.INSTANCE.getClosedCaptions().getPreviewing();
    }

    @Unique
    private boolean tgvanillahud$forcedSubtitles;

    @WrapMethod(
            //? if <26 {
            /*method = "render"
            *///?} else {
            method = "extractRenderState"
            //?}
    )
    private void tgvanillahud$forceSubtitles(GuiGraphicsExtractor graphics, Operation<Void> original) {
        ISubtitleOverlay overlay = (ISubtitleOverlay) (Object) this;
        ListenerTransform transform = Minecraft.getInstance().getSoundManager().getListenerTransform();
        if (!tgvanillahud$editing() || transform == null) {
            if (tgvanillahud$forcedSubtitles) {
                overlay.getAudibleSubtitles().clear();
                tgvanillahud$forcedSubtitles = false;
            }
            original.call(graphics);
            return;
        }

        tgvanillahud$forcedSubtitles = true;
        List<Object> subtitles = overlay.getSubtitles();
        List<Object> real = new ArrayList<>(subtitles);
        subtitles.clear();
        SubtitleOverlay self = (SubtitleOverlay) (Object) this;
        for (DemoData.DemoSubtitle demo : DemoData.INSTANCE.demoSubtitles(transform.position(), transform.forward(), transform.right())) {
            self.onPlaySound(demo.getSound(), demo.getEvent(), demo.getRange());
        }
        original.call(graphics);
        subtitles.clear();
        subtitles.addAll(real);
    }

    @ModifyExpressionValue(
            //? if <26 {
            /*method = "render",
            *///?} else {
            method = "extractRenderState",
            //?}
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;"),
            slice = @Slice(to = @At(value = "INVOKE", target = "Lnet/minecraft/client/sounds/SoundManager;getListenerTransform()Lcom/mojang/blaze3d/audio/ListenerTransform;"))
    )
    private Object tgvanillahud$forceSubtitleOption(Object original) {
        return tgvanillahud$editing() ? Boolean.TRUE : original;
    }
}
//?} else {
/*// 1.8.9 has no subtitles to preview
import net.minecraft.client.gui.GameGui;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GameGui.class)
public abstract class SubtitleOverlayMixin {
}
*///?}
