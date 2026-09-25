package com.dev.maxyablochkin.demotaskapp.core.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalAppNavController = staticCompositionLocalOf<NavHostController> {
    error("LocalAppNavController not provided")
}

val LocalBottomTabNavController = staticCompositionLocalOf<NavHostController> {
    error("LocalBottomTabNavController not provided")
}