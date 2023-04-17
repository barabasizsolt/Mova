package com.barabasizsolt.mova.ui.screen.seeall

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.barabasizsolt.mova.domain.model.ContentItem
import com.barabasizsolt.mova.domain.usecase.screen.seeall.SeeAllContentType
import com.barabasizsolt.mova.ui.R
import com.barabasizsolt.mova.ui.catalog.ErrorItem
import com.barabasizsolt.mova.ui.catalog.LoadingContent
import com.barabasizsolt.mova.ui.catalog.MediumPersonCard
import com.barabasizsolt.mova.ui.catalog.MovaHeader
import com.barabasizsolt.mova.ui.catalog.WatchableWithRating
import com.barabasizsolt.mova.ui.screen.base.BaseScreenState
import com.barabasizsolt.mova.ui.screen.base.UserAction
import com.barabasizsolt.mova.ui.screen.main.base.BaseScreen
import com.barabasizsolt.mova.ui.theme.AppTheme
import com.barabasizsolt.mova.ui.util.navigationBarInsetDp
import com.barabasizsolt.mova.ui.util.statusBarInsetDp

@Composable
fun SeeAllScreen(screenState: SeeAllScreenState) = BaseScreen(
    screenState = screenState,
    onSnackBarDismissed = { screenState.getScreenData(userAction = UserAction.Normal) },
    snackBarModifier = Modifier.systemBarsPadding(),
    content = { gridState, _ ->
        ScreenContent(
            items = screenState.watchableItems,
            onLoadMoreItem = { screenState.getScreenData(userAction = UserAction.Normal) },
            onRetryClick = { screenState.getScreenData(userAction = UserAction.TryAgain) },
            onUpClicked = screenState::onUpClicked,
            contentType = screenState.contentType,
            gridState = gridState,
            isTryAgainLoading = screenState.state is BaseScreenState.State.TryAgainLoading,
            onMovieClicked = screenState::onMovieClicked
        )
    }
)

@Composable
private fun ScreenContent(
    items: List<ContentItem>,
    onLoadMoreItem: () -> Unit,
    onRetryClick: () -> Unit,
    onUpClicked: () -> Unit,
    contentType: String,
    gridState: LazyGridState,
    isTryAgainLoading: Boolean,
    onMovieClicked: (Int) -> Unit
) = LazyVerticalGrid(
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
    content(
        items = items,
        contentType = contentType,
        onHeaderClick = onUpClicked,
        onLoadMoreItem = onLoadMoreItem,
        onRetryClick = onRetryClick,
        isTryAgainLoading = isTryAgainLoading,
        onMovieClicked = onMovieClicked
    )
}

private fun LazyGridScope.content(
    items: List<ContentItem>,
    contentType: String,
    onHeaderClick: () -> Unit,
    onLoadMoreItem: () -> Unit,
    onRetryClick: () -> Unit,
    onMovieClicked: (Int) -> Unit,
    isTryAgainLoading: Boolean
) = itemsIndexed(
    items = items,
    key = { index, item -> item.id + index },
    span = { _, item ->
        GridItemSpan(
            currentLineSpan = when (item) {
                is ContentItem.ItemHeader, is ContentItem.ItemTail, is ContentItem.ItemError -> 6
                is ContentItem.Person -> 2
                else -> 3
            }
        )
    }
) { _, item ->
    when (item) {
        is ContentItem.ItemHeader -> MovaHeader(
            text = when (contentType) {
                SeeAllContentType.POPULAR_MOVIES.name -> stringResource(id = R.string.popular_movies)
                SeeAllContentType.POPULAR_PEOPLE.name -> stringResource(id = R.string.popular_people)
                SeeAllContentType.NOW_PLAYING_MOVIES.name -> stringResource(id = R.string.now_playing_movies)
                SeeAllContentType.TOP_RATED_MOVIES.name -> stringResource(id = R.string.top_rated_movies)
                else -> stringResource(id = R.string.all_content)
            },
            onClick = onHeaderClick,
            modifier = Modifier.padding(bottom = AppTheme.dimens.contentPadding)
        )
        is ContentItem.Watchable -> WatchableWithRating(
            item = item,
            onClick = onMovieClicked
        )
        is ContentItem.Person -> MediumPersonCard(
            item = item,
            onClick = { /*TODO: Implement it*/ }
        )
        is ContentItem.ItemTail -> if (item.loadMore) {
            LoadingContent(modifier = Modifier
                .height(height = 80.dp)
                .fillMaxWidth())
            LaunchedEffect(
                key1 = Unit,
                block = {
                    println("<<HERE")
                    onLoadMoreItem()
                }
            )
        }
        is ContentItem.ItemError -> ErrorItem(
            onRetryClick = onRetryClick,
            isLoading = isTryAgainLoading
        )
    }
}