package com.dev.maxyablochkin.demotaskapp.core.domain.usecase

import com.dev.maxyablochkin.demotaskapp.core.domain.model.Article
import com.dev.maxyablochkin.demotaskapp.core.domain.repository.NewsArticleRepository
import kotlinx.coroutines.flow.Flow

class GetHomeNewsUseCase(
    private val repository: NewsArticleRepository
) {
    fun getArticlesFlow(): Flow<List<Article>> {
        return repository.getArticlesByCategoryFlow("general")
    }

    suspend fun fetchHomeNews(page: Int, isRefresh: Boolean): Result<Int> {
        return repository.fetchCategoryNews(category = "general", page = page, isRefresh = isRefresh)
    }
}
