package com.dev.maxyablochkin.demotaskapp.core.data.di

import com.dev.maxyablochkin.demotaskapp.core.data.repository.NewsArticleRepositoryImpl
import com.dev.maxyablochkin.demotaskapp.core.domain.repository.NewsArticleRepository
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single

val coreDataModule = module {
    single<NewsArticleRepositoryImpl>() bind NewsArticleRepository::class
}
