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
 * Renders a user-editable list of file (or directory) pickers
 * <p>
 * The annotated field must be {@code String[]} or a {@code List<String>} with each entry holding one path
 * <p>
 * This is the list counterpart of {@link File}
 * <p>
 * Rows can be added and removed and dragged into a different order
 * <p>
 * The property value is the list of paths in display order
 */
@Option(display = Visualizer.FileListVisualizer.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface FileList {
    String title();

    /**
     * @deprecated TGOneConfig auto-translates title values that are translation keys so pass the key directly
     */
    @Deprecated
    boolean titleTranslation() default false;

    String description() default "";

    /**
     * @deprecated TGOneConfig auto-translates description values that are translation keys so pass the key directly
     */
    @Deprecated
    boolean descriptionTranslation() default false;

    String icon() default "";

    String category() default "General";

    /**
     * @deprecated TGOneConfig auto-translates category values that are translation keys so pass the key directly
     */
    @Deprecated
    boolean categoryTranslation() default false;

    String subcategory() default "General";

    /**
     * @deprecated translation keys will be translated by default
     */
    @Deprecated
    boolean subcategoryTranslation() default false;

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
     * Select directories instead of files
     * <p>
     * When {@code true} the {@link #types()} value is ignored
     */
    boolean directory() default false;

    @TranslatedDefault("tgoneconfig.filepicker.placeholder")
    String placeholder() default "tgoneconfig.filepicker.placeholder";

    /**
     * @deprecated TGOneConfig auto-translates placeholder values that are translation keys so pass the key directly
     */
    @Deprecated
    boolean placeholderTranslation() default false;

    /** Maximum number of entries the user may add and {@code 0} means unlimited */
    int maxEntries() default 0;

    /** Allow the user to drag entries to reorder them */
    boolean reorderable() default true;

    /** Label of the button that appends a new entry */
    String addText() default "Add";

    /**
     * @deprecated TGOneConfig auto-translates addText values that are translation keys so pass the key directly
     */
    @Deprecated
    boolean addTextTranslation() default false;

    boolean fullWidth() default false;
}
