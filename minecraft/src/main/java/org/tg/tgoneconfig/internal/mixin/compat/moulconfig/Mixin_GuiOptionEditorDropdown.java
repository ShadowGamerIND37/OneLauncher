package org.tg.tgoneconfig.internal.mixin.compat.moulconfig;

//? moul_compat {
import io.github.notenoughupdates.moulconfig.gui.editors.GuiOptionEditorDropdown;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import org.tg.tgoneconfig.internal.utils.MoulConfigGuiOptionEditorDropdownAccessor;
import org.tg.tgoneconfig.relocator.annotations.MoulConfig;
import org.tg.tgoneconfig.relocator.annotations.RelocatedMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Pseudo
@MoulConfig
@RelocatedMixin
@Mixin(value = GuiOptionEditorDropdown.class, remap = false)
public class Mixin_GuiOptionEditorDropdown implements MoulConfigGuiOptionEditorDropdownAccessor {
    @Shadow
    private boolean useOrdinal;
    @Shadow
    private Enum<?>[] constants;

    @Unique
    private String[] tgoneconfig$resolvedValues;

    // MoulConfig 3.x uses String[] for this field and 4.x uses List<StructuredText>
    // reflection avoids the @Shadow descriptor mismatch that breaks mixin application
    public String[] tgoneconfig$values() {
        if (tgoneconfig$resolvedValues != null) return tgoneconfig$resolvedValues;
        try {
            Field f = this.getClass().getDeclaredField("values");
            f.setAccessible(true);
            Object val = f.get(this);
            if (val instanceof String[]) {
                tgoneconfig$resolvedValues = (String[]) val;
            } else if (val instanceof List<?>) {
                List<?> list = (List<?>) val;
                String[] result = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    Object item = list.get(i);
                    if (item instanceof String) {
                        result[i] = (String) item;
                    } else {
                        try {
                            Method getText = item.getClass().getMethod("getText");
                            result[i] = (String) getText.invoke(item);
                        } catch (Exception e) {
                            result[i] = item.toString();
                        }
                    }
                }
                tgoneconfig$resolvedValues = result;
            } else {
                tgoneconfig$resolvedValues = new String[0];
            }
        } catch (Exception e) {
            tgoneconfig$resolvedValues = new String[0];
        }
        return tgoneconfig$resolvedValues;
    }

    public boolean tgoneconfig$useOrdinal() {
        return this.useOrdinal;
    }

    public Enum<?>[] tgoneconfig$constants() {
        return this.constants;
    }

}
//? }