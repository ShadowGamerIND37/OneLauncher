package org.tg.tgoneconfig.internal.mixin.render;

//? if = 1.8.9 {
/*import net.minecraft.client.gui.GameGui;
import net.minecraft.client.render.Window;
import net.minecraft.world.scores.Objective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameGui.class)
public interface LegacyHudSampleAccessor {
    @Invoker("renderBossBars")
    void tgoneconfig$renderBossBars();

    @Invoker("renderScoreboardObjective")
    void tgoneconfig$renderScoreboardObjective(Objective objective, Window window);

    @Accessor("overlayMessage")
    String tgoneconfig$overlayMessage();

    @Accessor("overlayMessage")
    void tgoneconfig$setOverlayMessage(String message);

    @Accessor("overlayMessageCooldown")
    int tgoneconfig$overlayMessageCooldown();

    @Accessor("overlayMessageCooldown")
    void tgoneconfig$setOverlayMessageCooldown(int cooldown);

    @Accessor("overlayMessageTinted")
    boolean tgoneconfig$overlayMessageTinted();

    @Accessor("overlayMessageTinted")
    void tgoneconfig$setOverlayMessageTinted(boolean tinted);

    @Accessor("titleTime")
    int tgoneconfig$titleTime();

    @Accessor("titleTime")
    void tgoneconfig$setTitleTime(int time);

    @Accessor("title")
    String tgoneconfig$title();

    @Accessor("title")
    void tgoneconfig$setTitle(String title);

    @Accessor("subtitle")
    String tgoneconfig$subtitle();

    @Accessor("subtitle")
    void tgoneconfig$setSubtitle(String subtitle);

    @Accessor("titleFadeInTime")
    int tgoneconfig$titleFadeInTime();

    @Accessor("titleFadeInTime")
    void tgoneconfig$setTitleFadeInTime(int time);

    @Accessor("titleDuration")
    int tgoneconfig$titleDuration();

    @Accessor("titleDuration")
    void tgoneconfig$setTitleDuration(int time);

    @Accessor("titleFadeOutTime")
    int tgoneconfig$titleFadeOutTime();

    @Accessor("titleFadeOutTime")
    void tgoneconfig$setTitleFadeOutTime(int time);
}
*///?}
