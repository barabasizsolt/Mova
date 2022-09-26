package com.barabasizsolt.navigation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.barabasizsolt.domain.usecase.auth.IsLoggedInUseCase
import com.barabasizsolt.theme.attributes.AppTheme
import com.barabasizsolt.util.navigationBarInsetDp
import org.koin.androidx.compose.inject

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomItems = listOf(BottomNavItem.Home, BottomNavItem.Explore, BottomNavItem.Favourites, BottomNavItem.Profile)

    Column {
        NavHost(
            navController = navController,
            startDestination = Route.Splash.route,
            builder = {
                composable(route = Route.Splash.route) {
                    val isLoggedInUseCase by inject<IsLoggedInUseCase>()
                    LaunchedEffect(
                        key1 = Unit,
                        block = {
                            navController.navigate(route = if (isLoggedInUseCase()) Route.Main.route else Route.Authentication.route)
                        }
                    )
                }
                authNavigation(navController = navController)
                mainNavigation(navController = navController)
            },
            modifier = Modifier.weight(weight = 1f)
        )
        AnimatedVisibility(
            visible = bottomItems.map { it.route }.contains(element = navBackStackEntry?.destination?.route)
        ) {
            BottomNavigation(
                backgroundColor = AppTheme.colors.background,
                modifier = Modifier.height(height = navigationBarInsetDp + AppTheme.dimens.bottomNavHeight),
                content = {
                    val currentDestination = navBackStackEntry?.destination
                    bottomItems.forEach { item ->
                        BottomNavigationItem(
                            icon = { Icon(imageVector = item.icon , contentDescription = item.title) },
                            label = {
                                Text(
                                    text = item.title,
                                    style = AppTheme.typography.caption
                                )
                            },
                            selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                            selectedContentColor = AppTheme.colors.secondary,
                            unselectedContentColor = Color.Gray,
                            modifier = Modifier.systemBarsPadding(),
                            onClick = {
                                navController.navigate(route = item.route) {
                                    popUpTo(id = navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            )
        }
    }
}

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home: BottomNavItem(
        route = Route.Main.HOME,
        title = "Home",
        icon = Icons.Default.Home
    )
    object Explore: BottomNavItem(
        route = Route.Main.EXPLORE,
        title = "Explore",
        icon = Icons.Default.Search
    )
    object Favourites: BottomNavItem(
        route = Route.Main.FAVOURITES,
        title = "Favourites",
        icon = Icons.Default.Favorite
    )
    object Profile: BottomNavItem(
        route = Route.Main.PROFILE,
        title = "Profile",
        icon = Icons.Default.Person
    )
}