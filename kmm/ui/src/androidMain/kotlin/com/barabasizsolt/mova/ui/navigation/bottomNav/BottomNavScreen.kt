package com.barabasizsolt.mova.ui.navigation.bottomNav

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.barabasizsolt.mova.ui.screen.profile.ProfileScreen
import com.barabasizsolt.mova.ui.theme.AppTheme
import com.barabasizsolt.mova.ui.util.navigationBarInsetDp

object BottomNavScreen : Screen {

    @Composable
    override fun Content() {
        TabNavigator(tab = HomeTab) {
            Scaffold(
                content = {
                    Box(modifier = Modifier.fillMaxSize().padding(paddingValues = it)) {
                        CurrentTab()
                    }
                },
                bottomBar = {
                    BottomNavigation(
                        backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
                        modifier = Modifier.height(height = navigationBarInsetDp + AppTheme.dimens.bottomNavHeight),
                        elevation = 16.dp,
                    ) {
                        TabNavigationItem(tab = HomeTab)
                        TabNavigationItem(tab = ExploreTab)
                        TabNavigationItem(tab = ProfileScreen)
                    }
                }
            )
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) },
        label = { Text(text = tab.options.title, style = AppTheme.typography.bottomNav) },
        selected = tabNavigator.current == tab,
        selectedContentColor = AppTheme.colors.secondary,
        unselectedContentColor = Color.Gray,
        modifier = Modifier.navigationBarsPadding(),
        onClick = { tabNavigator.current = tab }
    )
}
