package com.barabasizsolt.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.barabasizsolt.catalog.ErrorContent
import com.barabasizsolt.catalog.LoadingContent
import com.barabasizsolt.catalog.MovaSnackBar
import com.barabasizsolt.catalog.PeopleCarousel
import com.barabasizsolt.catalog.WatchablePager
import com.barabasizsolt.catalog.WatchableWithRatingCarousel
import com.barabasizsolt.theme.attributes.AppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(screenState: HomeScreenState) {
    val snackBarHostState = remember { SnackbarHostState() }

    Box(modifier = Modifier.background(color = AppTheme.colors.primary)) {
        when (screenState.state) {
            is HomeScreenState.State.Error -> ErrorContent(onRetry = { screenState.getScreenData(swipeRefresh = false) })
            is HomeScreenState.State.Loading -> LoadingContent()
            else -> ScreenContent(screenState = screenState)
        }

        MovaSnackBar(
            snackBarHostState = snackBarHostState,
            onDismiss = { snackBarHostState.currentSnackbarData?.dismiss() }
        )

        LaunchedEffect(
            key1 = screenState.state,
            block = {
                if (screenState.state is HomeScreenState.State.ShowSnackBar) {
                    snackBarHostState.showSnackbar(
                        message = "Oops, something went wrong.",
                        actionLabel = "Try again"
                    )
                }
            }
        )
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
                    pagerContent = screenState.homeContent?.upcomingMovies.orEmpty(),
                    onClick = { /*TODO: Implement it*/ },
                    onPlayButtonClicked = { /*TODO: Implement it*/ },
                    onAddToFavouriteButtonClicked = { /*TODO: Implement it*/ }
                )
            }
            item {
                WatchableWithRatingCarousel(
                    header = stringResource(id = com.barabasizsolt.util.R.string.popular_movies),
                    buttonText = stringResource(id = com.barabasizsolt.util.R.string.more_popular_movies),
                    items = screenState.homeContent?.popularMovies.orEmpty(),
                    onItemClick = { /*TODO: Implement it*/ },
                    onHeaderClick = screenState::onSeeAllPopularMoviesClicked,
                )
            }
            item {
                PeopleCarousel(
                    header = stringResource(id = com.barabasizsolt.util.R.string.popular_people),
                    items = screenState.homeContent?.popularPeople.orEmpty(),
                    onItemClick = { /*TODO: Implement it*/ },
                    onHeaderClick = screenState::onSeeAllPopularPeopleClicked,
                )
            }
            item {
                WatchableWithRatingCarousel(
                    header = stringResource(id = com.barabasizsolt.util.R.string.now_playing_movies),
                    buttonText = stringResource(id = com.barabasizsolt.util.R.string.more_now_playing_movies),
                    items = screenState.homeContent?.nowPlayingMovies.orEmpty(),
                    onItemClick = { /*TODO: Implement it*/ },
                    onHeaderClick = screenState::onSeeAllNowPlayingMoviesClicked,
                )
            }
            item {
                WatchableWithRatingCarousel(
                    header = stringResource(id = com.barabasizsolt.util.R.string.top_rated_movies),
                    buttonText = stringResource(id = com.barabasizsolt.util.R.string.more_top_rated_movies),
                    items = screenState.homeContent?.topRatedMovies.orEmpty(),
                    onItemClick = { /*TODO: Implement it*/ },
                    onHeaderClick = screenState::onSeeAllTopRatedMoviesClicked,
                    showDivider = false
                )
            }
        }
    }
}