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

package org.tg.tgoneconfig.api.event.v1.events;

import org.jetbrains.annotations.ApiStatus;
import org.tg.tgoneconfig.api.event.v1.EventManager;

public class MouseInputEvent implements Event {
    public static final int RELEASED = 0;
    public static final int PRESSED = 1;

    public final int button;
    /** {@link #RELEASED} or {@link #PRESSED}. */
    public final int state;

    public MouseInputEvent(int button, int state) {
        this.button = button;
        this.state = state;
    }

    public int component1() {
        return button;
    }

    public int component2() {
        return state;
    }

    /**
     * This event is only fired when the mouse is moved inside a screen
     * <br>
     * The provided coordinates are SCREEN coordinates and not minecraft-specific ones
     */
    public static final class Moved implements Event {
        private static final Moved INSTANCE = new Moved();
        private float x, y;

        private Moved() {}

        @ApiStatus.Internal
        public static void post(float x, float y) {
            INSTANCE.x = x;
            INSTANCE.y = y;
            EventManager.INSTANCE.post(INSTANCE);
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float component1() {
            return x;
        }

        public float component2() {
            return y;
        }
    }
}
