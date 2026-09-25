package com.dev.maxyablochkin.demotaskapp.core.domain.di

import com.dev.maxyablochkin.demotaskapp.core.domain.usecase.GetCategoryNewsUseCase
import com.dev.maxyablochkin.demotaskapp.core.domain.usecase.GetHomeNewsUseCase
import com.dev.maxyablochkin.demotaskapp.core.domain.usecase.NewsArticleUseCase
import com.dev.maxyablochkin.demotaskapp.core.domain.usecase.SearchNewsUseCase
import org.koin.dsl.module
import org.koin.plugin.module.dsl.factory

val coreDomainModule = module {
    factory<NewsArticleUseCase>()
    factory<GetHomeNewsUseCase>()
    factory<GetCategoryNewsUseCase>()
    factory<SearchNewsUseCase>()
}
