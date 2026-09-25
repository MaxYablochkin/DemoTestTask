package com.dev.maxyablochkin.demotaskapp.app

import android.app.Application
import com.dev.maxyablochkin.demotaskapp.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DemoTaskAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DemoTaskAppApplication)
            modules(appModule)
        }
    }
}
