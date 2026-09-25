package com.dev.maxyablochkin.demotaskapp.core.domain.di

import com.dev.maxyablochkin.demotaskapp.core.domain.usecase.NewsArticleUseCase
import org.koin.dsl.module
import org.koin.plugin.module.dsl.factory

val coreDomainModule = module {
    factory<NewsArticleUseCase>()
}
