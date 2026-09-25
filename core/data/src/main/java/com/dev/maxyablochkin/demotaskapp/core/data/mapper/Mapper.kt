package com.dev.maxyablochkin.demotaskapp.core.data.mapper

import com.dev.maxyablochkin.demotaskapp.core.database.entity.ArticleEntity
import com.dev.maxyablochkin.demotaskapp.core.domain.model.Article
import com.dev.maxyablochkin.demotaskapp.core.domain.model.Source
import com.dev.maxyablochkin.demotaskapp.core.network.dto.ArticleDto

fun ArticleDto.toDomain(): Article {
    return Article(
        id = url.orEmpty(),
        title = title.orEmpty(),
        urlToImage = urlToImage.orEmpty(),
        source = Source(id = source?.id, name = source?.name.orEmpty()),
        publishedAt = publishedAt.orEmpty(),
        content = content.orEmpty(),
        url = url.orEmpty()
    )
}

fun ArticleEntity.toDomain(): Article {
    return Article(
        id = url,
        title = title.orEmpty(),
        urlToImage = urlToImage.orEmpty(),
        source = Source(id = null, name = sourceName.orEmpty()),
        publishedAt = publishedAt.orEmpty(),
        content = content.orEmpty(),
        url = url
    )
}

fun Article.toEntity(category: String = "", page: Int = 1): ArticleEntity {
    return ArticleEntity(
        url = url.ifEmpty { id },
        title = title,
        urlToImage = urlToImage,
        sourceName = source.name,
        publishedAt = publishedAt,
        content = content,
        category = category,
        page = page
    )
}
