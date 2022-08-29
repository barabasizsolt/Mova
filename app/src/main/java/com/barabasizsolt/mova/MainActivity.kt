package com.barabasizsolt.mova

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.barabasizsolt.mova.ui.screen.splash.SplashScreen
import com.barabasizsolt.mova.ui.theme.MovaTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovaTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = isSystemInDarkTheme())
                systemUiController.setNavigationBarColor(color = Color.Transparent, darkIcons = false)

                TabNavigator(tab = SplashScreen)
            }
        }
    }
}