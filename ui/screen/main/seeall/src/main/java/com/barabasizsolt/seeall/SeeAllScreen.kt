package com.barabasizsolt.seeall

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.barabasizsolt.base.BaseScreen
import com.barabasizsolt.base.BaseScreenState
import com.barabasizsolt.catalog.LoadingContent
import com.barabasizsolt.catalog.MediumPersonCard
import com.barabasizsolt.catalog.MovaHeader
import com.barabasizsolt.catalog.ScrollToTopItem
import com.barabasizsolt.catalog.WatchableWithRating
import com.barabasizsolt.domain.model.WatchableItem
import com.barabasizsolt.domain.usecase.screen.seeall.SeeAllContentType
import com.barabasizsolt.theme.AppTheme
import com.barabasizsolt.util.R
import com.barabasizsolt.util.navigationBarInsetDp
import com.barabasizsolt.util.statusBarInsetDp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/*TODO: Handle pagination edge case (themovidedb supports max 500 page)*/

@Composable
fun SeeAllScreen(screenState: SeeAllScreenState) = BaseScreen(
    screenState = screenState,
    onSnackBarDismissed = { screenState.getScreenData(swipeRefresh = false) },
    snackBarModifier = Modifier.systemBarsPadding(),
    content = {
        ScreenContent(
            isRefreshing = screenState.state is BaseScreenState.State.SwipeRefresh,
            onRefresh = { screenState.getScreenData(swipeRefresh = true) },
            items = screenState.watchableItems,
            onLoadMoreItem = { screenState.getScreenData(swipeRefresh = false) },
            onUpClicked = screenState::onUpClicked,
            contentType = screenState.contentType
        )
    }
)

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
    val scrollToUpIsVisible = rememberSaveable { mutableStateOf(value = false) }

    LaunchedEffect(
        key1 = gridState.firstVisibleItemIndex,
        block = {
            if (gridState.firstVisibleItemIndex > 20) {
                scrollToUpIsVisible.value = true
            }
            if (gridState.firstVisibleItemIndex < 1) {
                scrollToUpIsVisible.value = false
            }
        }
    )

    Box {
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

        AnimatedVisibility(
            visible = scrollToUpIsVisible.value,
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .padding(top = AppTheme.dimens.screenPadding * 2)
        ) {
            ScrollToTopItem(
                text = stringResource(id = com.barabasizsolt.seeall.R.string.scroll_up),
                onClick = { scope.launch { gridState.scrollToItem(index = 0, scrollOffset = 0) } },
            )
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
        key = { index, item -> item.id + index },
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