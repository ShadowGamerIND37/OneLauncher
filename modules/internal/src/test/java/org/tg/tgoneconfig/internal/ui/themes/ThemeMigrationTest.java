package org.tg.tgoneconfig.internal.ui.themes;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.tg.tgoneconfig.api.config.v1.ConfigManager;
import org.tg.tgoneconfig.internal.ThemeConfig;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ThemeMigrationTest {
    private static final ThemeConfig CONFIG = new ThemeConfig();

    @Test
    @Order(1)
    void themeLoadBeforeConfigInitKeepsRename() throws Exception {
        Path file = Path.of("config", "themes.json");
        Files.createDirectories(file.getParent());
        Files.writeString(file, "{\"activeTheme\":\"PolyGlass Light\"}");
        ThemeRegistry.INSTANCE.loadFromConfig();
        CONFIG.preload();
        ThemeRegistry.INSTANCE.loadFromConfig();
        assertEquals("TGed Light", ThemeConfig.activeTheme);
        assertTrue(ThemeConfig.polyfrostedRenameApplied);
    }

    @Test
    @Order(2)
    void profileSwitchRenamesOnlySavedThemes() throws Exception {
        CONFIG.preload();
        Path oldSave = ConfigManager.PROFILES_DIR.resolve("theme-old-save");
        Files.createDirectories(ConfigManager.PROFILES_DIR.resolve("theme-no-save"));
        Files.createDirectories(oldSave);
        Files.writeString(oldSave.resolve("themes.json"), "{\"activeTheme\":\"PolyGlass Dark\"}");
        ThemeConfig.activeTheme = "PolyGlass Dark";
        try {
            ConfigManager.openProfile("theme-no-save");
            ThemeRegistry.INSTANCE.loadFromConfig();
            assertEquals("PolyGlass Dark", ThemeConfig.activeTheme);

            ConfigManager.openProfile("theme-old-save");
            ThemeRegistry.INSTANCE.loadFromConfig();
            assertEquals("TGed Dark", ThemeConfig.activeTheme);
        } finally {
            ConfigManager.openProfile("");
        }
    }
}
