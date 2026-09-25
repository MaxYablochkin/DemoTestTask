package com.dev.maxyablochkin.demotaskapp.core.domain.usecase

import com.dev.maxyablochkin.demotaskapp.core.domain.model.Article
import com.dev.maxyablochkin.demotaskapp.core.domain.model.NewsCategory
import com.dev.maxyablochkin.demotaskapp.core.domain.repository.NewsArticleRepository
import kotlinx.coroutines.flow.Flow

class GetCategoryNewsUseCase(
    private val repository: NewsArticleRepository
) {
    fun getArticlesFlow(category: NewsCategory): Flow<List<Article>> {
        return repository.getArticlesByCategoryFlow(category.apiName)
    }

    suspend fun fetchCategoryNews(category: NewsCategory, page: Int): Result<Int> {
        return repository.fetchCategoryNews(category = category.apiName, page = page)
    }
}
