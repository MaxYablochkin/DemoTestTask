package com.dev.maxyablochkin.demotaskapp.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Any,
    navGraphBuilder: NavGraphBuilder.() -> Unit
) = LocalNavHost(
    modifier = modifier,
    localNavController = LocalAppNavController,
    navController = navController,
    startDestination = startDestination,
    enterTransition = { fadeIn(tween(150)) },
    exitTransition = { fadeOut(tween(150)) },
    popEnterTransition = { fadeIn(tween(150)) },
    popExitTransition = { fadeOut(tween(150)) },
    predictivePopEnterTransition = { fadeIn(tween(150)) },
    predictivePopExitTransition = { fadeOut(tween(150)) },
    navGraphBuilder = navGraphBuilder,
)


@Composable
fun BottomTabNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Any,
    navGraphBuilder: NavGraphBuilder.() -> Unit
) = LocalNavHost(
    modifier = modifier,
    localNavController = LocalBottomTabNavController,
    navController = navController,
    startDestination = startDestination,
    enterTransition = { fadeIn(tween(150)) },
    exitTransition = { fadeOut(tween(150)) },
    popEnterTransition = { fadeIn(tween(150)) },
    popExitTransition = { fadeOut(tween(150)) },
    predictivePopEnterTransition = { fadeIn(tween(150)) },
    predictivePopExitTransition = { fadeOut(tween(150)) },
    navGraphBuilder = navGraphBuilder
)

@Composable
fun LocalNavHost(
    modifier: Modifier = Modifier,
    localNavController: ProvidableCompositionLocal<NavHostController>,
    navController: NavHostController,
    startDestination: Any,
    enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
        { fadeIn(animationSpec = tween(700)) },
    exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
        { fadeOut(animationSpec = tween(700)) },
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) =
        enterTransition,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) =
        exitTransition,
    predictivePopEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.(Int) -> EnterTransition) = { EnterTransition.None },
    predictivePopExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.(Int) -> ExitTransition) = { ExitTransition.None },
    sizeTransform: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> SizeTransform?)? =
        null,
    navGraphBuilder: NavGraphBuilder.() -> Unit
) = CompositionLocalProvider(localNavController provides navController) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        predictivePopEnterTransition = predictivePopEnterTransition,
        predictivePopExitTransition = predictivePopExitTransition,
        sizeTransform = sizeTransform,
        builder = navGraphBuilder
    )
}