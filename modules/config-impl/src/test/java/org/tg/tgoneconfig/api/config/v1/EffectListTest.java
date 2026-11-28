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

package org.tg.tgoneconfig.api.config.v1;

import org.junit.jupiter.api.Test;
import org.tg.tgoneconfig.api.config.v1.annotations.EffectList;
import org.tg.tgoneconfig.api.config.v1.collect.impl.TGOneConfigCollector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EffectListTest {
    @Test
    void collectsStringArrayAndListMetadata() {
        Tree tree = new TGOneConfigCollector().collect(new ValidEffectListConfig());

        Property<?> multiple = tree.getProp("multiple");
        assertEquals(Visualizer.EffectListVisualizer.class, multiple.getMetadata("visualizer"));
        assertEquals(Integer.valueOf(0), multiple.getMetadata("maxEntries"));
        assertEquals("tgoneconfig.effectlist.add", multiple.getMetadata("addTextKey"));
        assertEquals(false, multiple.getMetadata("fullWidth"));

        Property<?> single = tree.getProp("single");
        assertEquals(Integer.valueOf(1), single.getMetadata("maxEntries"));
        assertEquals(false, single.getMetadata("reorderable"));
        assertEquals(true, single.getMetadata("fullWidth"));
    }

    @Test
    void rejectsAListWithTheWrongElementType() {
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> new TGOneConfigCollector().collect(new InvalidElementConfig())
        );
        assertTrue(hasMessage(exception, "@EffectList field 'invalid' must be a String[] or List<String>"));
    }

    @Test
    void rejectsNegativeLimits() {
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> new TGOneConfigCollector().collect(new InvalidLimitConfig())
        );
        assertTrue(hasMessage(exception, "@EffectList field 'invalid' has a negative maxEntries"));
    }

    private static boolean hasMessage(Throwable throwable, String text) {
        for (Throwable current = throwable; current != null; current = current.getCause()) {
            if (current.getMessage() != null && current.getMessage().contains(text)) return true;
        }
        return false;
    }

    private static final class ValidEffectListConfig extends Config {
        @EffectList(title = "Effects")
        private String[] multiple = {"minecraft:speed"};

        @EffectList(title = "Effect", maxEntries = 1, reorderable = false, fullWidth = true)
        private List<String> single = new ArrayList<>(List.of("minecraft:night_vision"));

        private ValidEffectListConfig() {
            super("effect-list-test.json", "Effect List Test", Category.OTHER);
        }
    }

    private static final class InvalidElementConfig extends Config {
        @EffectList(title = "Invalid")
        private List<Integer> invalid = List.of(1);

        private InvalidElementConfig() {
            super("invalid-effect-list-test.json", "Invalid Effect List Test", Category.OTHER);
        }
    }

    private static final class InvalidLimitConfig extends Config {
        @EffectList(title = "Invalid", maxEntries = -1)
        private String[] invalid = {};

        private InvalidLimitConfig() {
            super("invalid-effect-limit-test.json", "Invalid Effect Limit Test", Category.OTHER);
        }
    }
}
