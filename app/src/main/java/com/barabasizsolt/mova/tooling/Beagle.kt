package com.barabasizsolt.mova.tooling

import android.app.Application
import com.barabasizsolt.mova.BuildConfig
import com.barabasizsolt.mova.R
import com.pandulapeter.beagle.Beagle
import com.pandulapeter.beagle.common.configuration.Behavior
import com.pandulapeter.beagle.common.configuration.Insets
import com.pandulapeter.beagle.common.configuration.toText
import com.pandulapeter.beagle.logOkHttp.BeagleOkHttpLogger
import com.pandulapeter.beagle.modules.*

fun setupBeagle(application: Application) {
    if (BuildConfig.BUILD_TYPE == "debug") {
        Beagle.initialize(
            application = application,
            behavior = Behavior(
                networkLogBehavior = Behavior.NetworkLogBehavior(
                    networkLoggers = listOf(BeagleOkHttpLogger)
                ),
                shakeDetectionBehavior = Behavior.ShakeDetectionBehavior(threshold = null),
                bugReportingBehavior = Behavior.BugReportingBehavior(
                    logRestoreLimit = 20, // By default this is 20. Decrement the value if the crash reporter feature does not work (!!! FAILED BINDER TRANSACTION !!! error - transaction too large). This is needed if the app has huge logs / network logs.
                    buildInformation = {
                        listOf(
                            "Version name".toText() to BuildConfig.VERSION_NAME,
                            "Version code".toText() to BuildConfig.VERSION_CODE.toString(),
                            "Application ID".toText() to BuildConfig.APPLICATION_ID
                        )
                    }
                )
            )
        )
        Beagle.set(
            HeaderModule(
                title = application.getString(R.string.app_name),
                subtitle = BuildConfig.APPLICATION_ID,
                text = "${BuildConfig.BUILD_TYPE} v${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
            ),
            AppInfoButtonModule(),
            DeveloperOptionsButtonModule(),
            PaddingModule(),
            TextModule(
                text = "General",
                type = TextModule.Type.SECTION_HEADER
            ),
            KeylineOverlaySwitchModule(applyInsets = { Insets() }),
            AnimationDurationSwitchModule(),
            ScreenCaptureToolboxModule(),
            BugReportButtonModule(),
            DividerModule(),
            TextModule(
                text = "Networking",
                type = TextModule.Type.SECTION_HEADER
            ),
            NetworkLogListModule(
                maxItemCount = 20
            ),
            DividerModule(),
            TextModule(
                text = "Other",
                type = TextModule.Type.SECTION_HEADER
            ),
            LogListModule(),
            DeviceInfoModule(),
            ForceCrashButtonModule()
        )
    }
}