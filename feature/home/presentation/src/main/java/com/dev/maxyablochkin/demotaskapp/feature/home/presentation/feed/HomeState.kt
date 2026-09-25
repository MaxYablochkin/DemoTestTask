package com.dev.maxyablochkin.demotaskapp.feature.home.presentation.feed

import com.dev.maxyablochkin.demotaskapp.core.domain.model.Article

data class HomeState(
    val articles: List<Article> = emptyList(),
    val searchResults: List<Article> = emptyList(),
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val page: Int = 1,
    val hasMore: Boolean = true
)
