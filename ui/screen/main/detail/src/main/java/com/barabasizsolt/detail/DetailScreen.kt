package com.barabasizsolt.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.barabasizsolt.base.BaseScreen
import com.barabasizsolt.catalog.R
import com.barabasizsolt.catalog.WatchableWithRating
import com.barabasizsolt.detail.catalog.CastCrewContent
import com.barabasizsolt.detail.catalog.ContentHeader
import com.barabasizsolt.detail.catalog.ContentTabs
import com.barabasizsolt.domain.model.ContentItem
import com.barabasizsolt.domain.model.DetailScreenContent
import com.barabasizsolt.domain.model.toContentItem
import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.theme.AppTheme
import com.barabasizsolt.util.YoutubePlayer
import com.barabasizsolt.util.navigationBarInsetDp
import com.barabasizsolt.video.model.Video

@Composable
fun DetailScreen(screenState: DetailScreenState) {
    var tabIndex by remember { mutableStateOf(value = 0) }

    BaseScreen(
        screenState = screenState,
        content = { gridState, _ ->
            ScreenContent(
                item = screenState.details,
                gridState = gridState,
                tabIndex = tabIndex,
                onTabIndexChange = { index -> tabIndex = index },
            )
        }
    )
}

/*TODO[HIGH]: handle empty videos, movies, comments*/
@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    item: DetailScreenContent,
    gridState: LazyGridState,
    tabIndex: Int,
    onTabIndexChange: (Int) -> Unit,
) = LazyVerticalGrid(
    modifier = modifier.fillMaxSize().background(color = AppTheme.colors.surface),
    columns = GridCells.Fixed(count = 2),
    state = gridState,
    verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding * 2),
    horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
    contentPadding = PaddingValues(bottom = navigationBarInsetDp),
    content = {
        item(span = itemSpan) {
            ContentHeader(
                item = item,
                onPlayButtonClicked = { /*TODO: Implement it*/ },
                onAddToFavouriteButtonClicked = { /*TODO: Implement it*/ }
            )
        }
        item(span = itemSpan) {
            CastCrewContent(
                castCrew = (item as DetailScreenContent.MovieDetails).castCrew,
                onItemClick = { /*TODO: Implement it*/ }
            )
        }
        item(span = itemSpan) {
            ContentTabs(
                tabIndex = tabIndex,
                onTabIndexChange = onTabIndexChange
            )
        }
        when (tabIndex) {
            0 -> similarMoviesItem(movies = (item as DetailScreenContent.MovieDetails).similarMovies)
            1 -> similarVideoItem(videos = (item as DetailScreenContent.MovieDetails).videos)
            2 -> itemsIndexed(
                items = (item as DetailScreenContent.MovieDetails).reviews,
                span = getTabItemsSpan(tabStyle = TabStyle.ROW)
            ) { _, item ->
                /*TODO: Implement it*/
                Text(
                    text = item.content,
                    style = AppTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
)


private fun LazyGridScope.similarMoviesItem(movies: List<Movie>) = baseTabItem(
    items = movies,
    span = getTabItemsSpan(tabStyle = TabStyle.GRID)
) { index, item ->
    WatchableWithRating(
        item = (item as Movie).toContentItem() as ContentItem.Watchable,
        onClick = { /*TODO: Implement it*/ },
        modifier = Modifier.padding(
            start = if (index % 2 == 0) AppTheme.dimens.screenPadding else 0.dp,
            end = if (index % 2 == 0) 0.dp else AppTheme.dimens.screenPadding,
        )
    )
}

private fun LazyGridScope.similarVideoItem(videos: List<Video>) = baseTabItem(
    items = videos,
    span = getTabItemsSpan(tabStyle = TabStyle.ROW)
) { _, item ->
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimens.screenPadding)
    ) {
        Text(
            text = (item as Video).name,
            style = AppTheme.typography.body1,
            fontWeight = FontWeight.Bold
        )
        YoutubePlayer(
            videoId = (item).videoId
        )
    }
}

private fun LazyGridScope.baseTabItem(
    items: List<Any>,
    span: (LazyGridItemSpanScope.(index: Int, item: Any) -> GridItemSpan),
    content: @Composable (index: Int, item: Any) -> Unit
) =
    if (items.isEmpty()) {
        item(span = { GridItemSpan(currentLineSpan = 2) }) {
            EmptyTabItem()
        }
    } else {
        itemsIndexed(
            items = items,
            span = span
        ) { index, item ->
            content(index, item)
        }
}

@Composable
private fun EmptyTabItem(modifier: Modifier = Modifier) = Card(
    modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = AppTheme.dimens.screenPadding),
    backgroundColor = AppTheme.colors.secondary,
    elevation = 16.dp,
    shape = AppTheme.shapes.medium
) {
    Text(
        text = stringResource(id = R.string.no_result_found),
        textAlign = TextAlign.Center,
        style = AppTheme.typography.body2,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxSize()
            .padding(all = AppTheme.dimens.screenPadding)
    )
}

private val itemSpan: (LazyGridItemSpanScope.() -> GridItemSpan) = { GridItemSpan(currentLineSpan = 2) }

private fun getTabItemsSpan(tabStyle: TabStyle): (LazyGridItemSpanScope.(index: Int, item: Any) -> GridItemSpan) = { _, _ ->
    GridItemSpan(
        currentLineSpan = when (tabStyle) {
            TabStyle.ROW -> 2
            TabStyle.GRID -> 1
        }
    )
}

enum class TabStyle {
    GRID, ROW
}