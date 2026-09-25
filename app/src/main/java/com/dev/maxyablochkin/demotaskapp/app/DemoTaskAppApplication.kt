package com.dev.maxyablochkin.demotaskapp.app

import android.app.Application
import com.dev.maxyablochkin.demotaskapp.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class DemoTaskAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@DemoTaskAppApplication)
            modules(appModule)
        }
    }
}