package com.dev.maxyablochkin.demotaskapp.feature.home.presentation.navigation

import kotlinx.serialization.Serializable

internal sealed interface HomeDestination {
    @Serializable
    data object Feed : HomeDestination
}
