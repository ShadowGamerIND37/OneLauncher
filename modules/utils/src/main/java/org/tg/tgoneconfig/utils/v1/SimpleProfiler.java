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

package org.tg.tgoneconfig.utils.v1;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * A simple class that can be used to profile code
 */
public final class SimpleProfiler {
    private static final HashMap<String, Long> startTimes = new HashMap<>();
    private static final Logger LOGGER = LogManager.getLogger("TG OneConfig/Profiler");

    private SimpleProfiler() {
    }

    /**
     * Push a profiler start time to the map <br>
     *
     * @param msg the key for this tracker
     * @return true if the key was not already in the map
     * @see #pop(String)
     */
    public static boolean push(@NotNull final String msg) {
        return startTimes.put(msg, System.nanoTime()) == null;
    }

    /**
     * Pop a profiler start time from the map and print the time to the log in this format <br>
     * {@code [TGOneConfig Profiler/INFO] <msg> took <time>ms}
     *
     * @param msg the key that was used for {@link #push(String)} <p> This is also used for the message above
     * @return the time in milliseconds since the push was called
     * @see #push(String)
     */
    public static float pop(@NotNull final String msg) {
        return pop(msg, Level.DEBUG);
    }

    /**
     * Pop a profiler start time from the map and print the time to the log in this format <br>
     * {@code [TGOneConfig Profiler/<level>] <msg> took <time>ms}
     *
     * @param msg   the key that was used for {@link #push(String)} <p> This is also used for the message above
     * @param level the log level to use
     * @return the time in milliseconds since the push was called
     * @see #push(String)
     */
    public static float pop(@NotNull final String msg, @NotNull Level level) {
        final float time = (System.nanoTime() - startTimes.remove(msg)) / 1000000f;
        LOGGER.log(level, "{} took {}ms", msg, time);
        return time;
    }
}
