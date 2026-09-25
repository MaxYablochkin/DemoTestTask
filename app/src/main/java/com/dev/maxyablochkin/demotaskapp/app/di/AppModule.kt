package com.dev.maxyablochkin.demotaskapp.app.di

import com.dev.maxyablochkin.demotaskapp.core.data.di.coreDataModule
import com.dev.maxyablochkin.demotaskapp.core.database.di.coreDatabaseModule
import com.dev.maxyablochkin.demotaskapp.core.domain.di.coreDomainModule
import com.dev.maxyablochkin.demotaskapp.core.network.di.coreNetworkModule
import com.dev.maxyablochkin.demotaskapp.feature.categories.presentation.di.categoriesPresentationModule
import com.dev.maxyablochkin.demotaskapp.feature.home.presentation.di.homePresentationModule
import org.koin.dsl.module

val appModule = module {
    includes(
        coreModules,
        homeFeatureModule,
        categoriesFeatureModule
    )
}

val coreModules = module {
    includes(
        coreDataModule,
        coreDomainModule,
        coreDatabaseModule,
        coreNetworkModule
    )
}

val homeFeatureModule = module {
    includes(homePresentationModule)
}

val categoriesFeatureModule = module {
    includes(categoriesPresentationModule)
}
