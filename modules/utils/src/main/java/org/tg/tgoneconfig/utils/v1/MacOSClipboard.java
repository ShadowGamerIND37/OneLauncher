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

import ca.weblite.objc.Client;
import ca.weblite.objc.Proxy;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/**
 * macOS clipboard via NSPasteboard
 * <p>
 * Loaded only on macOS through {@link ClipboardHelper}
 */
@ApiStatus.Internal
public final class MacOSClipboard {

    private static final String PLAIN_TEXT_TYPE = "public.utf8-plain-text";

    private MacOSClipboard() {
    }

    @Nullable
    public static String getString() {
        Client client = Client.getInstance();
        Proxy pasteboard = client.sendProxy("NSPasteboard", "generalPasteboard");
        return pasteboard.sendString("stringForType:", PLAIN_TEXT_TYPE);
    }

    public static boolean setString(String value) {
        Client client = Client.getInstance();
        Proxy pasteboard = client.sendProxy("NSPasteboard", "generalPasteboard");
        pasteboard.send("clearContents");
        return pasteboard.sendBoolean("setString:forType:", value, PLAIN_TEXT_TYPE);
    }

    public static boolean copyImageFromPath(String path) {
        Client client = Client.getInstance();
        Proxy url = client.sendProxy("NSURL", "fileURLWithPath:", path);
        Proxy image = client.sendProxy("NSImage", "alloc");
        image = image.sendProxy("initWithContentsOfURL:", url);
        Proxy array = client.sendProxy("NSArray", "array");
        array = array.sendProxy("arrayByAddingObject:", image);
        Proxy pasteboard = client.sendProxy("NSPasteboard", "generalPasteboard");
        pasteboard.send("clearContents");
        return pasteboard.sendBoolean("writeObjects:", array);
    }

    public static boolean clear() {
        Client client = Client.getInstance();
        Proxy pasteboard = client.sendProxy("NSPasteboard", "generalPasteboard");
        pasteboard.send("clearContents");
        return true;
    }
}
