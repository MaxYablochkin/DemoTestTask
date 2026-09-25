package com.dev.maxyablochkin.demotaskapp.feature.categories.presentation.categories

import com.dev.maxyablochkin.demotaskapp.core.domain.model.Article

sealed interface CategoriesNavEvent {
    data class NavigateToArticle(val article: Article) : CategoriesNavEvent
}