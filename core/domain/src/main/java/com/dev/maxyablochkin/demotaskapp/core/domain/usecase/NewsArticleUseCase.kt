package com.dev.maxyablochkin.demotaskapp.core.domain.usecase

import com.dev.maxyablochkin.demotaskapp.core.domain.model.Article
import com.dev.maxyablochkin.demotaskapp.core.domain.repository.NewsArticleRepository
import com.dev.maxyablochkin.demotaskapp.core.domain.util.RequestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsArticleUseCase(private val newsArticleRepository: NewsArticleRepository) {

    fun getCategoryNews(
        category: String = "general",
        page: Int = 1,
        isRefresh: Boolean = false
    ): Flow<RequestResult<List<Article>>> = flow {
        emit(RequestResult.Loading(""))
        try {
            val result = newsArticleRepository.fetchCategoryNews(category = category, page = page, isRefresh = isRefresh)
            if (result.isSuccess) {
                newsArticleRepository.getArticlesByCategoryFlow(category).collect { articles ->
                    emit(RequestResult.Success(articles))
                    return@collect
                }
            } else {
                emit(RequestResult.Error(result.exceptionOrNull()?.message ?: "Unknown error"))
            }
        } catch (e: Exception) {
            emit(RequestResult.Error(e.message ?: "Unknown error"))
        }
    }

    fun searchNews(
        query: String,
        page: Int = 1
    ): Flow<RequestResult<List<Article>>> = flow {
        if (query.isBlank()) {
            emit(RequestResult.Success(emptyList()))
            return@flow
        }

        emit(RequestResult.Loading(""))
        try {
            newsArticleRepository.searchNews(query = query, page = page)
            newsArticleRepository.searchLocalArticlesFlow(query).collect { articles ->
                emit(RequestResult.Success(articles))
                return@collect
            }
        } catch (e: Exception) {
            emit(RequestResult.Error(e.message ?: "Unknown error"))
        }
    }
}