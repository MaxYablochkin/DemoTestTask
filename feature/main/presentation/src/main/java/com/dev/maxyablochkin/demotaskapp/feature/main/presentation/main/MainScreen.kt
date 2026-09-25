package com.dev.maxyablochkin.demotaskapp.feature.main.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dev.maxyablochkin.demotaskapp.core.navigation.BottomTabNavHost
import com.dev.maxyablochkin.demotaskapp.core.navigation.LocalBottomTabNavController
import com.dev.maxyablochkin.demotaskapp.feature.categories.api.CategoriesGraph
import com.dev.maxyablochkin.demotaskapp.feature.home.api.HomeGraph

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    state: MainState,
    onAction: (MainAction) -> Unit,
    tabGraphs: NavGraphBuilder.() -> Unit
) {
    val bottomTabNavController = LocalBottomTabNavController.current
    val selectedTab = bottomTabNavController.rememberSelectedTab(initialTab = state.selectedTab)

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == NavigationTab.HOME,
                    onClick = dropUnlessResumed {
                        onAction(MainAction.OnNavigateTabClick(NavigationTab.HOME))
                    },
                    icon = { Text("📰") },
                    label = { Text("Головна") }
                )
                NavigationBarItem(
                    selected = selectedTab == NavigationTab.CATEGORIES,
                    onClick = dropUnlessResumed {
                        onAction(MainAction.OnNavigateTabClick(NavigationTab.CATEGORIES))
                    },
                    icon = { Text("🏷️") },
                    label = { Text("Категорії") }
                )
            }
        }
    ) { contentPadding ->
        BottomTabNavHost(
            modifier = Modifier.padding(contentPadding),
            navController = bottomTabNavController,
            startDestination = HomeGraph,
            navGraphBuilder = tabGraphs
        )
    }
}

@Composable
fun NavHostController.rememberSelectedTab(initialTab: NavigationTab): NavigationTab {
    var lastValue by rememberSaveable { mutableStateOf(initialTab) }
    val destination = currentBackStackEntryAsState().value?.destination

    val isHomeGraph = destination?.hierarchy?.any { it.hasRoute<HomeGraph>() } == true
    val isCategoriesGraph = destination?.hierarchy?.any { it.hasRoute<CategoriesGraph>() } == true

    val derived = when {
        isHomeGraph -> NavigationTab.HOME
        isCategoriesGraph -> NavigationTab.CATEGORIES
        else -> null
    }

    LaunchedEffect(derived) { derived?.let { lastValue = it } }

    return derived ?: lastValue
}

enum class NavigationTab { HOME, CATEGORIES }

