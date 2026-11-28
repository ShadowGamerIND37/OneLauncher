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

import org.tg.compose.render.PolyColor;
import org.tg.tgoneconfig.api.config.v1.serialize.adapter.Adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PolyColorAdapter extends Adapter<PolyColor, Object> {
	@Override
	public Object serialize(PolyColor in) {
		if (!in.getChroma() && !in.getGradientEnabled()) {
			return rgba(in.getRawArgb());
		}

		Map<String, Object> out = new HashMap<>();
		out.put("rgba", rgba(in.getRawArgb()));
		if (in.getChroma()) {
			out.put("chroma", true);
			out.put("chromaSpeed", in.getChromaSpeed());
		}
		if (in.getGradientEnabled()) {
			out.put("gradient", rgba(in.getGradientArgb()));
		}
		return out;
	}

	@Override
	public PolyColor deserialize(Object in) {
		if (in instanceof Map<?, ?>) {
			Map<?, ?> map = (Map<?, ?>) in;
			PolyColor color = deserialize(map.get("rgba"));
			Object speed = map.get("chromaSpeed");
			PolyColor out = color.withChroma(
				Boolean.TRUE.equals(map.get("chroma")),
				speed instanceof Number ? ((Number) speed).floatValue() : color.getChromaSpeed()
			);
			Object gradient = map.get("gradient");
			if (gradient != null) {
				out = out.withGradient(true, deserialize(gradient).getRawArgb());
			}
			return out;
		}
		if (in instanceof int[]) {
			int[] color = (int[]) in;
			return PolyColor.Companion.rgba(color[0], color[1], color[2], color[3]);
		}
		if (in instanceof List<?>) {
			List<?> color = (List<?>) in;
			return PolyColor.Companion.rgba(
				((Number) color.get(0)).intValue(),
				((Number) color.get(1)).intValue(),
				((Number) color.get(2)).intValue(),
				((Number) color.get(3)).intValue()
			);
		}
		if (in instanceof Number) {
			int argb = (int) ((Number) in).longValue();
			return PolyColor.Companion.rgba((argb >> 16) & 0xFF, (argb >> 8) & 0xFF, argb & 0xFF, (argb >> 24) & 0xFF);
		}
		if (in instanceof String) {
			return PolyColor.Companion.hex((String) in);
		}
		throw new IllegalArgumentException("Unsupported PolyColor value: " + in);
	}

	@Override
	public Class<PolyColor> getTargetClass() {
		return PolyColor.class;
	}

	@Override
	public Class<Object> getOutputClass() {
		return Object.class;
	}

	private static int[] rgba(int argb) {
		return new int[]{
			(argb >> 16) & 0xFF,
			(argb >> 8) & 0xFF,
			argb & 0xFF,
			(argb >> 24) & 0xFF
		};
	}
}
