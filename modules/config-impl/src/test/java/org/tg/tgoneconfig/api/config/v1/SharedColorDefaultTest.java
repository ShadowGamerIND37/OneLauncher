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
import org.polyfrost.compose.render.PolyColor;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@code Property.Field#set0} mutates complex values in place so using the same PolyColor constant
 * as the default of several options must not make a change to one option leak into the others
 * <br>
 * Every access to a named colour hands out its own instance
 */
public class SharedColorDefaultTest {

    static class Bean {
        public PolyColor first = PolyColor.Companion.getWHITE();
        public PolyColor second = PolyColor.Companion.getWHITE();
    }

    @Test
    void settingOneOptionDoesNotChangeSiblingsSharingTheSameDefault() throws Exception {
        Bean b = new Bean();
        Property<?> first = Properties.field("first", null, Bean.class.getDeclaredField("first"), b);

        first.setAs(PolyColor.Companion.rgba(255, 0, 0, 255));

        assertEquals(0xFFFF0000, b.first.getRawArgb());
        assertEquals(0xFFFFFFFF, b.second.getRawArgb());
        assertEquals(0xFFFFFFFF, PolyColor.Companion.getWHITE().getRawArgb());
    }

    @Test
    void chromaFlagIsNotLeakedOntoSiblingsSharingTheSameDefault() throws Exception {
        Bean b = new Bean();
        Property<?> first = Properties.field("first", null, Bean.class.getDeclaredField("first"), b);

        first.setAs(PolyColor.Companion.getWHITE().withChroma(true, 2f));

        assertTrue(b.first.getChroma());
        assertFalse(b.second.getChroma());
        assertFalse(PolyColor.Companion.getWHITE().getChroma());
    }

    @Test
    void namedColoursHandOutTheirOwnInstance() {
        assertNotSame(PolyColor.Companion.getWHITE(), PolyColor.Companion.getWHITE());
    }

    @Test
    void copyDefaultCopiesComplexValuesAndPassesSimpleOnesThrough() {
        PolyColor colour = PolyColor.Companion.getWHITE();
        assertNotSame(colour, Config.copyDefault(PolyColor.class, colour));

        String simple = "text";
        assertSame(simple, Config.copyDefault(String.class, simple));
    }
}
