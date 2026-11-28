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

package org.tg.tgoneconfig.api.notifications.v1

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.tg.tgoneconfig.api.event.v1.EventManager
import org.tg.tgoneconfig.api.event.v1.events.MouseInputEvent
import org.tg.tgoneconfig.api.platform.v1.Platform
import java.util.concurrent.atomic.AtomicBoolean

internal object ToastInput {
    @Volatile var mouseX: Float = -1f
        private set

    @Volatile var mouseY: Float = -1f
        private set

    var hoveredAction by mutableStateOf<NotificationAction?>(null)

    @Volatile
    var hoverTarget: ToastHit? = null

    private val installed = AtomicBoolean(false)

    private val leftButton by lazy { Platform.compatibility().keys().mouseButtonLeft }

    private val consumedButtons = HashSet<Int>()

    fun clearHover() {
        hoverTarget = null
        hoveredAction = null
    }

    fun install() {
        if (!installed.compareAndSet(false, true)) return
        EventManager.register(MouseInputEvent.Moved::class.java) { e ->
            mouseX = e.x
            mouseY = e.y
        }
    }

    fun hovered(): ToastHit? = NotificationsRenderer.hitNow()

    fun consumeButton(button: Int, pressed: Boolean): Boolean {
        if (!pressed) return consumedButtons.remove(button)
        val target = hovered() ?: return false
        consumedButtons += button
        if (button == leftButton) NotificationsRenderer.dispatchClick(target)
        return true
    }
}
