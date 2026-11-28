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

package org.tg.tgoneconfig.api.config.v1;

/**
 * Lets a mod provide its own TGOneConfig layout, which replaces the one TGOneConfig would otherwise extract
 * from the mod's original config screen (YACL, Cloth Config, MidnightLib, ...). The original screen stays
 * reachable from the "open original" button.
 * <br>
 * A mod provides one by naming an implementation under the {@value #ENTRYPOINT} entrypoint of its
 * {@code fabric.mod.json}. The class is only loaded when TGOneConfig is installed, so this can be a
 * {@code compileOnly} dependency.
 */
@FunctionalInterface
public interface ConfigEntrypoint {
    String ENTRYPOINT = "tgoneconfig:config";

    /**
     * Called once after every mod has initialized. Create your {@link Config} instances here. Configs whose class is
     * in your mod (or a mod nested in its jar) belong to it automatically if they were created before this returns. Any other
     * config, such as one whose class is in a library, must be passed to {@link Context#register(Config)}.
     * <br>
     * If none of its configs ends up registered, because this registered none, threw before registering any,
     * or every one failed to initialize, TGOneConfig extracts the original screen as if there were no entrypoint.
     * A config whose constructor throws out of this method is discarded. If you catch that exception yourself,
     * TGOneConfig cannot tell and still initializes the half-built config, so let it propagate instead.
     */
    void registerConfigs(Context context);

    /**
     * Information about, and actions for, the mod whose entrypoint is being called.
     * Meant to be used while {@link #registerConfigs(Context)} runs. A config registered after it returns logs a
     * warning, and is ignored if TGOneConfig already fell back to extracting the original screen.
     */
    interface Context {
        /**
         * The id of the mod that provides this entrypoint
         */
        String modId();

        /**
         * Marks the config as belonging to this mod, so it gets the "open original" button. Only needed for
         * configs whose class is not in this mod, such as one from a library, whenever they were created
         *
         * @return the config, for chaining
         */
        <T extends Config> T register(T config);

        /**
         * Replaces how the "open original" button opens this mod's original config screen.
         * By default, it opens the screen the mod gives Mod Menu.
         */
        void originalScreen(Runnable opener);
    }
}
