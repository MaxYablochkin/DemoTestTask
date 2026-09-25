package com.dev.maxyablochkin.demotaskapp.feature.home.presentation.model

data class ArticleUiModel(
    val id: String,
    val title: String,
    val imageUrl: String,
    val sourceName: String,
    val formattedDate: String,
    val content: String,
    val url: String
)