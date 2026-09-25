package com.dev.maxyablochkin.demotaskapp.feature.categories.presentation.di

import com.dev.maxyablochkin.demotaskapp.feature.categories.presentation.categories.CategoriesViewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

val categoriesPresentationModule = module {
    viewModel<CategoriesViewModel>()
}
