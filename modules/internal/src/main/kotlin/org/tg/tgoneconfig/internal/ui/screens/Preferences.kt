package org.tg.tgoneconfig.internal.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.tg.tgoneconfig.api.ui.v1.components.EmptyState
import org.tg.tgoneconfig.internal.ui.api.ConfigRegistry
import org.tg.tgoneconfig.internal.ui.components.translated

const val PREFERENCES_ID = "tgoneconfig.json"

@Composable
fun Preferences() {
    val revision = ConfigRegistry.revision
    val tree = ConfigRegistry.findTree(PREFERENCES_ID)

    if (tree == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            EmptyState(translated("tgoneconfig.preferences.empty", "No preferences available"), icon = "settings")
        }
        return
    }

    key(revision, tree) {
        ConfigScreen(tree, pageKey = PREFERENCES_ID)
    }
}
