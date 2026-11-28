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

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    @Test
    void test() {
        File configFile = new File("config/test_mod.json");
        assertTrue(!configFile.exists() || configFile.delete());
        TestConfig config = new TestConfig();
        config.initialize(true);
        Tree t = config.tree;
        assertEquals(t, ConfigManager.active().get(t.getID()));
        assertNotNull(t.get("chicken").getMetadata("visualizer"));
        assertNull(t.get("reserved:overwritten"));
        System.err.println(t);
        assertTrue(TestConfig.chicken);
        assertFalse(TestConfig.cow5);
        config.loadFrom(new File(new File(new File(".").getParentFile(), "config-to-migrate-for-test"), "test_migration_mod.json").toPath());
        assertNotNull(t.get("reserved:overwritten"));
        assertFalse(TestConfig.chicken);
        assertTrue(TestConfig.cow5);
        System.err.println(t);
    }
}
