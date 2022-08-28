package com.barabasizsolt.mova

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.barabasizsolt.mova.ui.screen.splash.SplashScreen
import com.barabasizsolt.mova.ui.theme.MovaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovaTheme {
                TabNavigator(tab = SplashScreen)
            }
        }
    }
}