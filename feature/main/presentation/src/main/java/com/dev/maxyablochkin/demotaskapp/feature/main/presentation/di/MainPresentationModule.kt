package com.dev.maxyablochkin.demotaskapp.feature.main.presentation.di

import com.dev.maxyablochkin.demotaskapp.feature.main.presentation.main.MainViewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

val mainPresentationModule = module {
    viewModel<MainViewModel>()
}
