package org.tg.tgoneconfig.internal.utils;

//? moul_compat {
import io.github.notenoughupdates.moulconfig.Config;
import org.tg.tgoneconfig.relocator.annotations.MoulConfig;

@MoulConfig
public interface MoulConfigProcessorAccessor<T extends Config> {

    T tgoneconfig$getConfig();

}
//? }