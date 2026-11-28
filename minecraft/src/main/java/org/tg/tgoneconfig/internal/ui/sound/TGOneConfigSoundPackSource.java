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

package org.tg.tgoneconfig.internal.ui.sound;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.RepositorySource;

//? if >= 26.3 {
import java.util.stream.Stream;
import net.minecraft.server.packs.PackMetadataResources;
//?}

//? if > 1.8.9 {
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
//?}

//? if = 1.8.9 {
/*import net.ornithemc.osl.resource.loader.api.resource.pack.PackPosition;
import net.ornithemc.osl.resource.loader.api.resource.repository.ResourcePackSummary;
import net.ornithemc.osl.resource.loader.impl.resource.pack.DirectoryResourcePack;
*///?}

//~ if = 1.8.9 'RepositorySource' -> 'ResourcePackRepository.Source'
public final class TGOneConfigSoundPackSource implements RepositorySource {
    public static final TGOneConfigSoundPackSource INSTANCE = new TGOneConfigSoundPackSource();

    public static final Path PACK_ROOT = Paths.get("tgoneconfig", "sounds");

    private static final String PACK_ID = "tgoneconfig_sounds";

    private TGOneConfigSoundPackSource() {
    }

    //? if > 1.8.9 {
    @Override
    public void loadPacks(Consumer<Pack> consumer) {
        if (!Files.isRegularFile(PACK_ROOT.resolve("pack.mcmeta"))) return;

        PackLocationInfo location = new PackLocationInfo(
                PACK_ID,
                Component.literal("TG OneConfig Sounds"),
                PackSource.BUILT_IN,
                Optional.empty()
        );
        //? if >= 26.3 {
        Pack.ResourcesSupplier resources = new Pack.ResourcesSupplier() {
            @Override
            public PackMetadataResources openMetadata(PackLocationInfo info) {
                return new PathPackResources(info, PACK_ROOT);
            }

            @Override
            public Stream<PackResources> openResources(PackLocationInfo info, Pack.Metadata metadata) {
                return Stream.of(new PathPackResources(info, PACK_ROOT));
            }
        };
        //?} else {
        /*Pack.ResourcesSupplier resources = new Pack.ResourcesSupplier() {
            @Override
            public PackResources openPrimary(PackLocationInfo info) {
                return new PathPackResources(info, PACK_ROOT);
            }

            @Override
            public PackResources openFull(PackLocationInfo info, Pack.Metadata metadata) {
                return new PathPackResources(info, PACK_ROOT);
            }
        };
        *///?}
        PackSelectionConfig selection = new PackSelectionConfig(true, Pack.Position.BOTTOM, false);

        Pack pack = Pack.readMetaAndCreate(location, resources, PackType.CLIENT_RESOURCES, selection);
        if (pack != null) {
            consumer.accept(pack);
        }
    }
    //?} else {
    /*@Override
    public void loadResourcePacks(Consumer<ResourcePackSummary> consumer) {
            if (!Files.isRegularFile(PACK_ROOT.resolve("pack.mcmeta"))) return;

            ResourcePackSummary summary = ResourcePackSummary.create(
                    PACK_ID,
                    true,
                    false,
                    PackPosition.BOTTOM,
                    () -> new DirectoryResourcePack(PACK_ROOT) {
                        @Override
                        public String getName() {
                            return "TG OneConfig Sounds";
                        }
                    }
            );

            if (summary != null) {
                consumer.accept(summary);
            }
        }
    *///?}
}
