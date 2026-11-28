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

import org.tg.tgoneconfig.api.config.v1.ValueFormatter;
import org.tg.tgoneconfig.api.config.v1.Visualizer;

import java.lang.annotation.*;

/**
 * A start and end pair within {@link #min()} and {@link #max()} edited with two number inputs
 * <br>
 * This is {@link RangeSlider} without the track so it suits ranges too wide to drag across
 * <br>
 * The annotated field holds both ends together as a two-element numeric array or list
 * such as {@code float[]} or {@code int[]} or {@code List<Integer>} where index 0 is the
 * start and index 1 is the end
 * <br>
 * Values are written back in whatever container shape the field already holds so its type is preserved
 */
@Option(display = Visualizer.RangeNumberVisualizer.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface RangeNumber {
    String title();

    String description() default "";

    String icon() default "";

    String category() default "General";

    String subcategory() default "General";

    float min() default 0f;

    float max() default 100f;

    /** Amount the arrows on each input step by */
    float step() default 1f;

    Class<? extends ValueFormatter> formatter() default ValueFormatter.class;

}
