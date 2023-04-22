package com.barabasizsolt.mova.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.barabasizsolt.mova.domain.model.ContentItem
import com.barabasizsolt.mova.domain.usecase.screen.seeall.SeeAllContentType
import com.barabasizsolt.mova.ui.R
import com.barabasizsolt.mova.ui.catalog.PeopleCarousel
import com.barabasizsolt.mova.ui.catalog.WatchablePager
import com.barabasizsolt.mova.ui.catalog.WatchableWithRatingCarousel
import com.barabasizsolt.mova.ui.screen.base.UserAction
import com.barabasizsolt.mova.ui.screen.base.BaseScreen
import com.barabasizsolt.mova.ui.screen.seeall.SeeAllScreen
import com.barabasizsolt.mova.ui.theme.AppTheme
import movie.model.Movie
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object HomeScreen : Screen, KoinComponent {

    private val screenState: HomeScreenState by inject()

    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow

        BaseScreen(
            screenState = screenState,
            onSnackBarDismissed = { screenState.getScreenData(userAction = UserAction.Normal) },
            content = { gridState, _ ->
                ScreenContent(
                    upcomingMovies = screenState.homeContent.upcomingMovies,
                    popularMovies = screenState.homeContent.popularMovies,
                    onSeeAllPopularMoviesClicked = { navigator.push(item = SeeAllScreen(contentType = SeeAllContentType.POPULAR_MOVIES.name)) },
                    popularPeople = screenState.homeContent.popularPeople,
                    onSeeAllPopularPeopleClicked = { navigator.push(item = SeeAllScreen(contentType = SeeAllContentType.POPULAR_PEOPLE.name)) },
                    nowPlayingMovies = screenState.homeContent.nowPlayingMovies,
                    onSeeAllNowPlayingMoviesClicked = { navigator.push(item = SeeAllScreen(contentType = SeeAllContentType.NOW_PLAYING_MOVIES.name)) },
                    topRatedMovies = screenState.homeContent.topRatedMovies,
                    onSeeAllTopRatedMoviesClicked = { navigator.push(item = SeeAllScreen(contentType = SeeAllContentType.TOP_RATED_MOVIES.name)) },
                    gridState = gridState,
                    genres = screenState.homeContent.genres,
                    onItemClicked = { /*TODO: Implement it*/ }
                )
            }
        )
    }
}

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
    item(key = "HomeScreen.Pager") {
        WatchablePager(
            pagerContent = upcomingMovies,
            genres = genres,
            onClick = onItemClicked,
            onPlayButtonClicked = { /*TODO: Implement it*/ },
            onAddToFavouriteButtonClicked = { /*TODO: Implement it*/ }
        )
    }
    item(key = "HomeScreen.PopularMovies") {
        WatchableWithRatingCarousel(
            header = stringResource(id = R.string.popular_movies),
            buttonText = stringResource(id = R.string.more_popular_movies),
            items = popularMovies,
            onItemClick = onItemClicked,
            onHeaderClick = onSeeAllPopularMoviesClicked,
        )
    }
    item(key = "HomeScreen.People") {
        PeopleCarousel(
            header = stringResource(id = R.string.popular_people),
            items = popularPeople,
            onItemClick = { /*TODO: Implement it*/ },
            onHeaderClick = onSeeAllPopularPeopleClicked,
        )
    }
    item(key = "HomeScreen.NowPlayingMovies") {
        WatchableWithRatingCarousel(
            header = stringResource(id = R.string.now_playing_movies),
            buttonText = stringResource(id = R.string.more_now_playing_movies),
            items = nowPlayingMovies,
            onItemClick = onItemClicked,
            onHeaderClick = onSeeAllNowPlayingMoviesClicked,
        )
    }
    item(key = "HomeScreen.TopRatedMovies") {
        WatchableWithRatingCarousel(
            header = stringResource(id = R.string.top_rated_movies),
            buttonText = stringResource(id = R.string.more_top_rated_movies),
            items = topRatedMovies,
            onItemClick = onItemClicked,
            onHeaderClick = onSeeAllTopRatedMoviesClicked,
            showDivider = false
        )
    }
}