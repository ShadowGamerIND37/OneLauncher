package org.tg.tgvanillahud.hud;

public final class HudInternals {
    private HudInternals() {
    }

    public static int scoreboardRevision;

    public static void bumpScoreboardRevision() {
        scoreboardRevision++;
    }
}
