package com.barabasizsolt.mova

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.barabasizsolt.navigation.navigation.AppNavigation
import com.barabasizsolt.theme.attributes.MovaTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
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
}