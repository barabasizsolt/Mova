package com.barabasizsolt.navigation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions

@Composable
fun AppNavigation() = TabNavigator(tab = AppNavigationTab)

object AppNavigationTab : Tab {

    override val options: TabOptions
        @Composable
        get() = remember { TabOptions(index = 12u, title = "splash", icon = null) }

    @Composable
    override fun Content() {

        val navigator = LocalTabNavigator.current

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
                onClick = { navigator.current = AuthNavigation },
                content = {
                    Text(
                        text = "Authentication",
                        style = MaterialTheme.typography.h5
                    )
                },
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Button(
                onClick = { navigator.current = MainNavigation },
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