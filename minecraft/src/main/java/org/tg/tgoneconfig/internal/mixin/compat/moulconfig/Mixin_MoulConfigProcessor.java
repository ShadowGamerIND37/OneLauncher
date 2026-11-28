package org.tg.tgoneconfig.internal.mixin.compat.moulconfig;

//? moul_compat {
import io.github.notenoughupdates.moulconfig.Config;
import io.github.notenoughupdates.moulconfig.processor.MoulConfigProcessor;
import org.tg.tgoneconfig.internal.utils.MoulConfigProcessorAccessor;
import org.tg.tgoneconfig.relocator.annotations.MoulConfig;
import org.tg.tgoneconfig.relocator.annotations.RelocatedMixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;

@Pseudo
@MoulConfig
@RelocatedMixin
@Mixin(value = MoulConfigProcessor.class, remap = false)
public class Mixin_MoulConfigProcessor<T extends Config> implements MoulConfigProcessorAccessor<T> {

    @Final
    @Shadow
    private T configBaseObject;

    public T tgoneconfig$getConfig() {
        return this.configBaseObject;
    }

}
//? }
