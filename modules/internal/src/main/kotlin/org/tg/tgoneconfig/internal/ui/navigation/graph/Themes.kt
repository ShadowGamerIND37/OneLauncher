package org.tg.tgoneconfig.internal.ui.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import org.tg.tgoneconfig.internal.ui.screens.Themes

@Serializable
data object ThemesGraph

@Serializable
data object ThemesRoute

fun NavGraphBuilder.themesGraph() {
    navigation<ThemesGraph>(startDestination = ThemesRoute) {
        composable<ThemesRoute> {
            Themes()
        }
    }
}