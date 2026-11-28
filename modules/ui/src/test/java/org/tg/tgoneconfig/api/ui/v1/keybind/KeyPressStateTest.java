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

package org.tg.tgoneconfig.api.ui.v1.keybind;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KeyPressStateTest {
    private static final long THRESHOLD_MS = 300L;
    private static final long MS = 1_000_000L;

    @Test
    void heldIsActiveOnlyWhileDown() {
        KeyPressState state = new KeyPressState();
        assertTrue(state.input(true, KeyPressMode.Held, THRESHOLD_MS, 0L));
        assertTrue(state.getActive());
        assertFalse(state.input(true, KeyPressMode.Held, THRESHOLD_MS, 10L * MS));
        assertTrue(state.input(false, KeyPressMode.Held, THRESHOLD_MS, 20L * MS));
        assertFalse(state.getActive());
    }

    @Test
    void toggleFlipsOnEachPress() {
        KeyPressState state = new KeyPressState();
        state.input(true, KeyPressMode.Toggle, THRESHOLD_MS, 0L);
        state.input(false, KeyPressMode.Toggle, THRESHOLD_MS, 1000L * MS);
        assertTrue(state.getActive());
        state.input(true, KeyPressMode.Toggle, THRESHOLD_MS, 2000L * MS);
        state.input(false, KeyPressMode.Toggle, THRESHOLD_MS, 2010L * MS);
        assertFalse(state.getActive());
    }

    @Test
    void quickPressTapActsAsToggle() {
        KeyPressState state = new KeyPressState();
        state.input(true, KeyPressMode.QuickPress, THRESHOLD_MS, 0L);
        state.input(false, KeyPressMode.QuickPress, THRESHOLD_MS, 100L * MS);
        assertTrue(state.getActive());
        state.input(true, KeyPressMode.QuickPress, THRESHOLD_MS, 500L * MS);
        state.input(false, KeyPressMode.QuickPress, THRESHOLD_MS, 600L * MS);
        assertFalse(state.getActive());
    }

    @Test
    void quickPressHoldReturnsToStateBeforePress() {
        KeyPressState state = new KeyPressState();
        state.input(true, KeyPressMode.QuickPress, THRESHOLD_MS, 0L);
        assertTrue(state.getActive());
        state.input(false, KeyPressMode.QuickPress, THRESHOLD_MS, 400L * MS);
        assertFalse(state.getActive());

        state.input(true, KeyPressMode.QuickPress, THRESHOLD_MS, 1000L * MS);
        state.input(false, KeyPressMode.QuickPress, THRESHOLD_MS, 1050L * MS);
        assertTrue(state.getActive());
        state.input(true, KeyPressMode.QuickPress, THRESHOLD_MS, 2000L * MS);
        assertFalse(state.getActive());
        state.input(false, KeyPressMode.QuickPress, THRESHOLD_MS, 2500L * MS);
        assertTrue(state.getActive());
    }

    @Test
    void quickPressAtThresholdIsStillATap() {
        KeyPressState state = new KeyPressState();
        state.input(true, KeyPressMode.QuickPress, THRESHOLD_MS, 0L);
        state.input(false, KeyPressMode.QuickPress, THRESHOLD_MS, THRESHOLD_MS * MS);
        assertTrue(state.getActive());
    }

    @Test
    void releaseOfPressFromAnotherModeIsNotAHold() {
        KeyPressState state = new KeyPressState();
        state.input(true, KeyPressMode.Toggle, THRESHOLD_MS, 0L);
        state.input(false, KeyPressMode.QuickPress, THRESHOLD_MS, 5000L * MS);
        assertTrue(state.getActive());
    }

    @Test
    void resetForgetsPressInProgress() {
        KeyPressState state = new KeyPressState();
        state.input(true, KeyPressMode.QuickPress, THRESHOLD_MS, 0L);
        assertTrue(state.reset());
        assertFalse(state.getActive());
        assertFalse(state.isDown());
        state.input(false, KeyPressMode.QuickPress, THRESHOLD_MS, 5000L * MS);
        assertFalse(state.getActive());
    }
}
