package com.dev.maxyablochkin.demotaskapp.core.domain.model

data class Article(
    val id: String,
    val title: String,
    val urlToImage: String,
    val source: Source = Source(null, ""),
    val publishedAt: String = "",
    val content: String = "",
    val url: String = id,
    val isSaved: Boolean = false
)
