package com.barabasizsolt.mova

import android.app.Application
import com.barabasizsolt.api.AuthenticationService
import com.barabasizsolt.mova.di.createAppModule
import com.barabasizsolt.mova.tooling.setupBeagle
import com.pandulapeter.beagle.Beagle
import com.pandulapeter.beagle.common.configuration.Behavior
import com.pandulapeter.beagle.common.configuration.toText
import com.pandulapeter.beagle.logOkHttp.BeagleOkHttpLogger
import com.pandulapeter.beagle.modules.AnimationDurationSwitchModule
import com.pandulapeter.beagle.modules.AppInfoButtonModule
import com.pandulapeter.beagle.modules.DeveloperOptionsButtonModule
import com.pandulapeter.beagle.modules.DeviceInfoModule
import com.pandulapeter.beagle.modules.DividerModule
import com.pandulapeter.beagle.modules.ForceCrashButtonModule
import com.pandulapeter.beagle.modules.HeaderModule
import com.pandulapeter.beagle.modules.KeylineOverlaySwitchModule
import com.pandulapeter.beagle.modules.LogListModule
import com.pandulapeter.beagle.modules.NetworkLogListModule
import com.pandulapeter.beagle.modules.PaddingModule
import com.pandulapeter.beagle.modules.ScreenCaptureToolboxModule
import com.pandulapeter.beagle.modules.TextModule
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
            modules(modules = createAppModule())
            authService.initialize(context = this@MainApplication)
        }
    }
}