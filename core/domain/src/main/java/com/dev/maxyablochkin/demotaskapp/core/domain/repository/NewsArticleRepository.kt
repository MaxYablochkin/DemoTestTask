package com.dev.maxyablochkin.demotaskapp.core.domain.repository

import com.dev.maxyablochkin.demotaskapp.core.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsArticleRepository {
    fun getArticlesByCategoryFlow(category: String): Flow<List<Article>>
    fun getBookmarkedArticlesFlow(): Flow<List<Article>>
    fun searchLocalArticlesFlow(query: String): Flow<List<Article>>
    fun getArticleByUrlFlow(url: String): Flow<Article?>

    fun getApiKey(): String
    fun saveApiKey(key: String)

    suspend fun fetchCategoryNews(category: String, page: Int, isRefresh: Boolean = false): Result<Int>
    suspend fun searchNews(query: String, page: Int): Result<Int>
    suspend fun toggleBookmark(url: String, currentStatus: Boolean)
}