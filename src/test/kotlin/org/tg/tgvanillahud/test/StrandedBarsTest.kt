package org.tg.tgvanillahud.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.tg.tgvanillahud.util.HudConfigMigrator
import java.nio.file.Files

class StrandedBarsTest {
    @Test
    fun `unanchored bars are unmarked even when saved as player-moved`() {
        val folder = Files.createTempDirectory("tgvanillahud-bars")
        val huds = Files.createDirectories(folder.resolve("huds"))
        Files.writeString(huds.resolve("tgvanillahud-health.json"), """{ "anchorTargetId": "", "movedByPlayer": true }""")
        Files.writeString(huds.resolve("tgvanillahud-hunger.json"), """{ "anchorTargetId": "", "movedByPlayer": false }""")
        Files.writeString(huds.resolve("tgvanillahud-armor.json"), """{ "anchorTargetId": "huds/tgvanillahud-health.json" }""")
        Files.writeString(huds.resolve("tgvanillahud-air.json"), "{}")
        val customized = folder.resolve("tgvanillahud-customized")
        Files.write(
            customized,
            listOf(
                "tgvanillahud-health.json", "tgvanillahud-hunger.json", "tgvanillahud-armor.json", "tgvanillahud-air.json",
                "tgvanillahud-experience.json", "tgvanillahud-tab.json",
            ),
        )

        HudConfigMigrator.reseedStrandedBars(folder)

        assertEquals(listOf("tgvanillahud-armor.json", "tgvanillahud-tab.json"), Files.readAllLines(customized))
    }
}
