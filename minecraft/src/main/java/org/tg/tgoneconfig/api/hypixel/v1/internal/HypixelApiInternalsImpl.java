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

package org.tg.tgoneconfig.api.hypixel.v1.internal;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.hypixel.modapi.HypixelModAPI;
import net.hypixel.modapi.packet.impl.clientbound.ClientboundHelloPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;
import org.tg.tgoneconfig.api.event.v1.EventManager;
import org.tg.tgoneconfig.api.event.v1.events.HypixelLocationEvent;

@ApiStatus.Internal
public final class HypixelApiInternalsImpl implements HypixelApiInternals {
    private static final Logger LOGGER = LogManager.getLogger("TG OneConfig/HypixelAPI");

    private volatile boolean onHypixel = false;

    public HypixelApiInternalsImpl() {
        LOGGER.info("Registering Hypixel API hello/disconnect handlers");
        HypixelModAPI.getInstance().createHandler(ClientboundHelloPacket.class, p -> onHypixel = true);
        //? if > 1.8.9 {
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> onHypixel = false);
        //?} else
        //ClientConnectionEvents.DISCONNECT.register(context -> onHypixel = false);
    }

    @Override
    public boolean isOnHypixel() {
        return onHypixel;
    }

    @Override
    @ApiStatus.Internal
    public void postLocationEvent() {
        EventManager.INSTANCE.post(HypixelLocationEvent.INSTANCE);
    }
}
