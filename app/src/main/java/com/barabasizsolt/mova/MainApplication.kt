package com.barabasizsolt.mova

import android.app.Application
import createAppModule
import com.barabasizsolt.mova.tooling.setupBeagle
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
        setupBeagle(application = this)
    }

    private fun setupKoin() {
        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(androidContext = this@MainApplication)
            modules(
                modules = createAppModule(
                    host = BuildConfig.HOST,
                    apiKey = BuildConfig.API_KEY,
                    isDebugBuild = BuildConfig.DEBUG
                )
            )
        }
    }
}