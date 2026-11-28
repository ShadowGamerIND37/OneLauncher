package org.tg.tgoneconfig.internal.mixin.render;

//? if > 1.8.9 {
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.network.chat.Component;
import net.minecraft.world.scores.Objective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

//? if >= 26.2 {
import net.minecraft.client.gui.Hud;
//?} else {
/*import net.minecraft.client.gui.Gui;
*///?}
//? if >= 26.1 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?} else {
/*import net.minecraft.client.gui.GuiGraphics;
*///?}

//? if >= 26.2 {
@Mixin(Hud.class)
//?} else
//@Mixin(Gui.class)
public interface HudSampleAccessor {
    @Accessor("bossOverlay")
    BossHealthOverlay tgoneconfig$bossOverlay();

    @Accessor("overlayMessageString")
    Component tgoneconfig$overlayMessage();

    @Accessor("overlayMessageString")
    void tgoneconfig$setOverlayMessage(Component message);

    @Accessor("overlayMessageTime")
    int tgoneconfig$overlayMessageTime();

    @Accessor("overlayMessageTime")
    void tgoneconfig$setOverlayMessageTime(int time);

    @Accessor("animateOverlayMessageColor")
    boolean tgoneconfig$animateOverlayMessage();

    @Accessor("animateOverlayMessageColor")
    void tgoneconfig$setAnimateOverlayMessage(boolean animate);

    @Accessor("title")
    Component tgoneconfig$title();

    @Accessor("title")
    void tgoneconfig$setTitle(Component title);

    @Accessor("subtitle")
    Component tgoneconfig$subtitle();

    @Accessor("subtitle")
    void tgoneconfig$setSubtitle(Component subtitle);

    @Accessor("titleTime")
    int tgoneconfig$titleTime();

    @Accessor("titleTime")
    void tgoneconfig$setTitleTime(int time);

    @Accessor("titleFadeInTime")
    int tgoneconfig$titleFadeIn();

    @Accessor("titleFadeInTime")
    void tgoneconfig$setTitleFadeIn(int time);

    @Accessor("titleStayTime")
    int tgoneconfig$titleStay();

    @Accessor("titleStayTime")
    void tgoneconfig$setTitleStay(int time);

    @Accessor("titleFadeOutTime")
    int tgoneconfig$titleFadeOut();

    @Accessor("titleFadeOutTime")
    void tgoneconfig$setTitleFadeOut(int time);

    //? if >= 26.1 {
    @Invoker("extractBossOverlay")
    void tgoneconfig$drawBossOverlay(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker);

    @Invoker("extractOverlayMessage")
    void tgoneconfig$drawOverlayMessage(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker);

    @Invoker("extractTitle")
    void tgoneconfig$drawTitle(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker);

    @Invoker("displayScoreboardSidebar")
    void tgoneconfig$drawSidebar(GuiGraphicsExtractor graphics, Objective objective);
    //?} else {
    /*//? if >= 1.21.8 {
    @Invoker("renderBossOverlay")
    void tgoneconfig$drawBossOverlay(GuiGraphics graphics, DeltaTracker deltaTracker);
    //?}

    @Invoker("renderOverlayMessage")
    void tgoneconfig$drawOverlayMessage(GuiGraphics graphics, DeltaTracker deltaTracker);

    @Invoker("renderTitle")
    void tgoneconfig$drawTitle(GuiGraphics graphics, DeltaTracker deltaTracker);

    @Invoker("displayScoreboardSidebar")
    void tgoneconfig$drawSidebar(GuiGraphics graphics, Objective objective);
    *///?}
}
//?}
