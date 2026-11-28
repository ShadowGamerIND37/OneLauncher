package org.tg.tgvanillahud.mixin;

//? if = 1.8.9 {
/*import net.minecraft.client.render.texture.TextureManager;
import net.minecraft.client.resource.manager.ResourceManager;
import net.minecraft.resources.ResourceLocation;
import org.tg.tgvanillahud.render.HudCapture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextureManager.class)
public abstract class CaptureTextureMixin {
    @Inject(method = "bind", at = @At("HEAD"))
    private void tgvanillahud$bind(ResourceLocation id, CallbackInfo ci) {
        HudCapture.bind(id);
    }

    @Inject(method = "reload", at = @At("TAIL"))
    private void tgvanillahud$reload(ResourceManager manager, CallbackInfo ci) {
        HudCapture.resourcesReloaded();
    }
}
*///?} else {
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

// only 1.8.9 needs this hook, see DrawCaptureMixin
@Mixin(Minecraft.class)
public abstract class CaptureTextureMixin {
}
//?}
