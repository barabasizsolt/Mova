package com.barabasizsolt.mova.ui.screen.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.barabasizsolt.mova.ui.screen.auth.login.LoginScreen
import com.barabasizsolt.mova.ui.screen.main.home.HomeScreen

object SplashScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel { SplashScreenModel() }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Splash",
                style = MaterialTheme.typography.h4
            )
            Button(
                onClick = {
                    navigator.replace(item = LoginScreen)
                },
                content = {
                    Text(
                        text = "Authentication",
                        style = MaterialTheme.typography.h5
                    )
                },
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Button(
                onClick = {
                    navigator.replace(item = HomeScreen)
                },
                content = {
                    Text(
                        text = "Home",
                        style = MaterialTheme.typography.h5
                    )
                }
            )
        }
    }
}