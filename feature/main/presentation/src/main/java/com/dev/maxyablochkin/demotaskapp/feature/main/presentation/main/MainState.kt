package com.dev.maxyablochkin.demotaskapp.feature.main.presentation.main

import com.dev.maxyablochkin.demotaskapp.core.domain.model.Article

data class MainState(
    val selectedTab: NavigationTab = NavigationTab.HOME,
    val data: List<Article>? = emptyList(),
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val error: String = ""
)
