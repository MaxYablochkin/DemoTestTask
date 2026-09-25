package com.dev.maxyablochkin.demotaskapp.feature.home.presentation.feed

import androidx.lifecycle.viewModelScope
import com.dev.maxyablochkin.demotaskapp.core.domain.repository.NewsArticleRepository
import com.dev.maxyablochkin.demotaskapp.core.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: NewsArticleRepository
) : BaseViewModel<HomeState, HomeAction, Nothing, Nothing>(
    initialState = HomeState()
) {

    private var searchJob: Job? = null

    init {
        observeGeneralFeed()
        loadPage(page = 1, isRefresh = false)
    }

    override fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnSearchQueryChanged -> onSearchQueryChanged(action.query)
            HomeAction.OnClearSearch -> onClearSearch()
            HomeAction.OnLoadMore -> loadNextPage()
            HomeAction.OnRefresh -> onRefresh()
        }
    }

    private fun observeGeneralFeed() {
        viewModelScope.launch {
            repository.getArticlesByCategoryFlow("general").collect { list ->
                updateState { copy(articles = list) }
            }
        }
    }

    private fun onSearchQueryChanged(query: String) {
        updateState { copy(searchQuery = query, isSearching = query.isNotBlank()) }
        searchJob?.cancel()

        if (query.isBlank()) {
            updateState { copy(searchResults = emptyList()) }
            return
        }

        searchJob = viewModelScope.launch {
            launch {
                repository.searchLocalArticlesFlow(query).collect { localList ->
                    updateState { copy(searchResults = localList) }
                }
            }

            delay(600)
            if (query.length >= 3) {
                repository.searchNews(query = query, page = 1)
            }
        }
    }

    private fun onClearSearch() {
        updateState { copy(searchQuery = "", isSearching = false, searchResults = emptyList()) }
    }

    private fun onRefresh() {
        updateState { copy(isRefreshing = true) }
        loadPage(page = 1, isRefresh = true)
    }

    private fun loadNextPage() {
        val currentState = state.value
        if (currentState.isLoading || !currentState.hasMore || currentState.isSearching) return
        loadPage(page = currentState.page + 1, isRefresh = false)
    }

    private fun loadPage(page: Int, isRefresh: Boolean) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }

            val result = repository.fetchCategoryNews("general", page = page, isRefresh = isRefresh)

            updateState {
                val fetchedCount = result.getOrDefault(0)
                copy(
                    isLoading = false,
                    isRefreshing = false,
                    page = page,
                    hasMore = fetchedCount >= 5
                )
            }
        }
    }
}
