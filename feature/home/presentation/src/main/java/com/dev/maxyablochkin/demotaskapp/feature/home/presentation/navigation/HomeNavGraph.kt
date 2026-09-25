package com.dev.maxyablochkin.demotaskapp.feature.home.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.dev.maxyablochkin.demotaskapp.feature.home.api.HomeGraph
import com.dev.maxyablochkin.demotaskapp.feature.home.presentation.feed.HomeRoot

fun NavGraphBuilder.homeGraph() {
    navigation<HomeGraph>(startDestination = HomeDestination.Feed) {
        composable<HomeDestination.Feed> {
            HomeRoot()
        }
    }
}
