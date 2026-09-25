package com.dev.maxyablochkin.demotaskapp.feature.main.presentation.navigation

import kotlinx.serialization.Serializable

internal sealed interface MainDestination {
    @Serializable
    data object Main : MainDestination
}
