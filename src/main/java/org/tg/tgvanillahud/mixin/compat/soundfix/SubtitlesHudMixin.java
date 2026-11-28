package org.tg.tgvanillahud.mixin.compat.soundfix;

//? if = 1.8.9 {
/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.tg.tgvanillahud.compat.LegacyDrawContext;
import org.tg.tgvanillahud.compat.SoundFixCaptions;
import org.tg.tgvanillahud.hook.TextShadowHook;
import org.tg.tgvanillahud.hud.ClosedCaptionsHud;
import org.tg.tgvanillahud.hud.Huds;
import org.tg.tgvanillahud.render.HudTransform;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = "io.github.moehreag.soundfix.subtitles.SubtitlesHud", remap = false)
public abstract class SubtitlesHudMixin {
    @WrapMethod(method = "render")
    private void tgvanillahud$place(Operation<Void> original) {
        ISubtitlesHud self = (ISubtitlesHud) this;
        SoundFixCaptions.attach(self);
        ClosedCaptionsHud hud = Huds.INSTANCE.getClosedCaptions();
        if (hud.isDrawnAsVanilla()) {
            SoundFixCaptions.render(self, false, original::call);
            return;
        }
        if (!hud.shouldDraw()) return;

        HudTransform.begin(LegacyDrawContext.INSTANCE, hud);
        TextShadowHook.push(hud.getTextShadow());
        try {
            SoundFixCaptions.render(self, hud.getPreviewing(), original::call);
        } finally {
            TextShadowHook.pop();
            HudTransform.end(LegacyDrawContext.INSTANCE);
        }
    }

    @ModifyExpressionValue(
            method = "render",
            at = @At(value = "INVOKE", target = "Lio/github/moehreag/soundfix/util/ARGB;colorFromFloat(FFFF)I")
    )
    private int tgvanillahud$background(int original) {
        ClosedCaptionsHud hud = Huds.INSTANCE.getClosedCaptions();
        return hud.isDrawnAsVanilla() ? original : hud.getCaptionBgArgb();
    }

    @ModifyExpressionValue(
            method = "render",
            at = @At(value = "INVOKE", target = "Lio/github/moehreag/soundfix/util/ARGB;color(IIII)I")
    )
    private int tgvanillahud$text(int original) {
        ClosedCaptionsHud hud = Huds.INSTANCE.getClosedCaptions();
        return hud.isDrawnAsVanilla() ? original : hud.captionTextArgb(original);
    }
}
*///?}
