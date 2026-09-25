package com.dev.maxyablochkin.demotaskapp.feature.categories.presentation.categories

import androidx.lifecycle.viewModelScope
import com.dev.maxyablochkin.demotaskapp.core.domain.model.NewsCategory
import com.dev.maxyablochkin.demotaskapp.core.domain.usecase.GetCategoryNewsUseCase
import com.dev.maxyablochkin.demotaskapp.core.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val getCategoryNewsUseCase: GetCategoryNewsUseCase
) : BaseViewModel<CategoriesState, CategoriesAction, CategoriesNavEvent, Nothing>(
    initialState = CategoriesState()
) {

    private var feedObserveJob: Job? = null

    init {
        val initialCategory = state.value.selectedCategory
        observeCategoryFeed(initialCategory)
        loadPage(category = initialCategory, page = 1)
    }

    override fun onAction(action: CategoriesAction) {
        when (action) {
            is CategoriesAction.OnCategorySelected -> onCategorySelected(action.category)
            CategoriesAction.OnLoadMore -> loadNextPage()
            is CategoriesAction.OnArticleClick -> launchNavEvent(CategoriesNavEvent.NavigateToArticle(action.article))
        }
    }

    private fun onCategorySelected(category: NewsCategory) {
        if (state.value.selectedCategory == category) return

        updateState {
            copy(
                selectedCategory = category,
                page = 1,
                hasMore = true,
                articles = emptyList()
            )
        }

        observeCategoryFeed(category)
        loadPage(category = category, page = 1)
    }

    private fun observeCategoryFeed(category: NewsCategory) {
        feedObserveJob?.cancel()
        feedObserveJob = viewModelScope.launch {
            getCategoryNewsUseCase.getArticlesFlow(category).collect { list ->
                updateState { copy(articles = list) }
            }
        }
    }

    private fun loadNextPage() {
        val currentState = state.value
        if (currentState.isLoading || !currentState.hasMore) return
        loadPage(category = currentState.selectedCategory, page = currentState.page + 1)
    }

    private fun loadPage(category: NewsCategory, page: Int) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }

            val result = getCategoryNewsUseCase.fetchCategoryNews(category = category, page = page)

            updateState {
                val fetchedCount = result.getOrDefault(0)
                copy(
                    isLoading = false,
                    page = page,
                    hasMore = fetchedCount >= 3
                )
            }
        }
    }
}
