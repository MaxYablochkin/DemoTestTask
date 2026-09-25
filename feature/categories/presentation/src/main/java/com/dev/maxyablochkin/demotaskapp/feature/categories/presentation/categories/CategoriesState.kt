package com.dev.maxyablochkin.demotaskapp.feature.categories.presentation.categories

import com.dev.maxyablochkin.demotaskapp.core.domain.model.Article
import com.dev.maxyablochkin.demotaskapp.core.domain.model.NewsCategory

data class CategoriesState(
    val articles: List<Article> = emptyList(),
    val selectedCategory: NewsCategory = NewsCategory.BUSINESS,
    val isLoading: Boolean = false,
    val page: Int = 1,
    val hasMore: Boolean = true
)
