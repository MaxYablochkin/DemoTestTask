package com.dev.maxyablochkin.demotaskapp.feature.home.presentation.feed

sealed interface HomeAction {
    data class OnSearchQueryChanged(val query: String) : HomeAction
    data object OnClearSearch : HomeAction
    data object OnLoadMore : HomeAction
    data object OnRefresh : HomeAction
}
