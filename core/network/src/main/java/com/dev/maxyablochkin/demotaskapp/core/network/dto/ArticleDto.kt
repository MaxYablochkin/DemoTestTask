package com.dev.maxyablochkin.demotaskapp.core.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleDto(
    @param:Json(name = "source") val source: SourceDto? = null,
    @param:Json(name = "author") val author: String? = null,
    @param:Json(name = "title") val title: String? = null,
    @param:Json(name = "description") val description: String? = null,
    @param:Json(name = "url") val url: String? = null,
    @param:Json(name = "urlToImage") val urlToImage: String? = null,
    @param:Json(name = "publishedAt") val publishedAt: String? = null,
    @param:Json(name = "content") val content: String? = null
)