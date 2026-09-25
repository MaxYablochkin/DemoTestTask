package com.dev.maxyablochkin.demotaskapp.feature.categories.presentation.categories

import com.dev.maxyablochkin.demotaskapp.core.domain.model.Article
import com.dev.maxyablochkin.demotaskapp.core.domain.model.NewsCategory

sealed interface CategoriesAction {
    data class OnCategorySelected(val category: NewsCategory) : CategoriesAction
    data object OnLoadMore : CategoriesAction
    data class OnArticleClick(val article: Article) : CategoriesAction
}
