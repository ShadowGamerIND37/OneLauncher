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

package org.tg.tgoneconfig.api.event.v1.internal;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.tg.tgoneconfig.api.event.v1.events.Event;

import java.lang.reflect.Modifier;
import java.util.StringJoiner;

/**
 * Validates that a class handlers are registered for is a concrete event type that can actually be posted.
 * Abstract parent types such as {@code TickEvent} are never posted directly, so handlers for them would
 * silently never fire.
 */
@ApiStatus.Internal
public final class EventClassValidator {
    private EventClassValidator() {
    }

    public static void validate(Class<?> cls) {
        String problem = describeProblem(cls);
        if (problem != null) {
            throw new IllegalArgumentException(problem);
        }
    }

    @Nullable
    public static String describeProblem(Class<?> cls) {
        if (cls == null) {
            return "event class must not be null";
        }
        if (!Event.class.isAssignableFrom(cls)) {
            return cls.getName() + " does not implement Event";
        }
        if (!Modifier.isAbstract(cls.getModifiers())) {
            return null;
        }
        StringJoiner subtypes = new StringJoiner(", ");
        for (Class<?> inner : cls.getClasses()) {
            if (cls.isAssignableFrom(inner) && !Modifier.isAbstract(inner.getModifiers())) {
                subtypes.add(cls.getSimpleName() + '.' + inner.getSimpleName());
            }
        }
        String advice = subtypes.length() == 0
                ? "register for one of its concrete subtypes instead"
                : "register for one of its concrete subtypes instead: " + subtypes;
        return cls.getName() + " is abstract and never posted directly, so handlers for it would never be called; " + advice;
    }
}
