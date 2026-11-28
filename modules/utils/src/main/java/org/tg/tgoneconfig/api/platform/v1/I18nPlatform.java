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

package org.tg.tgoneconfig.api.platform.v1;

public interface I18nPlatform {
    /**
     * Return a translatable component-like object for the current platform
     */
    default Object translate(String key, Object... args) {
        return translate(key, key, args);
    }
    /**
     * Return a translatable component-like object for the current platform
     */
    Object translate(String key, String fallback, Object... args);

    /**
     * Resolve a translation key using the currently selected language
     */
    default String translateString(String key, Object... args) {
        return key;
    }

    /**
     * Return true when the currently loaded language has a value for {@code key}
     */
    default boolean hasTranslation(String key) {
        return false;
    }

    /**
     * Return the given component with all formatting codes removed
     */
    String getUnformattedText(Object component);
}
