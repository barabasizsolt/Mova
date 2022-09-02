package com.barabasizsolt.mova

import android.app.Application
import com.barabasizsolt.mova.di.createAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(androidContext = this@MainApplication)
            modules(modules = createAppModule())
        }
    }
}