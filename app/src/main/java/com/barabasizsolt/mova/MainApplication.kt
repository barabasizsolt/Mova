package com.barabasizsolt.mova

import android.app.Application
import com.barabasizsolt.auth.createAuthModules
import com.barabasizsolt.domain.di.createDomainModules
import com.barabasizsolt.di.createNetworkModule
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
            modules(
                modules = buildList {
                    add(createNetworkModule(baseUrl = BuildConfig.BASE_URL, apiKey = BuildConfig.API_KEY, clientId = BuildConfig.CLIENT_ID))
                    addAll(createAuthModules())
                    addAll(createDomainModules())
                }
            )
        }
    }
}