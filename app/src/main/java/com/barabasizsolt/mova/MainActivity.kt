package com.barabasizsolt.mova

import EntryPoint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import com.barabasizsolt.activityprovider.api.ActivitySetter
import com.barabasizsolt.mova.auth.api.AndroidAuthenticationService
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pandulapeter.beagle.Beagle
import org.koin.android.ext.android.inject

class MainActivity : FragmentActivity() {

    private val activitySetter by inject<ActivitySetter>()
    private val authService by inject<AndroidAuthenticationService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = rememberSystemUiController()
            systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = false)
            systemUiController.setNavigationBarColor(color = Color.Transparent, darkIcons = false)
            EntryPoint()
        }
    }

    override fun onResume() {
        super.onResume()
        activitySetter.set(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        activitySetter.clear()
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        authService.registerFacebookCallbackManager(requestCode = requestCode, resultCode = resultCode, data = data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean = when {
        handleDebugAction(keyCode = keyCode) -> true
        else -> super.onKeyDown(keyCode, event)
    }

    private fun handleDebugAction(keyCode: Int): Boolean = when {
        (BuildConfig.BUILD_TYPE == "debug") && keyCode == KeyEvent.KEYCODE_VOLUME_DOWN -> Beagle.show()
        (BuildConfig.BUILD_TYPE == "debug") && keyCode == KeyEvent.KEYCODE_VOLUME_UP -> Beagle.hide()
        else -> false
    }
}