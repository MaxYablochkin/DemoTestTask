package com.dev.maxyablochkin.demotaskapp.feature.home.presentation.di

import com.dev.maxyablochkin.demotaskapp.feature.home.presentation.feed.HomeViewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

val homePresentationModule = module {
    viewModel<HomeViewModel>()
}
