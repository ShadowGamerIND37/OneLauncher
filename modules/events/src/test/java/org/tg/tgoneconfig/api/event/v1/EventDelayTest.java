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

package org.tg.tgoneconfig.api.event.v1;

import org.junit.jupiter.api.Test;
import org.tg.tgoneconfig.api.event.v1.events.TickEvent;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventDelayTest {
    @Test
    void functionRepostingSameEventRunsOnce() {
        AtomicInteger runs = new AtomicInteger();
        EventDelay.tick(1, () -> {
            if (runs.incrementAndGet() < 10) EventManager.INSTANCE.post(TickEvent.End.INSTANCE);
        });
        EventManager.INSTANCE.post(TickEvent.End.INSTANCE);
        EventManager.INSTANCE.post(TickEvent.End.INSTANCE);
        assertEquals(1, runs.get());
    }
}
