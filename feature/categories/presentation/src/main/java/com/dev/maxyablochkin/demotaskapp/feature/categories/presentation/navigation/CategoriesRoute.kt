package com.dev.maxyablochkin.demotaskapp.feature.categories.presentation.navigation

import kotlinx.serialization.Serializable

internal sealed interface CategoriesDestination {
    @Serializable
    data object Categories : CategoriesDestination
}
