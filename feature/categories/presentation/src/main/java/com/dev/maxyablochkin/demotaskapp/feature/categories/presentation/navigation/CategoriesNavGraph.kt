package com.dev.maxyablochkin.demotaskapp.feature.categories.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.dev.maxyablochkin.demotaskapp.feature.categories.api.CategoriesGraph
import com.dev.maxyablochkin.demotaskapp.feature.categories.presentation.categories.CategoriesRoot

fun NavGraphBuilder.categoriesGraph() {
    navigation<CategoriesGraph>(startDestination = CategoriesDestination.Categories) {
        composable<CategoriesDestination.Categories> {
            CategoriesRoot()
        }
    }
}