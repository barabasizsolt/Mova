package com.barabasizsolt.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.barabasizsolt.base.BaseScreen
import com.barabasizsolt.base.UserAction
import com.barabasizsolt.catalog.PeopleCarousel
import com.barabasizsolt.catalog.WatchablePager
import com.barabasizsolt.catalog.WatchableWithRatingCarousel
import com.barabasizsolt.domain.model.ContentItem
import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.theme.AppTheme

@Composable
fun HomeScreen(screenState: HomeScreenState) = BaseScreen(
    screenState = screenState,
    onSnackBarDismissed = { screenState.getScreenData(userAction = UserAction.Normal) },
    content = { gridState, _ ->
        ScreenContent(
            upcomingMovies = screenState.homeContent.upcomingMovies,
            popularMovies = screenState.homeContent.popularMovies,
            onSeeAllPopularMoviesClicked = screenState::onSeeAllPopularMoviesClicked,
            popularPeople = screenState.homeContent.popularPeople,
            onSeeAllPopularPeopleClicked = screenState::onSeeAllPopularPeopleClicked,
            nowPlayingMovies = screenState.homeContent.nowPlayingMovies,
            onSeeAllNowPlayingMoviesClicked = screenState::onSeeAllNowPlayingMoviesClicked,
            topRatedMovies = screenState.homeContent.topRatedMovies,
            onSeeAllTopRatedMoviesClicked = screenState::onSeeAllTopRatedMoviesClicked,
            gridState = gridState,
            genres = screenState.homeContent.genres,
            onItemClicked = screenState::onMovieClicked
        )
    }
)

@Composable
private fun ScreenContent(
    upcomingMovies: List<Movie>,
    popularMovies: List<ContentItem.Watchable>,
    onSeeAllPopularMoviesClicked: () -> Unit,
    popularPeople: List<ContentItem.Person>,
    onSeeAllPopularPeopleClicked: () -> Unit,
    nowPlayingMovies: List<ContentItem.Watchable>,
    onSeeAllNowPlayingMoviesClicked: () -> Unit,
    topRatedMovies: List<ContentItem.Watchable>,
    onSeeAllTopRatedMoviesClicked: () -> Unit,
    gridState: LazyGridState,
    genres: Map<Long, String>,
    onItemClicked: (Int) -> Unit
) = LazyVerticalGrid(
    columns = GridCells.Fixed(count = 1),
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.screenPadding),
    contentPadding = PaddingValues(bottom = AppTheme.dimens.screenPadding),
    state = gridState
) {
    item {
        WatchablePager(
            pagerContent = upcomingMovies,
            genres = genres,
            onClick = onItemClicked,
            onPlayButtonClicked = { /*TODO: Implement it*/ },
            onAddToFavouriteButtonClicked = { /*TODO: Implement it*/ }
        )
    }
    item {
        WatchableWithRatingCarousel(
            header = stringResource(id = com.barabasizsolt.util.R.string.popular_movies),
            buttonText = stringResource(id = com.barabasizsolt.util.R.string.more_popular_movies),
            items = popularMovies,
            onItemClick = onItemClicked,
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
            onItemClick = onItemClicked,
            onHeaderClick = onSeeAllNowPlayingMoviesClicked,
        )
    }
    item {
        WatchableWithRatingCarousel(
            header = stringResource(id = com.barabasizsolt.util.R.string.top_rated_movies),
            buttonText = stringResource(id = com.barabasizsolt.util.R.string.more_top_rated_movies),
            items = topRatedMovies,
            onItemClick = onItemClicked,
            onHeaderClick = onSeeAllTopRatedMoviesClicked,
            showDivider = false
        )
    }
}