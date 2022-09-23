package com.barabasizsolt.navigation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.barabasizsolt.domain.usecase.auth.IsLoggedInUseCase
import org.koin.androidx.compose.inject

@Composable
fun AppNavigation() = TabNavigator(tab = AppNavigationTab)

object AppNavigationTab : Tab {

    override val options: TabOptions
        @Composable
        get() = remember { TabOptions(index = 0u, title = "splash", icon = null) }

    @Composable
    override fun Content() {

        val navigator = LocalTabNavigator.current
        val isLogged by inject<IsLoggedInUseCase>()
        navigator.current = if (isLogged()) MainNavigation else AuthNavigation
    }
}