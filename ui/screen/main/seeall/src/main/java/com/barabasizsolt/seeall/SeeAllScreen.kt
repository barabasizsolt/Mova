package com.barabasizsolt.seeall

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.barabasizsolt.catalog.ErrorContent
import com.barabasizsolt.catalog.LoadingContent
import com.barabasizsolt.catalog.MediumPersonCard
import com.barabasizsolt.catalog.MovaHeader
import com.barabasizsolt.catalog.MovaSnackBar
import com.barabasizsolt.catalog.WatchableWithRating
import com.barabasizsolt.domain.model.WatchableItem
import com.barabasizsolt.domain.usecase.screen.seeall.SeeAllContentType
import com.barabasizsolt.theme.attributes.AppTheme
import com.barabasizsolt.util.R
import com.barabasizsolt.util.navigationBarInsetDp
import com.barabasizsolt.util.statusBarInsetDp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/*TODO: Handle pagination edge case (themovidedb supports max 500 page)*/

@Composable
fun SeeAllScreen(screenState: SeeAllScreenState) {
    val snackBarHostState = remember { SnackbarHostState() }

    Box(modifier = Modifier.background(color = AppTheme.colors.primary)) {
        when (screenState.state) {
            is SeeAllScreenState.State.Error -> ErrorContent(onRetry = { screenState.getScreenData(swipeRefresh = false) })
            is SeeAllScreenState.State.Loading -> LoadingContent()
            else -> ScreenContent(
                isRefreshing = screenState.state is SeeAllScreenState.State.SwipeRefresh,
                onRefresh = { screenState.getScreenData(swipeRefresh = true) },
                items = screenState.watchableItems,
                onLoadMoreItem = { screenState.getScreenData(swipeRefresh = false) },
                onUpClicked = screenState::onUpClicked,
                contentType = screenState.contentType
            )
        }

        MovaSnackBar(
            snackBarHostState = snackBarHostState,
            onDismiss = {
                snackBarHostState.currentSnackbarData?.dismiss()
                screenState.resetState()
            },
            modifier = Modifier.systemBarsPadding()
        )

        LaunchedEffect(
            key1 = screenState.state,
            block = {
                if (screenState.state is SeeAllScreenState.State.ShowSnackBar) {
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
private fun ScreenContent(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    items: List<WatchableItem>,
    onLoadMoreItem: () -> Unit,
    onUpClicked: () -> Unit,
    contentType: String,
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    val gridState: LazyGridState = rememberLazyGridState()
    val scope: CoroutineScope = rememberCoroutineScope()

    BackHandler(enabled = gridState.firstVisibleItemScrollOffset > 0) {
        scope.launch {
            gridState.scrollToItem(index = 0, scrollOffset = 0)
        }
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = onRefresh
    ) {
        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Fixed(count = 6),
            verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
            horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
            contentPadding = PaddingValues(
                start = AppTheme.dimens.screenPadding,
                end = AppTheme.dimens.screenPadding,
                bottom = AppTheme.dimens.screenPadding + navigationBarInsetDp,
                top = AppTheme.dimens.screenPadding + statusBarInsetDp
            )
        ) {
            header(contentType = contentType, onClick = onUpClicked)
            content(items = items, onLoadMoreItem = onLoadMoreItem)
        }
    }
}

private fun LazyGridScope.header(
    modifier: Modifier = Modifier,
    contentType: String,
    onClick: () -> Unit
) = item(span = { GridItemSpan(currentLineSpan = 6) }) {
    MovaHeader(
        text = when (contentType) {
            SeeAllContentType.POPULAR_MOVIES.name -> stringResource(id = R.string.popular_movies)
            SeeAllContentType.POPULAR_PEOPLE.name -> stringResource(id = R.string.popular_people)
            SeeAllContentType.NOW_PLAYING_MOVIES.name -> stringResource(id = R.string.now_playing_movies)
            SeeAllContentType.TOP_RATED_MOVIES.name -> stringResource(id = R.string.top_rated_movies)
            else -> stringResource(id = R.string.all_content)
        },
        onClick = onClick,
        modifier = modifier.padding(bottom = AppTheme.dimens.contentPadding)
    )
}

private fun LazyGridScope.content(
    items: List<WatchableItem>,
    onLoadMoreItem: () -> Unit
) {
    itemsIndexed(
        items = items,
        key = { _, item -> item.id },
        span = { _, item -> GridItemSpan(currentLineSpan = if (item is WatchableItem.People) 2 else 3) }
    ) { index, item ->
        when (item) {
            is WatchableItem.Movie, is WatchableItem.TvSeries -> WatchableWithRating(
                item = item,
                onClick = { /*TODO: Implement it*/ }
            )
            is WatchableItem.People -> MediumPersonCard(
                item = item,
                onClick = { /*TODO: Implement it*/ }
            )
        }
        if (index == items.lastIndex) { SideEffect { onLoadMoreItem() } }
    }
    item(span = { GridItemSpan(currentLineSpan = 6) }) {
        LoadingContent(modifier = Modifier
            .height(height = 80.dp)
            .fillMaxWidth())
    }
}