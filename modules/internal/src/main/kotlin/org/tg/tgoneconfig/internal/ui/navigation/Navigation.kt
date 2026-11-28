package org.tg.tgoneconfig.internal.ui.navigation

import androidx.navigation.NavGraphBuilder
import org.tg.tgoneconfig.internal.ui.navigation.graph.changeLogGraph
import org.tg.tgoneconfig.internal.ui.navigation.graph.creditsGraph
import org.tg.tgoneconfig.internal.ui.navigation.graph.keybindsGraph
import org.tg.tgoneconfig.internal.ui.navigation.graph.modsGraph
import org.tg.tgoneconfig.internal.ui.navigation.graph.preferencesGraph
import org.tg.tgoneconfig.internal.ui.navigation.graph.profilesGraph
import org.tg.tgoneconfig.internal.ui.navigation.graph.themesGraph

fun NavGraphBuilder.navigation() {
    modsGraph()
    profilesGraph()
    keybindsGraph()
    themesGraph()
    preferencesGraph()
    changeLogGraph()
    creditsGraph()
    pageDestinations()
}