package com.dev.maxyablochkin.demotaskapp.core.database.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val url: String,
    val title: String,
    val description: String? = null,
    val content: String? = null,
    val author: String? = null,
    val sourceName: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val category: String = "general",
    val page: Int = 1,
    val isBookmarked: Boolean = false,
    val cachedAt: Long = System.currentTimeMillis()
)
