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

import org.tg.tgoneconfig.api.event.v1.EventManager;

/**
 * Called when the JVM is shutting down
 * <br>
 * This is called in a {@link Runtime#addShutdownHook(Thread) shutdown hook}
 *
 * @see ShutdownEvent
 */
public class JvmShutdownEvent implements Event {
    static {
        // <clinit> will be called if something wants to reference this
        Runtime.getRuntime().addShutdownHook(new Thread(() -> EventManager.INSTANCE.post(new JvmShutdownEvent())));
    }

    private JvmShutdownEvent() {
    }
}
