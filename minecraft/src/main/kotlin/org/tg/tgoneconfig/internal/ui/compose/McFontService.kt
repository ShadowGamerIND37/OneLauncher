package org.tg.tgoneconfig.internal.ui.compose

import org.tg.compose.render.FontManager

object McFontService {
    fun init() {
        FontManager.loadFromResource("assets/tgoneconfig/fonts/Poppins/Poppins-Thin.ttf", "poppins-thin")
        FontManager.loadFromResource("assets/tgoneconfig/fonts/Poppins/Poppins-ThinItalic.ttf", "poppins-thin-italic")
        FontManager.loadFromResource("assets/tgoneconfig/fonts/Poppins/Poppins-Regular.ttf", "poppins")
        FontManager.loadFromResource("assets/tgoneconfig/fonts/Poppins/Poppins-Italic.ttf", "poppins-italic")
        FontManager.loadFromResource("assets/tgoneconfig/fonts/Poppins/Poppins-Medium.ttf", "poppins-medium")
        FontManager.loadFromResource("assets/tgoneconfig/fonts/Poppins/Poppins-SemiBold.ttf", "poppins-semibold")
        FontManager.loadFromResource("assets/tgoneconfig/fonts/Poppins/Poppins-MediumItalic.ttf", "poppins-medium-italic")
        FontManager.loadFromResource("assets/tgoneconfig/fonts/Poppins/Poppins-Bold.ttf", "poppins-bold")
        FontManager.loadFromResource("assets/tgoneconfig/fonts/Poppins/Poppins-BoldItalic.ttf", "poppins-bold-italic")
        FontManager.loadFromResource("assets/tgoneconfig/fonts/Poppins/Poppins-Black.ttf", "poppins-black")
        FontManager.loadFromResource("assets/tgoneconfig/fonts/Poppins/Poppins-BlackItalic.ttf", "poppins-black-italic")
        // used by toasts when a Minecraft theme is active
        FontManager.loadFromResource("assets/tgoneconfig/fonts/minecraft/Minecraft-Regular.otf", "minecraft", pixelAligned = true)
        FontManager.loadFromResource("assets/tgoneconfig/fonts/minecraft/Minecraft-Bold.otf", "minecraft-bold", pixelAligned = true)
        FontManager.loadFromResource("assets/tgoneconfig/fonts/unifont/unifont.otf", "unifont", pixelAligned = true)
        FontManager.setDefault("poppins")
    }
}
