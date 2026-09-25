package com.dev.maxyablochkin.demotaskapp.core.database.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import com.dev.maxyablochkin.demotaskapp.core.database.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles WHERE category = :category ORDER BY cachedAt ASC, page ASC")
    fun getArticlesByCategory(category: String): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE isBookmarked = 1 ORDER BY cachedAt DESC")
    fun getBookmarkedArticles(): Flow<List<ArticleEntity>>

    @Query("""
        SELECT * FROM articles 
        WHERE title LIKE '%' || :query || '%' 
           OR description LIKE '%' || :query || '%' 
        ORDER BY cachedAt DESC
    """)
    fun searchLocalArticles(query: String): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE url = :url LIMIT 1")
    fun getArticleByUrl(url: String): Flow<ArticleEntity?>

    @Query("SELECT isBookmarked FROM articles WHERE url = :url LIMIT 1")
    suspend fun isBookmarked(url: String): Boolean?

    @Query("SELECT url FROM articles WHERE isBookmarked = 1")
    suspend fun getBookmarkedUrls(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("UPDATE articles SET isBookmarked = :isBookmarked WHERE url = :url")
    suspend fun updateBookmark(url: String, isBookmarked: Boolean)

    @Query("DELETE FROM articles WHERE category = :category AND isBookmarked = 0")
    suspend fun clearCategoryNonBookmarked(category: String)

    @Transaction
    suspend fun saveArticlesPreservingBookmarks(newArticles: List<ArticleEntity>) {
        val bookmarkedUrls = getBookmarkedUrls().toSet()
        val mapped = newArticles.map { article ->
            if (bookmarkedUrls.contains(article.url)) {
                article.copy(isBookmarked = true)
            } else {
                article
            }
        }
        insertArticles(mapped)
    }

    @Query("SELECT MAX(page) FROM articles WHERE category = :category")
    suspend fun getMaxPageForCategory(category: String): Int?
}
