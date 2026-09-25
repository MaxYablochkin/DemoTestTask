package com.dev.maxyablochkin.demotaskapp.feature.home.presentation.feed

import androidx.lifecycle.viewModelScope
import com.dev.maxyablochkin.demotaskapp.core.domain.usecase.GetHomeNewsUseCase
import com.dev.maxyablochkin.demotaskapp.core.domain.usecase.SearchNewsUseCase
import com.dev.maxyablochkin.demotaskapp.core.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomeNewsUseCase: GetHomeNewsUseCase,
    private val searchNewsUseCase: SearchNewsUseCase
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
            getHomeNewsUseCase.getArticlesFlow().collect { list ->
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
                searchNewsUseCase.searchLocalArticlesFlow(query).collect { localList ->
                    updateState { copy(searchResults = localList) }
                }
            }

            delay(600)
            if (query.length >= 3) {
                searchNewsUseCase.searchNews(query = query, page = 1)
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

            val result = getHomeNewsUseCase.fetchHomeNews(page = page, isRefresh = isRefresh)

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
