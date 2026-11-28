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

package org.tg.tgoneconfig.api.ui.v1.api.internal;

import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

//? if sdl {
import java.io.File;
//?}

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TinyFdApiImplTest {
    //? if sdl {
    @Test
    void filtersBecomeSemicolonSeparatedExtensions() {
        assertEquals("png;jpg;tar.gz", TinyFdApiImpl.pattern(new String[]{"*.png", ".jpg", "tar.gz"}));
        assertEquals("png", TinyFdApiImpl.pattern(new String[]{"*.png", "image*.webp"}));
        assertNull(TinyFdApiImpl.pattern(new String[]{"*.png", "*.*"}));
        assertNull(TinyFdApiImpl.pattern(new String[0]));
        assertNull(TinyFdApiImpl.pattern(null));
    }

    @Test
    void locationsAreAbsoluteAndFoldersEndWithASeparator() {
        String gameDirectory = Paths.get("").toAbsolutePath().toString();
        assertEquals(Paths.get("Default.zip").toAbsolutePath().toString(), TinyFdApiImpl.location("Default.zip"));
        assertEquals(gameDirectory + File.separator, TinyFdApiImpl.location(gameDirectory));
        assertEquals(Paths.get("missing").toAbsolutePath() + File.separator, TinyFdApiImpl.location("missing/"));
        assertNull(TinyFdApiImpl.location(""));
        assertNull(TinyFdApiImpl.location(null));
    }
    //?} else {
    /*@Test
    void bareFileNameAnchorsToGameDirectory() {
        assertEquals(Paths.get("Default.zip").toAbsolutePath().toString(), TinyFdApiImpl.absolutize("Default.zip"));
    }

    @Test
    void macKeepsOnlyTheFileNameSoAppleScriptGetsNoLocation() {
        assertEquals("Default.zip", TinyFdApiImpl.macDefaultPath("/Users/me/Library/Application Support/mc/Default.zip"));
        assertEquals("Default.zip", TinyFdApiImpl.macDefaultPath("Default.zip"));
        assertNull(TinyFdApiImpl.macDefaultPath("/Users/me/mc/"));
        assertNull(TinyFdApiImpl.macDefaultPath(Paths.get(".").toAbsolutePath().toString()));
        assertNull(TinyFdApiImpl.macDefaultPath(null));
    }

    @Test
    void absolutePathIsUntouched() {
        String absolute = Paths.get("config", "profile.zip").toAbsolutePath().toString();
        assertEquals(absolute, TinyFdApiImpl.absolutize(absolute));
        assertNull(TinyFdApiImpl.absolutize(null));
    }
    *///?}
}
