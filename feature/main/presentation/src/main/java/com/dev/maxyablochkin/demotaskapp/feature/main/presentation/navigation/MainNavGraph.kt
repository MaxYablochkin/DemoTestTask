package com.dev.maxyablochkin.demotaskapp.feature.main.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.dev.maxyablochkin.demotaskapp.feature.main.api.MainGraph
import com.dev.maxyablochkin.demotaskapp.feature.main.presentation.main.MainRoot

fun NavGraphBuilder.mainGraph(tabGraphs: NavGraphBuilder.() -> Unit) {
    navigation<MainGraph>(startDestination = MainRoute) {
        composable<MainRoute> {
            MainRoot(tabGraphs = tabGraphs)
        }
    }
}
