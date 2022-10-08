package com.barabasizsolt.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.barabasizsolt.catalog.ErrorContent
import com.barabasizsolt.catalog.LoadingContent
import com.barabasizsolt.catalog.PeopleCarousel
import com.barabasizsolt.catalog.WatchablePager
import com.barabasizsolt.catalog.WatchableWithRatingCarousel
import com.barabasizsolt.theme.attributes.AppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(screenState: HomeScreenState) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.primary)
                .padding(paddingValues = it)
        ) {
            when (val state = screenState.state) {
                is HomeScreenState.State.Error -> {
                    println("Error: ${state.message}")
                    ErrorContent(onRetry = { screenState.getScreenData(swipeRefresh = false) })
                }
                is HomeScreenState.State.Loading -> LoadingContent()
                else -> ScreenContent(screenState = screenState)
            }

            LaunchedEffect(
                key1 = screenState.state,
                block = {
                    if (screenState.state is HomeScreenState.State.ShowSnackBar) {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "Oops, something went wrong.",
                            actionLabel = "Try again"
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun ScreenContent(screenState: HomeScreenState) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = screenState.state is HomeScreenState.State.SwipeRefresh)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { screenState.getScreenData(swipeRefresh = true) }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.screenPadding),
            contentPadding = PaddingValues(bottom = AppTheme.dimens.screenPadding)
        ) {
            item {
                WatchablePager(
                    pagerContent = screenState.homeContent?.nowPlayingMovies.orEmpty(),
                    onClick = { },
                    onPlayButtonClicked = { },
                    onAddToFavouriteButtonClicked = { }
                )
            }
            item {
                WatchableWithRatingCarousel(
                    header = "Popular Movies",
                    buttonText = "More Popular Movies",
                    items = screenState.homeContent?.trendingMovies.orEmpty(),
                    onItemClick = { },
                    onHeaderClick = { },
                )
            }
            item {
                PeopleCarousel(
                    header = "Popular People",
                    items = screenState.homeContent?.popularPeople.orEmpty(),
                    onItemClick = { },
                    onHeaderClick = { },
                )
            }
            item {
                WatchableWithRatingCarousel(
                    header = "Now Playing Movies",
                    buttonText = "More Now Playing Movies",
                    items = screenState.homeContent?.upcomingMovies.orEmpty(),
                    onItemClick = { },
                    onHeaderClick = { },
                )
            }
            item {
                WatchableWithRatingCarousel(
                    header = "Top Rated Movies",
                    buttonText = "More Top Rated Movies",
                    items = screenState.homeContent?.topRatedMovies.orEmpty(),
                    onItemClick = { },
                    onHeaderClick = { },
                    showDivider = false
                )
            }
        }
    }
}