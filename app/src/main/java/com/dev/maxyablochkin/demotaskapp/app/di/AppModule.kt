package com.dev.maxyablochkin.demotaskapp.app.di

import com.dev.maxyablochkin.demotaskapp.core.data.di.coreDataModule
import com.dev.maxyablochkin.demotaskapp.core.database.di.coreDatabaseModule
import com.dev.maxyablochkin.demotaskapp.core.domain.di.coreDomainModule
import com.dev.maxyablochkin.demotaskapp.core.network.di.coreNetworkModule
import com.dev.maxyablochkin.demotaskapp.feature.categories.presentation.di.categoriesPresentationModule
import com.dev.maxyablochkin.demotaskapp.feature.home.presentation.di.homePresentationModule
import com.dev.maxyablochkin.demotaskapp.feature.main.presentation.di.mainPresentationModule
import org.koin.dsl.module

val coreModules = module {
    includes(coreDataModule, coreDomainModule, coreDatabaseModule, coreNetworkModule)
}

val mainFeatureModule = module {
    includes(mainPresentationModule)
}

val homeFeatureModule = module {
    includes(homePresentationModule)
}

val categoriesFeatureModule = module {
    includes(categoriesPresentationModule)
}

val appModule = module {
    includes(coreModules, mainFeatureModule, homeFeatureModule, categoriesFeatureModule)
}
