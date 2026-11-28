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

package org.tg.tgoneconfig.api.hud.v1

import org.jetbrains.annotations.ApiStatus

/**
 * Draws the HUDs into one part of the window, for mods that show the game in a smaller frame such as a
 * replay editor's preview. HUDs are laid out on a [guiWidth] by [guiHeight] screen with its top left corner
 * at [x], [y] and drawn at [scale] framebuffer pixels per GUI unit.
 *
 * @property x left edge of the frame in framebuffer pixels
 * @property y top edge of the frame in framebuffer pixels
 * @property guiWidth width of the frame in GUI units
 * @property guiHeight height of the frame in GUI units
 * @property scale framebuffer pixels per GUI unit
 */
@ApiStatus.Experimental
public data class HudViewport(val x: Float, val y: Float, val guiWidth: Float, val guiHeight: Float, val scale: Float)
