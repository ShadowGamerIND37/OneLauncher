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

package org.tg.tgoneconfig.internal;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tg.tgoneconfig.api.event.v1.EventManager;
import org.tg.tgoneconfig.api.event.v1.events.FramebufferRenderEvent;
import org.tg.tgoneconfig.api.event.v1.events.InitializationEvent;
import org.tg.tgoneconfig.api.event.v1.invoke.EventHandler;
import org.tg.tgoneconfig.api.platform.v1.Platform;
import org.tg.tgoneconfig.internal.compat.CompatLoader;
import org.tg.compose.minecraft.font.StbFonts;
import org.tg.tgoneconfig.internal.ui.compose.impls.TGOneConfigUIScreen;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;

public final class ClassPreloader {
    private static final Logger LOGGER = LogManager.getLogger("TG OneConfig/ClassPreloader");
    private static final String LIST = "/assets/tgoneconfig/open-classes.txt";
    private static final String GLYPHS = "/assets/tgoneconfig/open-glyphs.txt";
    private static final String RECORD = System.getProperty("tgoneconfig.debug.recordOpenClasses");
    private static final boolean IN_WORLD = Boolean.getBoolean("tgoneconfig.debug.preloadInWorld");
    private static final String[] SKIP = {"java.", "javax.", "jdk.", "sun.", "com.sun.", "net.minecraft."};

    private static boolean recorded = false;

    private ClassPreloader() {
    }

    public static void init() {
        if (RECORD != null || Boolean.getBoolean("tgoneconfig.debug.noPreload")) {
            TGOneConfigUIScreen.stopWarmUp();
            return;
        }
        Thread preloader = new Thread(ClassPreloader::preload, "TG OneConfig Class Preloader");
        preloader.setDaemon(true);
        preloader.setPriority(Thread.MIN_PRIORITY);
        preloader.start();
        EventManager.register(InitializationEvent.class, init -> EventHandler.ofRemoving(FramebufferRenderEvent.End.class, e -> {
            if (preloader.isAlive() || !CompatLoader.INSTANCE.getQueuedCompatsRan()) return false;
            boolean allowed = Minecraft.getInstance().level == null ? MainMenuFpsSampler.isMainMenu(Platform.screen().current()) : IN_WORLD;
            return TGOneConfigUIScreen.warmStep(allowed);
        }).register());
    }

    private static void preload() {
        long start = System.nanoTime();
        ClassLoader loader = ClassPreloader.class.getClassLoader();
        int loaded = 0;
        int missing = 0;
        try (InputStream in = ClassPreloader.class.getResourceAsStream(LIST)) {
            if (in == null) return;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String name;
            while ((name = reader.readLine()) != null) {
                if (name.isEmpty()) continue;
                try {
                    Class.forName(name, false, loader).getDeclaredMethods();
                    loaded++;
                } catch (Exception | LinkageError ignored) {
                    missing++;
                }
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to read the class preload list", e);
        }
        LOGGER.info("Preloaded {} classes ({} missing) in {}ms", loaded, missing, (System.nanoTime() - start) / 1_000_000L);
        prerasterizeGlyphs();
    }

    private static void prerasterizeGlyphs() {
        long start = System.nanoTime();
        int count = 0;
        try (InputStream in = ClassPreloader.class.getResourceAsStream(GLYPHS)) {
            if (in == null) return;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty() && StbFonts.INSTANCE.prerasterize(line)) count++;
            }
        } catch (Exception | LinkageError e) {
            LOGGER.warn("Failed to prerasterize the menu glyphs", e);
        }
        LOGGER.info("Prerasterized {} glyphs in {}ms", count, (System.nanoTime() - start) / 1_000_000L);
    }

    public static void recordFirstOpen() {
        if (recorded) return;
        recorded = true;
        if (RECORD == null) return;
        Recording recording = new Recording();
        recording.enable("jdk.ClassLoad").withoutThreshold().withoutStackTrace();
        recording.start();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(8000L);
                recording.stop();
                Path dump = Files.createTempFile("tgoneconfig-open", ".jfr");
                recording.dump(dump);
                recording.close();
                Set<String> names = new LinkedHashSet<>();
                for (RecordedEvent event : RecordingFile.readAllEvents(dump)) {
                    if (!event.getEventType().getName().equals("jdk.ClassLoad")) continue;
                    String name = event.getClass("loadedClass").getName();
                    if (!skipped(name)) names.add(name);
                }
                Files.delete(dump);
                Path out = Paths.get(RECORD).toAbsolutePath();
                Files.write(out, names, StandardCharsets.UTF_8);
                LOGGER.info("Recorded {} classes loaded by the first menu open to {}", names.size(), out);
            } catch (Exception e) {
                LOGGER.error("Failed to record the classes loaded by the first menu open", e);
            }
        }, "TG OneConfig Class Recorder");
        thread.setDaemon(true);
        thread.start();
    }

    private static boolean skipped(String name) {
        if (name.indexOf('/') >= 0 || name.contains("$$Lambda") || name.contains(".0x") || name.contains("_Test")) return true;
        for (String prefix : SKIP) if (name.startsWith(prefix)) return true;
        return false;
    }
}
