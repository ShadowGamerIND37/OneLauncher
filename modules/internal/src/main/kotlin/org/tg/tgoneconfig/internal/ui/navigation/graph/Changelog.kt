package org.tg.tgoneconfig.internal.ui.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import org.tg.tgoneconfig.internal.ui.screens.Changelog

@Serializable
data object ChangeLogGraph

@Serializable
data object ChangeLogRoute

fun NavGraphBuilder.changeLogGraph() {
    navigation<ChangeLogGraph>(startDestination = ChangeLogRoute) {
        composable<ChangeLogRoute> {
            Changelog()
        }
    }
}
