package org.tg.tgoneconfig.internal.utils;

//? moul_compat {
import org.tg.tgoneconfig.relocator.annotations.MoulConfig;

@MoulConfig
public interface MoulConfigGuiOptionEditorDropdownAccessor {

    String[] tgoneconfig$values();

    boolean tgoneconfig$useOrdinal();

    Enum<?>[] tgoneconfig$constants();

}
//? }
