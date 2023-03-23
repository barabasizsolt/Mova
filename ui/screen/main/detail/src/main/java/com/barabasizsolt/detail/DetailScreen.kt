package com.barabasizsolt.detail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.barabasizsolt.base.BaseScreen
import com.barabasizsolt.catalog.WatchableWithRating
import com.barabasizsolt.detail.catalog.CastCrewContent
import com.barabasizsolt.detail.catalog.ContentHeader
import com.barabasizsolt.detail.catalog.ContentTabs
import com.barabasizsolt.domain.model.ContentItem
import com.barabasizsolt.domain.model.DetailScreenContent
import com.barabasizsolt.domain.model.toContentItem
import com.barabasizsolt.theme.AppTheme
import com.barabasizsolt.util.YoutubePlayer
import com.barabasizsolt.util.navigationBarInsetDp

@Composable
fun DetailScreen(screenState: DetailScreenState) {
    var tabIndex by remember { mutableStateOf(value = 0) }
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    BaseScreen(
        screenState = screenState,
        content = { gridState, _ ->
            ScreenContent(
                item = screenState.details,
                gridState = gridState,
                tabIndex = tabIndex,
                onTabIndexChange = { index -> tabIndex = index },
                lifecycleOwner = lifecycleOwner,
                scrollState = rememberScrollState()
            )
        }
    )
}

/*TODO[HIGH]: handle empty videos, movies, comments*/
@Composable
private fun ScreenContent(
    item: DetailScreenContent,
    gridState: LazyGridState,
    tabIndex: Int,
    onTabIndexChange: (Int) -> Unit,
    lifecycleOwner: LifecycleOwner,
    scrollState: ScrollState
) = LazyVerticalGrid(
    columns = GridCells.Fixed(count = 2),
    //modifier = Modifier.fillMaxSize(),
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
        /*TODO[HIGH]: Make it separate item*/
        when (tabIndex) {
            0 -> itemsIndexed(
                items = (item as DetailScreenContent.MovieDetails).videos,
                span = getTabItemsSpan(tabIndex = tabIndex),
                key = { _, item -> item.id }
            ) { _, item ->
                /*TODO[HIGH]: Make it separate component*/
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.dimens.screenPadding)
                ) {
                    Text(
                        text = item.name,
                        style = AppTheme.typography.body1,
                        fontWeight = FontWeight.Bold
                    )
                    YoutubePlayer(videoId = item.videoId, lifecycleOwner = lifecycleOwner)
                }
            }
            1 -> itemsIndexed(
                items = (item as DetailScreenContent.MovieDetails).similarMovies,
                span = getTabItemsSpan(tabIndex = tabIndex)
            ) { index, item ->
                /*TODO[HIGH]: Make it separate component*/
                WatchableWithRating(
                    item = item.toContentItem() as ContentItem.Watchable,
                    onClick = { /*TODO: Implement it*/ },
                    modifier = Modifier.padding(
                        start = if (index % 2 == 0) AppTheme.dimens.screenPadding else 0.dp,
                        end = if (index % 2 == 0) 0.dp else AppTheme.dimens.screenPadding,
                    )
                )
            }
            2 -> itemsIndexed(
                items = (item as DetailScreenContent.MovieDetails).reviews,
                span = getTabItemsSpan(tabIndex = tabIndex)
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

private val itemSpan: (LazyGridItemSpanScope.() -> GridItemSpan) = { GridItemSpan(currentLineSpan = 2) }

private fun getTabItemsSpan(tabIndex: Int): (LazyGridItemSpanScope.(index: Int, item: Any) -> GridItemSpan) = { _, _ ->
    GridItemSpan(
        currentLineSpan = when (tabIndex) {
            0, 2 -> 2
            else -> 1
        }
    )
}