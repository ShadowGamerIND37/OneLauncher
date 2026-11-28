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

package org.tg.tgoneconfig.api.event.v1.invoke.impl;

import org.jetbrains.annotations.ApiStatus;
import org.tg.tgoneconfig.api.event.v1.events.Event;
import org.tg.tgoneconfig.api.event.v1.invoke.EventHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Consumer;

/**
 * Annotate a method as a subscriber to an event
 * <br>
 * The method should take one parameter which extends {@link Event} and return void
 * @apiNote This mechanism should be replaced with the lambda-style for events (see {@link EventHandler#of(Class, Consumer)})
 * <br> That style is more performant and better from a code quality perspective
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ApiStatus.Obsolete
public @interface Subscribe {
    /**
     * Priority for this event handler
     * @see EventHandler#getPriority()
     */
    int priority() default 0;
}
