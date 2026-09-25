package com.dev.maxyablochkin.demotaskapp.feature.home.presentation.mapper

import com.dev.maxyablochkin.demotaskapp.core.domain.model.Article
import com.dev.maxyablochkin.demotaskapp.feature.home.presentation.model.ArticleUiModel

internal fun Article.toUiModel(): ArticleUiModel {
    return ArticleUiModel(
        id = url.ifEmpty { id },
        title = title,
        imageUrl = urlToImage,
        sourceName = source.name,
        formattedDate = publishedAt.take(10),
        content = content,
        url = url
    )
}