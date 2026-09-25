package com.dev.maxyablochkin.demotaskapp.feature.home.data.mapper

import com.dev.maxyablochkin.demotaskapp.core.database.entity.ArticleEntity
import com.dev.maxyablochkin.demotaskapp.core.domain.model.Article
import com.dev.maxyablochkin.demotaskapp.core.domain.model.Source
import com.dev.maxyablochkin.demotaskapp.core.network.dto.ArticleDto

internal fun ArticleDto.toEntity(category: String = "", page: Int = 1): ArticleEntity {
    return ArticleEntity(
        url = url.orEmpty(),
        title = title.orEmpty(),
        urlToImage = urlToImage.orEmpty(),
        sourceName = source?.name.orEmpty(),
        publishedAt = publishedAt.orEmpty(),
        content = content.orEmpty(),
        category = category,
        page = page
    )
}

internal fun ArticleDto.toDomain(): Article {
    return Article(
        id = url.orEmpty(),
        title = title.orEmpty(),
        urlToImage = urlToImage.orEmpty(),
        source = Source(
            id = source?.id,
            name = source?.name.orEmpty()
        ),
        publishedAt = publishedAt.orEmpty(),
        content = content.orEmpty(),
        url = url.orEmpty()
    )
}

internal fun ArticleEntity.toDomain(): Article {
    return Article(
        id = url,
        title = title,
        urlToImage = urlToImage.orEmpty(),
        source = Source(
            id = null,
            name = sourceName.orEmpty()
        ),
        publishedAt = publishedAt.orEmpty(),
        content = content.orEmpty(),
        url = url.orEmpty()
    )
}