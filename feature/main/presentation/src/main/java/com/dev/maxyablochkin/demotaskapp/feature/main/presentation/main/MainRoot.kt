package com.dev.maxyablochkin.demotaskapp.feature.main.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.rememberNavController
import com.dev.maxyablochkin.demotaskapp.core.navigation.LocalBottomTabNavController
import com.dev.maxyablochkin.demotaskapp.core.presentation.LocalSnackbarHostState
import com.dev.maxyablochkin.demotaskapp.core.presentation.collectAsNavEventWithLifecycle
import com.dev.maxyablochkin.demotaskapp.feature.categories.api.CategoriesGraph
import com.dev.maxyablochkin.demotaskapp.feature.home.api.HomeGraph
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainRoot(
    modifier: Modifier = Modifier,
    tabGraphs: NavGraphBuilder.() -> Unit,
    viewModel: MainViewModel = koinViewModel(),
) {
    val bottomTabNavController = rememberNavController()
    val snackbarHostState = LocalSnackbarHostState.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    CompositionLocalProvider(
        LocalBottomTabNavController provides bottomTabNavController,
        LocalSnackbarHostState provides snackbarHostState
    ) {
        MainScreen(
            modifier = modifier,
            state = state,
            onAction = viewModel::onAction,
            tabGraphs = tabGraphs
        )

        viewModel.navEvents.collectAsNavEventWithLifecycle { navEvent ->
            val destination = when (navEvent) {
                MainNavEvent.Home -> HomeGraph
                MainNavEvent.Categories -> CategoriesGraph
            }

            bottomTabNavController.currentDestination?.let {
                bottomTabNavController.navigate(destination) {
                    popUpTo(bottomTabNavController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }
}
