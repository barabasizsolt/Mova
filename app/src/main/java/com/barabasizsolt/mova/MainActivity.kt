package com.barabasizsolt.mova

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.barabasizsolt.activityprovider.api.ActivitySetter
import com.barabasizsolt.api.AuthenticationService
import com.barabasizsolt.navigation.navigation.AppNavigation
import com.barabasizsolt.theme.attributes.MovaTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val activitySetter by inject<ActivitySetter>()
    private val authService: AuthenticationService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MovaTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = false)
                systemUiController.setNavigationBarColor(color = Color.Transparent, darkIcons = false)
                AppNavigation()
            }
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        authService.registerFacebookCallbackManager(requestCode = requestCode, resultCode =resultCode, data = data)
    }
}