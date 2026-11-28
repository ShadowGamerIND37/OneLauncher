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
 */

package org.tg.tgoneconfig.api.config.v1;

import org.junit.jupiter.api.Test;
import org.tg.tgoneconfig.api.config.v1.annotations.Slider;
import org.tg.tgoneconfig.api.config.v1.collect.impl.TGOneConfigCollector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ValueFormatterTest {
    @Test
    void collectsAnnotationFormatters() {
        Tree tree = new TGOneConfigCollector().collect(new FormatterConfig());

        ValueFormatter formatter = tree.getProp("formatted").getMetadata("formatter");
        assertEquals("5%", formatter.format(5));
        assertNull(tree.getProp("plain").getMetadata("formatter"));
    }

    static final class Percent implements ValueFormatter {
        @Override
        public String format(Number value) {
            return value + "%";
        }
    }

    private static final class FormatterConfig extends Config {
        @Slider(title = "Formatted", formatter = Percent.class)
        private int formatted = 5;

        @Slider(title = "Plain")
        private int plain = 5;

        private FormatterConfig() {
            super("value-formatter-test.json", "Value Formatter Test", Category.OTHER);
        }
    }
}
