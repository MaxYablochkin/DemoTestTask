package com.dev.maxyablochkin.demotaskapp.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dev.maxyablochkin.demotaskapp.core.navigation.AppNavHost
import com.dev.maxyablochkin.demotaskapp.feature.categories.presentation.navigation.categoriesGraph
import com.dev.maxyablochkin.demotaskapp.feature.home.presentation.navigation.homeGraph
import com.dev.maxyablochkin.demotaskapp.feature.main.api.MainGraph
import com.dev.maxyablochkin.demotaskapp.feature.main.presentation.navigation.mainGraph

@Composable
fun DemoTaskAppNavigation(modifier: Modifier = Modifier) {
    AppNavHost(modifier = modifier, startDestination = MainGraph) {
        mainGraph {
            homeGraph()
            categoriesGraph()
        }
    }
}