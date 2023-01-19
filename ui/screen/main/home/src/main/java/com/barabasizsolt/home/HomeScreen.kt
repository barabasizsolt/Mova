package com.barabasizsolt.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.barabasizsolt.base.BaseScreen
import com.barabasizsolt.base.BaseScreenState
import com.barabasizsolt.catalog.PeopleCarousel
import com.barabasizsolt.catalog.WatchablePager
import com.barabasizsolt.catalog.WatchableWithRatingCarousel
import com.barabasizsolt.domain.model.ContentItem
import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.theme.AppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(screenState: HomeScreenState) = BaseScreen(
    screenState = screenState,
    onSnackBarDismissed = { screenState.getScreenData(isUserAction = false) },
    content = {
        ScreenContent(
            isRefreshing = screenState.state is BaseScreenState.State.UserAction,
            onRefresh = { screenState.getScreenData(isUserAction = true) },
            upcomingMovies = screenState.homeContent?.upcomingMovies.orEmpty(),
            popularMovies = screenState.homeContent?.popularMovies.orEmpty(),
            onSeeAllPopularMoviesClicked = screenState::onSeeAllPopularMoviesClicked,
            popularPeople = screenState.homeContent?.popularPeople.orEmpty(),
            onSeeAllPopularPeopleClicked = screenState::onSeeAllPopularPeopleClicked,
            nowPlayingMovies = screenState.homeContent?.nowPlayingMovies.orEmpty(),
            onSeeAllNowPlayingMoviesClicked = screenState::onSeeAllNowPlayingMoviesClicked,
            topRatedMovies = screenState.homeContent?.topRatedMovies.orEmpty(),
            onSeeAllTopRatedMoviesClicked = screenState::onSeeAllTopRatedMoviesClicked
        )
    }
)

@Composable
private fun ScreenContent(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    upcomingMovies: List<Movie>,
    popularMovies: List<ContentItem.Watchable>,
    onSeeAllPopularMoviesClicked: () -> Unit,
    popularPeople: List<ContentItem.Person>,
    onSeeAllPopularPeopleClicked: () -> Unit,
    nowPlayingMovies: List<ContentItem.Watchable>,
    onSeeAllNowPlayingMoviesClicked: () -> Unit,
    topRatedMovies: List<ContentItem.Watchable>,
    onSeeAllTopRatedMoviesClicked: () -> Unit
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    val listState: LazyListState = rememberLazyListState()
    val scope: CoroutineScope = rememberCoroutineScope()

    BackHandler(enabled = listState.firstVisibleItemScrollOffset > 0) {
        scope.launch {
            listState.scrollToItem(index = 0, scrollOffset = 0)
        }
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = onRefresh
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.screenPadding),
            contentPadding = PaddingValues(bottom = AppTheme.dimens.screenPadding),
            state = listState
        ) {
            item {
                WatchablePager(
                    pagerContent = upcomingMovies,
                    onClick = { /*TODO: Implement it*/ },
                    onPlayButtonClicked = { /*TODO: Implement it*/ },
                    onAddToFavouriteButtonClicked = { /*TODO: Implement it*/ }
                )
            }
            item {
                WatchableWithRatingCarousel(
                    header = stringResource(id = com.barabasizsolt.util.R.string.popular_movies),
                    buttonText = stringResource(id = com.barabasizsolt.util.R.string.more_popular_movies),
                    items = popularMovies,
                    onItemClick = { /*TODO: Implement it*/ },
                    onHeaderClick = onSeeAllPopularMoviesClicked,
                )
            }
            item {
                PeopleCarousel(
                    header = stringResource(id = com.barabasizsolt.util.R.string.popular_people),
                    items = popularPeople,
                    onItemClick = { /*TODO: Implement it*/ },
                    onHeaderClick = onSeeAllPopularPeopleClicked,
                )
            }
            item {
                WatchableWithRatingCarousel(
                    header = stringResource(id = com.barabasizsolt.util.R.string.now_playing_movies),
                    buttonText = stringResource(id = com.barabasizsolt.util.R.string.more_now_playing_movies),
                    items = nowPlayingMovies,
                    onItemClick = { /*TODO: Implement it*/ },
                    onHeaderClick = onSeeAllNowPlayingMoviesClicked,
                )
            }
            item {
                WatchableWithRatingCarousel(
                    header = stringResource(id = com.barabasizsolt.util.R.string.top_rated_movies),
                    buttonText = stringResource(id = com.barabasizsolt.util.R.string.more_top_rated_movies),
                    items = topRatedMovies,
                    onItemClick = { /*TODO: Implement it*/ },
                    onHeaderClick = onSeeAllTopRatedMoviesClicked,
                    showDivider = false
                )
            }
        }
    }
}