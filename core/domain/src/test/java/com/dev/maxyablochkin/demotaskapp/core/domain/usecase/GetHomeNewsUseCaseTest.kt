package com.dev.maxyablochkin.demotaskapp.core.domain.usecase

import com.dev.maxyablochkin.demotaskapp.core.domain.model.Article
import com.dev.maxyablochkin.demotaskapp.core.domain.model.Source
import com.dev.maxyablochkin.demotaskapp.core.domain.repository.NewsArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetHomeNewsUseCaseTest {

    private val mockRepository = object : NewsArticleRepository {
        override fun getArticlesByCategoryFlow(category: String): Flow<List<Article>> {
            return flowOf(
                listOf(
                    Article(
                        id = "1",
                        title = "Test Headline",
                        urlToImage = "",
                        source = Source(id = "1", name = "Test Source"),
                        publishedAt = "2026-09-25",
                        content = "Content",
                        url = "https://example.com/1"
                    )
                )
            )
        }

        override fun getBookmarkedArticlesFlow(): Flow<List<Article>> = flowOf(emptyList())
        override fun searchLocalArticlesFlow(query: String): Flow<List<Article>> = flowOf(emptyList())
        override fun getArticleByUrlFlow(url: String): Flow<Article?> = flowOf(null)
        override fun getApiKey(): String = "test-key"
        override fun saveApiKey(key: String) {}
        override suspend fun fetchCategoryNews(category: String, page: Int, isRefresh: Boolean): Result<Int> = Result.success(1)
        override suspend fun searchNews(query: String, page: Int): Result<Int> = Result.success(0)
        override suspend fun toggleBookmark(url: String, currentStatus: Boolean) {}
    }

    private val useCase = GetHomeNewsUseCase(mockRepository)

    @Test
    fun `getArticlesFlow returns articles from general category`() = runTest {
        val articles = useCase.getArticlesFlow().first()
        assertEquals(1, articles.size)
        assertEquals("Test Headline", articles[0].title)
    }

    @Test
    fun `fetchHomeNews delegates call to repository`() = runTest {
        val result = useCase.fetchHomeNews(page = 1, isRefresh = false)
        assertEquals(true, result.isSuccess)
        assertEquals(1, result.getOrNull())
    }
}
