package com.dev.maxyablochkin.demotaskapp.core.domain.usecase

import com.dev.maxyablochkin.demotaskapp.core.domain.model.Article
import com.dev.maxyablochkin.demotaskapp.core.domain.repository.NewsArticleRepository
import kotlinx.coroutines.flow.Flow

class SearchNewsUseCase(
    private val repository: NewsArticleRepository
) {
    fun searchLocalArticlesFlow(query: String): Flow<List<Article>> {
        return repository.searchLocalArticlesFlow(query)
    }

    suspend fun searchNews(query: String, page: Int): Result<Int> {
        return repository.searchNews(query = query, page = page)
    }
}
