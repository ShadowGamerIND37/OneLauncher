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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Renders a searchable selector for status effects (potion effects)
 * <p>
 * The annotated field must be a {@code String[]} or {@code List<String>} containing
 * namespaced effect registry IDs for example {@code minecraft:speed}
 * <p>
 * Selected entries are ordered and can be removed and may be reordered
 * <p>
 * Use {@link #maxEntries()} with a value of {@code 1} for a single-effect selector
 */
@Option(display = Visualizer.EffectListVisualizer.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface EffectList {
    String title();

    String description() default "";

    String icon() default "";

    String category() default "General";

    String subcategory() default "General";

    /** Maximum number of selected effects and {@code 0} means unlimited */
    int maxEntries() default 0;

    /** Allow the user to drag selected effects into a different order */
    boolean reorderable() default true;

    /** Label of the button that opens the effect selector */
    @TranslatedDefault("tgoneconfig.effectlist.add")
    String addText() default "tgoneconfig.effectlist.add";

    boolean fullWidth() default false;
}
