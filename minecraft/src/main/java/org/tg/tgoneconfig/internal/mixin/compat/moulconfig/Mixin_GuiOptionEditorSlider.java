package org.tg.tgoneconfig.internal.mixin.compat.moulconfig;

//? moul_compat {
import io.github.notenoughupdates.moulconfig.gui.editors.GuiOptionEditorSlider;
import io.github.notenoughupdates.moulconfig.processor.ProcessedOption;
import org.tg.tgoneconfig.internal.compat.GuiOptionEditorSliderAccessor;
import org.tg.tgoneconfig.relocator.annotations.MoulConfig;
import org.tg.tgoneconfig.relocator.annotations.RelocatedMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@MoulConfig
@RelocatedMixin
@Mixin(value = GuiOptionEditorSlider.class, remap = false)
public class Mixin_GuiOptionEditorSlider implements GuiOptionEditorSliderAccessor {

    @Unique
    private float tgoneconfig$minValue;
    @Unique
    private float tgoneconfig$maxValue;
    @Unique
    private float tgoneconfig$minStep;

    @Inject(method = "<init>", at = @At("RETURN"), require = 0)
    public void meow(ProcessedOption option, float minValue, float maxValue, float minStep, CallbackInfo ci) {
        this.tgoneconfig$minValue = minValue;
        this.tgoneconfig$maxValue = maxValue;
        this.tgoneconfig$minStep = minStep;
    }

    @Override
    public float getOneconfig$minValue() {
        return tgoneconfig$minValue;
    }

    @Override
    public float getOneconfig$maxValue() {
        return tgoneconfig$maxValue;
    }

    @Override
    public float getOneconfig$minStep() {
        return tgoneconfig$minStep;
    }
}
//? }