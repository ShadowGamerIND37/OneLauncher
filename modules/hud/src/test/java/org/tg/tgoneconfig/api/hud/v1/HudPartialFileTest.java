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

package org.tg.tgoneconfig.api.hud.v1;

import kotlin.Pair;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tg.tgoneconfig.api.config.v1.ConfigManager;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HudPartialFileTest {
    private static final String ID = "huds/test-partial.json";

    private final List<String> errors = new CopyOnWriteArrayList<>();
    private final AbstractAppender appender = new AbstractAppender("HudPartialFileTest", null, null, true, Property.EMPTY_ARRAY) {
        @Override
        public void append(LogEvent event) {
            if (event.getLevel() == Level.ERROR || event.getLevel() == Level.FATAL) errors.add(event.getMessage().getFormattedMessage());
        }
    };

    @BeforeEach
    void setUp() throws Exception {
        ConfigManager.active();
        reset();
        appender.start();
        ((Logger) LogManager.getLogger("TG OneConfig/HUD")).addAppender(appender);
    }

    @AfterEach
    void tearDown() throws Exception {
        ((Logger) LogManager.getLogger("TG OneConfig/HUD")).removeAppender(appender);
        appender.stop();
        HudManager.INSTANCE.unregister(new PartialHud(), true, false);
        reset();
    }

    // mods like TGVanillaHUD migrate their own settings by writing a file without hudClass for a
    // HUD that does not exist yet, so that the HUD picks up those settings when it is first made
    @Test
    void fileWithoutHudClassIsAdoptedWithoutErrors() throws Exception {
        Path file = ConfigManager.active().getFolder().resolve(ID);
        Files.createDirectories(file.getParent());
        Files.write(file, "{\"hidden\": true}".getBytes(StandardCharsets.UTF_8));
        HudManager.register(new PartialHud());

        HudManager.INSTANCE.initialize();

        List<PartialHud> huds = HudManager.INSTANCE.getHudsOfType(PartialHud.class);
        assertEquals(1, huds.size());
        assertTrue(huds.get(0).getHidden(), "the migrated value should be adopted by the new HUD");
        assertEquals(List.of(), errors);
    }

    private static void reset() throws Exception {
        HudManager.INSTANCE.getActiveInstances().clear();
        knownProviders().clear();
        set("registryTree", null);
        set("init", false);
        Path folder = ConfigManager.active().getFolder();
        Files.deleteIfExists(folder.resolve(ID));
        Files.deleteIfExists(folder.resolve("hud-registry.json"));
        ConfigManager.active().delete("hud-registry.json");
        ConfigManager.active().delete(ID);
    }

    @SuppressWarnings("unchecked") // the field is declared as a Set<String>
    private static Set<String> knownProviders() throws Exception {
        Field f = HudManager.class.getDeclaredField("knownProviders");
        f.setAccessible(true);
        return (Set<String>) f.get(HudManager.INSTANCE);
    }

    private static void set(String name, Object value) throws Exception {
        Field f = HudManager.class.getDeclaredField(name);
        f.setAccessible(true);
        f.set(HudManager.INSTANCE, value);
    }

    static class PartialHud extends TextHud {
        PartialHud() {
            super("test-partial.json", "Test Partial HUD", Hud.Category.getINFO(), "", "");
        }

        @Override
        public Pair<Float, Float> defaultPosition() {
            return new Pair<>(10f, 10f);
        }

        @Override
        public boolean showByDefault() {
            return true;
        }

        @Override
        public boolean multipleInstancesAllowed() {
            return false;
        }

        @Override
        public String getText() {
            return "test";
        }
    }
}
