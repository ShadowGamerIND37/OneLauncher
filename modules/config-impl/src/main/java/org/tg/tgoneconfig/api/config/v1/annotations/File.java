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

@Option(display = Visualizer.FileVisualizer.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface File {
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

    /**
     * The file extensions to filter the dialog by such as {@code {".png", ".jpg"}}
     * <p>
     * Entries may be written as {@code ".png"} or {@code "png"} or {@code "*.png"} and they are all
     * normalised to the {@code *.ext} form {@code TinyFdApi} expects
     * <p>
     * Leave empty to allow any file
     */
    String[] types() default {};

    /**
     * A human-readable description for the {@link #types()} filter such as {@code "Images"}
     */
    String filterName() default "";

    /**
     * Select a directory instead of a file
     * <p>
     * When {@code true} the {@link #types()} value is ignored
     */
    boolean directory() default false;

    String category() default "General";

    @Deprecated
    String categoryKey() default "";

    /**
     * @deprecated TGOneConfig auto-translates category values that are translation keys so pass the key directly
     */
    @Deprecated
    boolean categoryTranslation() default false;

    String subcategory() default "General";

    @Deprecated
    String subcategoryKey() default "";

    /**
     * @deprecated translation keys will be translated by default
     */
    @Deprecated
    boolean subcategoryTranslation() default false;

    @TranslatedDefault("tgoneconfig.filepicker.placeholder")
    String placeholder() default "tgoneconfig.filepicker.placeholder";

    /**
     * @deprecated TGOneConfig auto-translates placeholder values that are translation keys so pass the key directly
     */
    @Deprecated
    boolean placeholderTranslation() default false;

    @Deprecated
    String placeholderKey() default "";

    boolean fullWidth() default false;
}
