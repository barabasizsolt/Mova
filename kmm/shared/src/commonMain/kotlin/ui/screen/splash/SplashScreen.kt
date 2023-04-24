package ui.screen.splash

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

object SplashScreen : Screen {

    @Composable
    override fun Content() {
        Spacer(modifier = Modifier.fillMaxSize())
    }
}