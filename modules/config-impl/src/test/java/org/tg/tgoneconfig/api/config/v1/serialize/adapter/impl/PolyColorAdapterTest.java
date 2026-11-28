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

package org.tg.tgoneconfig.api.config.v1.serialize.adapter.impl;

import org.junit.jupiter.api.Test;
import org.tg.compose.render.PolyColor;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PolyColorAdapterTest {
    private final PolyColorAdapter adapter = new PolyColorAdapter();

    @Test
    public void serializesStaticColorsAsLegacyArrays() {
        PolyColor color = PolyColor.Companion.rgba(20, 55, 3, 100);

        Object serialized = adapter.serialize(color);

        assertInstanceOf(int[].class, serialized);
        assertArrayEquals(new int[]{20, 55, 3, 100}, (int[]) serialized);
        PolyColor deserialized = adapter.deserialize(serialized);
        assertEquals(color.getRawArgb(), deserialized.getRawArgb());
        assertFalse(deserialized.getChroma());
    }

    @Test
    public void roundTripsChromaMetadata() {
        PolyColor color = new PolyColor(0x64143703, true, 2.5f);

        Object serialized = adapter.serialize(color);

        assertInstanceOf(Map.class, serialized);
        Map<?, ?> map = (Map<?, ?>) serialized;
        assertArrayEquals(new int[]{20, 55, 3, 100}, (int[]) map.get("rgba"));
        assertEquals(true, map.get("chroma"));
        assertEquals(2.5f, (Float) map.get("chromaSpeed"));

        PolyColor deserialized = adapter.deserialize(serialized);
        assertEquals(color.getRawArgb(), deserialized.getRawArgb());
        assertTrue(deserialized.getChroma());
        assertEquals(2.5f, deserialized.getChromaSpeed());
    }

    @Test
    public void deserializesPackedArgbInts() {
        PolyColor deserialized = adapter.deserialize(-12566464);
        assertEquals(PolyColor.Companion.rgba(64, 64, 64, 255).getRawArgb(), deserialized.getRawArgb());
        assertFalse(deserialized.getChroma());
    }
}
