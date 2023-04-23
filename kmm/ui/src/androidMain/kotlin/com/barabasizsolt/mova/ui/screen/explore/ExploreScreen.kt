package com.barabasizsolt.mova.ui.screen.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import category.Category
import com.barabasizsolt.mova.domain.model.ContentItem
import com.barabasizsolt.mova.filter.api.FilterItem
import com.barabasizsolt.mova.ui.catalog.ErrorItem
import com.barabasizsolt.mova.ui.catalog.FilterIcon
import com.barabasizsolt.mova.ui.catalog.LoadingContent
import com.barabasizsolt.mova.ui.catalog.MovaSearchField
import com.barabasizsolt.mova.ui.catalog.NotFoundItem
import com.barabasizsolt.mova.ui.catalog.SearchableItem
import com.barabasizsolt.mova.ui.catalog.WatchableWithRating
import com.barabasizsolt.mova.ui.getPlatform
import com.barabasizsolt.mova.ui.screen.base.BaseScreenState
import com.barabasizsolt.mova.ui.screen.base.UserAction
import com.barabasizsolt.mova.ui.screen.base.BaseScreen
import com.barabasizsolt.mova.ui.screen.detail.DetailScreen
import com.barabasizsolt.mova.ui.theme.AppTheme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object ExploreScreen : Screen, KoinComponent {

    private val screenState: ExploreScreenState by inject()
    private val filterScreenState: FilterScreenState by inject()

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow

        val movieListState: LazyGridState = rememberLazyGridState()
        val movieSearchState: LazyGridState = rememberLazyGridState()
        val tvListState: LazyGridState = rememberLazyGridState()
        val tvSearchState: LazyGridState = rememberLazyGridState()

        when (filterScreenState.action?.consume()) {
            is FilterScreenState.Action.OnApplyButtonClicked -> screenState.onApplyButtonClicked()
            is FilterScreenState.Action.OnResetButtonClicked -> screenState.onResetButtonClicked()
            else -> Unit
        }

        var shouldShowScrollUp by remember { mutableStateOf(value = false) }

        BaseScreen(
            screenState = screenState,
            gridState = when (screenState.selectedCategory.wrappedItem as Category) {
                Category.MOVIE -> if (screenState.query.isEmpty()) movieListState else movieSearchState
                Category.TV -> if (screenState.query.isEmpty()) tvListState else tvSearchState
            },
            scrollUpTopPadding = AppTheme.dimens.searchBarHeight + AppTheme.dimens.screenPadding * 5,
            shouldShowScrollUp = shouldShowScrollUp,
            content = { gridState, scope ->
                BottomSheetNavigator(
                    sheetShape = AppTheme.shapes.medium.copy(
                        bottomStart = CornerSize(size = 0.dp),
                        bottomEnd = CornerSize(size = 0.dp)
                    )
                ) {
                    LaunchedEffect(
                        key1 = it.isVisible,
                        block = { shouldShowScrollUp = !it.isVisible }
                    )

                    Box(modifier = Modifier.fillMaxSize().background(color = AppTheme.colors.primary)) {
                        ScreenContent(
                            query = screenState.query,
                            onQueryChange = screenState::onQueryChange,
                            discoverItems = screenState.discoverContent,
                            searchItems = screenState.searchContent,
                            filterItems = buildList {
                                add(element = screenState.selectedCategory)
                                if ((screenState.selectedCategory.wrappedItem as Category) == Category.MOVIE) {
                                    addAll(elements = screenState.selectedRegions)
                                }
                                addAll(elements = screenState.selectedGenres)
                                addAll(elements = screenState.selectedSortOptions)
                            },
                            isLoading = screenState.state in listOf(BaseScreenState.State.SearchLoading),
                            isTryAgainLoading = screenState.state is BaseScreenState.State.TryAgainLoading,
                            onLoadMoreItem = { screenState.getScreenData(userAction = UserAction.Normal) },
                            onRetryClick = {
                                if (screenState.query.isNotEmpty() && screenState.searchContent.size <= 1) {
                                    screenState.clearSearchContent()
                                    // TODO [MID] here after success retry, keep the error item loading till the content will be laaded.
                                }
                                screenState.getScreenData(userAction = UserAction.TryAgain)
                            },
                            onClick = {
                                shouldShowScrollUp = it
                            },
                            onMovieClicked = { id -> navigator.push(item = DetailScreen(id = id)) },
                            scope = scope,
                            gridState = gridState
                        )
                    }
                }
            }
        )
    }
}

@Composable
private fun ScreenContent(
    query: String,
    onQueryChange: (String) -> Unit,
    discoverItems: List<ContentItem>,
    searchItems: List<ContentItem>,
    filterItems: List<FilterItem>,
    isLoading: Boolean,
    isTryAgainLoading: Boolean,
    onLoadMoreItem: () -> Unit,
    onRetryClick: () -> Unit,
    scope: CoroutineScope,
    onClick: (Boolean) -> Unit,
    onMovieClicked: (Int) -> Unit,
    gridState: LazyGridState
) {
    Column(verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.screenPadding)) {
        SearchBar(
            query = query,
            onQueryChange = onQueryChange ,
            scope = scope,
            onClick = onClick
        )
        if (isLoading) {
            LoadingBody(
                gridState = gridState
            )
        } else {
            if (query.isEmpty()) {
                FilterItemCarousel(
                    filterItems = filterItems,
                )
            }
            ContentBody(
                gridState = gridState,
                query = query,
                discoverItems = discoverItems,
                searchItems = searchItems,
                onLoadMoreItem = onLoadMoreItem,
                onRetryClick = onRetryClick,
                isTryAgainLoading = isTryAgainLoading,
                onMovieClicked = onMovieClicked
            )
        }
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    scope: CoroutineScope,
    onClick: (Boolean) -> Unit
) = Row(
    modifier = modifier
        .padding(
            top = getPlatform().statusBarInsetDp + AppTheme.dimens.screenPadding,
            start = AppTheme.dimens.screenPadding,
            end = AppTheme.dimens.screenPadding
        ),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding)
) {
    val bottomSheetNavigator = LocalBottomSheetNavigator.current
    MovaSearchField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier.weight(weight = 1f)
    )
    FilterIcon(
        onClick = {
            scope.launch {
                if (bottomSheetNavigator.isVisible) {
                    onClick(true)
                    bottomSheetNavigator.hide()
                } else {
                    onClick(false)
                    bottomSheetNavigator.show(screen = FilterScreen)
                }
            }
        }
    )
}

@Composable
private fun ContentBody(
    modifier: Modifier = Modifier,
    gridState: LazyGridState,
    query: String,
    discoverItems: List<ContentItem>,
    searchItems: List<ContentItem>,
    onLoadMoreItem: () -> Unit,
    onRetryClick: () -> Unit,
    isTryAgainLoading: Boolean,
    onMovieClicked: (Int) -> Unit
) = LazyVerticalGrid(
    columns = GridCells.Fixed(count = 2),
    verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
    horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
    contentPadding = PaddingValues(
        start = AppTheme.dimens.screenPadding,
        end = AppTheme.dimens.screenPadding,
        bottom = AppTheme.dimens.screenPadding + getPlatform().imeBottomInsetDp
    ),
    modifier = modifier.fillMaxSize(),
    state = gridState
) {
    if (query.isNotEmpty()) {
        searchableItemsIndexed(
            items = searchItems,
            key = { index, item -> item.id + index },
            span = searchableItemSpan(baseLineSpan = 2),
            onLoadMoreItem = onLoadMoreItem,
            onRetryClick = onRetryClick,
            isTryAgainLoading = isTryAgainLoading
        ) { _, item ->
            SearchableItem(
                item = item as ContentItem.Watchable,
                onClick = {
                    if (item.isMovie) {
                        onMovieClicked(item.id.toInt())
                    }
                }
            )
        }
    } else {
        searchableItemsIndexed(
            items = discoverItems,
            key = { index, item -> item.id + index },
            span = searchableItemSpan(baseLineSpan = 1),
            onLoadMoreItem = onLoadMoreItem,
            onRetryClick = onRetryClick,
            isTryAgainLoading = isTryAgainLoading
        ) { _, item ->
            WatchableWithRating(
                item = item as ContentItem.Watchable,
                onClick = {
                    if (item.isMovie) {
                        onMovieClicked(item.id.toInt())
                    }
                }
            )
        }
    }
}

@Composable
private fun FilterItemCarousel(
    modifier: Modifier = Modifier,
    filterItems: List<FilterItem>
) = LazyRow(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
    verticalAlignment = Alignment.CenterVertically,
    contentPadding = PaddingValues(start = AppTheme.dimens.screenPadding)
) {
    items(items = filterItems) { item ->
        SelectedFilterItem(text = item.name)
    }
}

@Composable
private fun LoadingBody(gridState: LazyGridState) {
    LaunchedEffect(
        key1 = Unit,
        block = { gridState.scrollToItem(index = 0, scrollOffset = 0) }
    )
    LoadingContent()
}

private inline fun LazyGridScope.searchableItemsIndexed(
    items: List<ContentItem>,
    isTryAgainLoading: Boolean,
    crossinline onLoadMoreItem: () -> Unit,
    crossinline onRetryClick: () -> Unit,
    noinline key: ((index: Int, item: ContentItem) -> Any)? = null,
    noinline span: (LazyGridItemSpanScope.(index: Int, item: ContentItem) -> GridItemSpan)? = null,
    crossinline itemContent: @Composable LazyGridItemScope.(index: Int, item: ContentItem) -> Unit
) = itemsIndexed(
    items = items,
    key = key,
    span = span,
) { index, item: ContentItem ->
    when (item) {
        is ContentItem.ItemTail -> when {
            item.loadMore -> {
                LoadingContent(modifier = Modifier
                    .height(height = 80.dp)
                    .fillMaxWidth())
                LaunchedEffect(
                    key1 = Unit,
                    block = { onLoadMoreItem() }
                )
            }
            items.size == 1 -> NotFoundItem()
        }
        is ContentItem.ItemError -> ErrorItem(
            onRetryClick = { onRetryClick() },
            isLoading = isTryAgainLoading
        )
        else -> itemContent(index, item)
    }
}

private fun searchableItemSpan(baseLineSpan: Int): (LazyGridItemSpanScope.(index: Int, item: ContentItem) -> GridItemSpan) = { _, item ->
    GridItemSpan(
        currentLineSpan = when (item) {
            is ContentItem.ItemTail, is ContentItem.ItemError -> 2
            else -> baseLineSpan
        }
    )
}

@Composable
private fun SelectedFilterItem(
    modifier: Modifier = Modifier,
    text: String
) = Box(
    modifier = modifier
        .clip(shape = CircleShape)
        .background(color = AppTheme.colors.secondary)
        .border(
            width = 1.dp,
            color = AppTheme.colors.secondary,
            shape = CircleShape
        ),
    content = {
        Text(
            text = text,
            style = AppTheme.typography.caption,
            color = AppTheme.colors.onSecondary,
            modifier = Modifier.padding(
                horizontal = AppTheme.dimens.contentPadding * 2,
                vertical = AppTheme.dimens.contentPadding
            )
        )
    }
)