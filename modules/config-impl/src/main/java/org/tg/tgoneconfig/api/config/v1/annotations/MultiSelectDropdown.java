/*
 * This file is part of TGOneConfig.
 * TGOneConfig - Next Generation Config Library for Minecraft: Java Edition
 * Copyright (C) 2021~2024 TG.
 *   <https://github.com/ShadowGamerIND37> <https://github.com/TG/>
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 *   TGOneConfig is licensed under the terms of version 3 of the GNU Lesser
 * General Public License as published by the Free Software Foundation, AND
 * under the Additional Terms Applicable to TGOneConfig, as published by TG,
 * either version 1.0 of the Additional Terms, or (at your option) any later
 * version.
 *
 *   This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 * License.  If not, see <https://www.gnu.org/licenses/>. You should
 * have also received a copy of the Additional Terms Applicable
 * to TGOneConfig, as published by TG. If not, see
 * <https://github.com/ShadowGamerIND37/legal/tgoneconfig/additional-terms>
 */

package org.tg.tgoneconfig.api.config.v1.annotations;

import org.tg.tgoneconfig.api.config.v1.Visualizer;

import java.lang.annotation.*;

/**
 * Renders a dropdown that opens a selectable list
 * <p>
 * When {@code checkable = true} which is the default multiple items can be selected and the
 * annotated field must be {@code boolean[]} indexed by option position
 * <p>
 * When {@code checkable = false} only one item can be selected and the annotated field must
 * be {@code int} holding the selected index or -1 for none
 */
@Option(display = Visualizer.MultiSelectDropdownVisualizer.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface MultiSelectDropdown {
    String title();

    /**
     * @deprecated TGOneConfig auto-translates title values that are translation keys so pass the key directly
     */
    @Deprecated
    boolean titleTranslation() default false;

    @Deprecated
    String titleKey() default "";

    String description() default "";

    /**
     * @deprecated TGOneConfig auto-translates description values that are translation keys so pass the key directly
     */
    @Deprecated
    boolean descriptionTranslation() default false;

    @Deprecated
    String descriptionKey() default "";

    String icon() default "";

    String category() default "General";

    /**
     * @deprecated TGOneConfig auto-translates category values that are translation keys so pass the key directly
     */
    @Deprecated
    boolean categoryTranslation() default false;

    @Deprecated
    String categoryKey() default "";

    String subcategory() default "General";

    /**
     * @deprecated translation keys will be translated by default
     */
    @Deprecated
    boolean subcategoryTranslation() default false;

    @Deprecated
    String subcategoryKey() default "";

    String[] options() default {};

    /**
     * @deprecated TGOneConfig auto-translates options values that are translation keys so pass the key directly
     */
    @Deprecated
    boolean optionsTranslation() default false;

    @Deprecated
    String[] optionsKey() default {};

    /** When true which is the default it renders checkboxes for multi-select and when false a single-select list */
    boolean checkable() default true;
}
