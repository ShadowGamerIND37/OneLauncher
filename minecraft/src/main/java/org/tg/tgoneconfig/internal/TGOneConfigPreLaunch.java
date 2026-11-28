package org.tg.tgoneconfig.internal;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.polyfrost.compose.render.PolyBitmap;
import org.slf4j.LoggerFactory;

public final class TGOneConfigPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        try {
            PolyBitmap.registerImageReaders();
        } catch (Throwable t) {
            LoggerFactory.getLogger(TGOneConfigPreLaunch.class).error("Failed to register WebP/ICO image readers; AWT is unavailable", t);
        }
    }
}
