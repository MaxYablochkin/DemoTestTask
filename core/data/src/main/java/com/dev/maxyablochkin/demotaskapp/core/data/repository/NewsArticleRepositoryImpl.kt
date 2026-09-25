package com.dev.maxyablochkin.demotaskapp.core.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.dev.maxyablochkin.demotaskapp.core.data.mapper.toDomain
import com.dev.maxyablochkin.demotaskapp.core.database.dao.ArticleDao
import com.dev.maxyablochkin.demotaskapp.core.database.entity.ArticleEntity
import com.dev.maxyablochkin.demotaskapp.core.domain.model.Article
import com.dev.maxyablochkin.demotaskapp.core.domain.repository.NewsArticleRepository
import com.dev.maxyablochkin.demotaskapp.core.network.NewsApiService
import com.dev.maxyablochkin.demotaskapp.core.network.dto.ArticleDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.collections.isNotEmpty
import androidx.core.content.edit

class NewsArticleRepositoryImpl(
    context: Context,
    private val articleDao: ArticleDao,
    private val apiService: NewsApiService
) : NewsArticleRepository {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("news_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val TAG = "NewsArticleRepository"
        private const val PREF_API_KEY = "custom_news_api_key"
        const val PAGE_SIZE = 15
    }

    override fun getArticlesByCategoryFlow(category: String): Flow<List<Article>> {
        return articleDao.getArticlesByCategory(category).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getBookmarkedArticlesFlow(): Flow<List<Article>> {
        return articleDao.getBookmarkedArticles().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun searchLocalArticlesFlow(query: String): Flow<List<Article>> {
        return articleDao.searchLocalArticles(query).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getArticleByUrlFlow(url: String): Flow<Article?> {
        return articleDao.getArticleByUrl(url).map { entity ->
            entity?.toDomain()
        }
    }

    override fun getApiKey(): String {
        return prefs.getString(PREF_API_KEY, null)?.trim().orEmpty()
    }

    override fun saveApiKey(key: String) {
        prefs.edit { putString(PREF_API_KEY, key.trim()) }
    }

    override suspend fun fetchCategoryNews(
        category: String,
        page: Int,
        isRefresh: Boolean
    ): Result<Int> = withContext(Dispatchers.IO) {
        val effectiveCategory = if (category == "headlines") null else category
        val dbCategoryKey = category.lowercase()
        val apiKey = getApiKey()

        if (apiKey.isBlank()) {
            seedInitialCategoryDataIfNeeded(dbCategoryKey, page)
            return@withContext Result.success(if (page <= 2) 5 else 0)
        }

        try {
            val response = apiService.getTopHeadlines(
                country = "us",
                category = effectiveCategory,
                page = page,
                pageSize = PAGE_SIZE,
                apiKey = apiKey
            )

            if (response.status == "ok" && !response.articles.isNullOrEmpty()) {
                if (isRefresh && page == 1) {
                    articleDao.clearCategoryNonBookmarked(dbCategoryKey)
                }

                val entities = response.articles.orEmpty()
                    .filter { !it.title.isNullOrBlank() && !it.url.isNullOrBlank() }
                    .map { it.toEntity(dbCategoryKey, page) }

                articleDao.saveArticlesPreservingBookmarks(entities)
                Result.success(entities.size)
            } else {
                val msg = response.message ?: "No articles received from NewsAPI"
                Log.w(TAG, "NewsAPI warning: $msg")
                seedInitialCategoryDataIfNeeded(dbCategoryKey, page)
                Result.success(0)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Network error fetching news: ${e.message}", e)
            seedInitialCategoryDataIfNeeded(dbCategoryKey, page)
            Result.failure(e)
        }
    }

    override suspend fun searchNews(query: String, page: Int): Result<Int> =
        withContext(Dispatchers.IO) {
            val apiKey = getApiKey()
            val searchCategoryKey = "search:${query.trim().lowercase()}"

            if (apiKey.isBlank() || query.isBlank()) {
                return@withContext Result.success(0)
            }

            try {
                val response = apiService.searchEverything(
                    query = query,
                    page = page,
                    pageSize = PAGE_SIZE,
                    apiKey = apiKey
                )

                if (response.status == "ok" && !response.articles.isNullOrEmpty()) {

                    val entities = response.articles.orEmpty()
                        .filter { !it.title.isNullOrBlank() && !it.url.isNullOrBlank() }
                        .map { it.toEntity(searchCategoryKey, page) }

                    articleDao.saveArticlesPreservingBookmarks(entities)
                    Result.success(entities.size)
                } else {
                    Result.success(0)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Search network error: ${e.message}", e)
                Result.failure(e)
            }
        }

    override suspend fun toggleBookmark(url: String, currentStatus: Boolean) =
        withContext(Dispatchers.IO) {
            articleDao.updateBookmark(url, !currentStatus)
        }

    private suspend fun seedInitialCategoryDataIfNeeded(category: String, page: Int) {
        if (page > 2) return
        val sampleArticles = getSampleArticles(category, page)
        if (sampleArticles.isNotEmpty()) {
            articleDao.saveArticlesPreservingBookmarks(sampleArticles)
        }
    }

    private fun ArticleDto.toEntity(category: String, page: Int): ArticleEntity {
        return ArticleEntity(
            url = this.url ?: "urn:uuid:${System.nanoTime()}",
            title = this.title ?: "Без заголовка",
            description = this.description,
            content = this.content,
            author = this.author,
            sourceName = this.source?.name ?: "NewsAPI",
            urlToImage = this.urlToImage,
            publishedAt = this.publishedAt,
            category = category,
            page = page,
            isBookmarked = false,
            cachedAt = System.currentTimeMillis()
        )
    }

    private fun getSampleArticles(category: String, page: Int): List<ArticleEntity> {
        val now = System.currentTimeMillis()
        val suffix = if (page == 1) "Головні події" else "Аналітика та тренди"

        return when (category) {
            "business" -> listOf(
                ArticleEntity(
                    url = "https://example.com/biz-1-p$page",
                    title = "Глобальні ринки демонструють рекордне зростання ($suffix)",
                    description = "Інвестори збільшують фінансування стартапів у сфері штучного інтелекту.",
                    content = "Провідні біржові індекси оновили історичні максимуми завдяки зростанню капіталізації.",
                    author = "Олександр Коваленко",
                    sourceName = "Bloomberg UA",
                    urlToImage = "https://images.unsplash.com/photo-1611974789855-9c2a0a7236a3?w=800",
                    publishedAt = "2026-09-24T18:00:00Z",
                    category = "business",
                    page = page,
                    cachedAt = now
                )
            )
            "technology" -> listOf(
                ArticleEntity(
                    url = "https://example.com/tech-1-p$page",
                    title = "Революція нейромереж ($suffix)",
                    description = "Штучний інтелект нового покоління здатен проектувати складні архітектурні рішення.",
                    content = "Інженери представили нейромережеву архітектуру, здатну інтегрувати логічний аналіз коду.",
                    author = "Андрій Бондаренко",
                    sourceName = "TechCrunch",
                    urlToImage = "https://images.unsplash.com/photo-1518770660439-4636190af475?w=800",
                    publishedAt = "2026-09-24T17:45:00Z",
                    category = "technology",
                    page = page,
                    cachedAt = now
                )
            )
            else -> listOf(
                ArticleEntity(
                    url = "https://example.com/gen-1-p$page",
                    title = "Глобальний саміт зі сталого розвитку ($suffix)",
                    description = "Делегації понад 120 країн підписали декларацію про перехід на відновлювану енергетику.",
                    content = "Угода передбачає масштабне фінансування інфраструктурних проектів.",
                    author = "Редакція VRG News",
                    sourceName = "Global News Network",
                    urlToImage = "https://images.unsplash.com/photo-1585829365295-ab7cd400c167?w=800",
                    publishedAt = "2026-09-24T18:45:00Z",
                    category = "general",
                    page = page,
                    cachedAt = now
                )
            )
        }
    }
}