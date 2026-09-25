package com.dev.maxyablochkin.demotaskapp.core.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsDto(
    @param:Json(name = "status") val status: String? = null,
    @param:Json(name = "totalResults") val totalResults: Int? = null,
    @param:Json(name = "articles") val articles: List<ArticleDto>? = null,
    @param:Json(name = "message") val message: String? = null
)