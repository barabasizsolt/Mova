package com.barabasizsolt.mova

import android.app.Application
import com.barabasizsolt.api.AuthenticationService
import com.barabasizsolt.mova.shared.createAppModule
import com.barabasizsolt.mova.tooling.setupBeagle
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    private val authService: AuthenticationService by inject()

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
            authService.initialize(context = this@MainApplication)
        }
    }
}