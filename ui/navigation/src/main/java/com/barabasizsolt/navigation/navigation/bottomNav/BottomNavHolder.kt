package com.barabasizsolt.navigation.navigation.bottomNav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.barabasizsolt.navigation.navigation.Route
import com.barabasizsolt.theme.AppTheme
import com.barabasizsolt.util.navigationBarInsetDp

@Composable
fun BottomNavHolder(navController: NavHostController, navBackStackEntry: NavBackStackEntry?) {
    AnimatedVisibility(
        visible = bottomItems.map { it.route }.contains(element = navBackStackEntry?.destination?.route)
    ) {
        BottomNavigation(
            backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
            modifier = Modifier.height(height = navigationBarInsetDp + AppTheme.dimens.bottomNavHeight),
            elevation = 16.dp,
            content = {
                val currentDestination = navBackStackEntry?.destination
                bottomItems.forEach { item ->
                    BottomNavigationItem(
                        icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                        label = { Text(text = item.title, style = AppTheme.typography.bottomNav) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        selectedContentColor = AppTheme.colors.secondary,
                        unselectedContentColor = Color.Gray,
                        modifier = Modifier.navigationBarsPadding(),
                        onClick = {
                            navController.navigate(route = item.route) {
                                popUpTo(route = Route.Main.HOME) {
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        )
    }
}