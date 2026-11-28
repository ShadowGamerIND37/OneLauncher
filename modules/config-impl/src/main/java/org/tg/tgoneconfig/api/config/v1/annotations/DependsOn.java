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

import org.tg.tgoneconfig.api.config.v1.Config;
import org.tg.tgoneconfig.api.config.v1.Property;

import java.lang.annotation.*;
import java.util.function.Supplier;

/**
 * Annotation used to specify conditions for this property
 * <br>
 * Apply this annotation to a field or button method
 * <br>
 * The value contains an array of strings with each string being the fully qualified path using dots '.' to the condition property
 * <br>
 * Any property in the array must be a {@link Boolean}
 * <br><br>
 * For complex conditions use {@link Config#addDependency(String, String, Supplier)} or {@link Property#addDisplayCondition(Supplier...)}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Documented
public @interface DependsOn {
    String[] value();
}
