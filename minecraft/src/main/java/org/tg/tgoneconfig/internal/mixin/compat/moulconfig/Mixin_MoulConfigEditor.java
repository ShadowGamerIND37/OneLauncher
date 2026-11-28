package org.tg.tgoneconfig.internal.mixin.compat.moulconfig;

//? moul_compat {
import io.github.notenoughupdates.moulconfig.Config;
import io.github.notenoughupdates.moulconfig.gui.MoulConfigEditor;
import io.github.notenoughupdates.moulconfig.processor.ProcessedCategory;
import java.util.LinkedHashMap;
import org.tg.tgoneconfig.internal.compat.MoulConfigCompat;
import org.tg.tgoneconfig.relocator.annotations.MoulConfig;
import org.tg.tgoneconfig.relocator.annotations.RelocatedMixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@MoulConfig
@RelocatedMixin
@Mixin(value = MoulConfigEditor.class, remap = false)
public class Mixin_MoulConfigEditor<T extends Config> {
    @Shadow
    @Final
    private LinkedHashMap<String, ProcessedCategory> allCategories;

    @Shadow
    @Final
    private T configObject;

    @Inject(at = @At("TAIL"), method = "<init>(Ljava/util/LinkedHashMap;Lio/github/notenoughupdates/moulconfig/Config;)V", require = 0)
    public void tgoneconfig$init(CallbackInfo ci) {
        MoulConfigCompat.parseMoulconfigFromEditor(this.allCategories.values(), this.configObject);
    }
}
//? }