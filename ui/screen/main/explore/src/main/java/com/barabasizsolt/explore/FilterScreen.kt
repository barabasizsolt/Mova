package com.barabasizsolt.explore

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.barabasizsolt.catalog.MovaButton
import com.barabasizsolt.filter.api.Category
import com.barabasizsolt.filter.api.FilterItem
import com.barabasizsolt.theme.AppTheme
import com.barabasizsolt.theme.MovaTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun FilterScreenPreview() = MovaTheme(isDarkTheme = true) {
    FilterScreen(
        screenState = rememberFilterScreenState(),
        bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterScreen(
    screenState: FilterScreenState,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    val isDark: Boolean = isSystemInDarkTheme()
    val scope: CoroutineScope = rememberCoroutineScope()
    val genreListState = rememberLazyListState()

    BackHandler(enabled = bottomSheetScaffoldState.bottomSheetState.isExpanded) {
        scope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.screenPadding),
        contentPadding = PaddingValues(vertical = AppTheme.dimens.screenPadding),
        modifier = Modifier.background(color = AppTheme.colors.background)
    ) {
        item {
            Text(
                text = stringResource(id = R.string.sort_filter),
                color = AppTheme.colors.secondary,
                style = AppTheme.typography.h5,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Divider(
                modifier = Modifier.padding(horizontal = AppTheme.dimens.screenPadding),
                color = if (isDark) Color.DarkGray else Color.LightGray
            )
        }
        item {
            SingleSelectionFilterCarousel(
                header = stringResource(id = R.string.categories),
                selectedItem = screenState.selectedCategory,
                items = screenState.categories,
                onClick = { position ->
                    screenState.onCategorySelected(position)
                    scope.launch {
                        genreListState.scrollToItem(index = 0, scrollOffset = 0)
                    }
                }
            )
        }
        if (screenState.selectedCategory.wrappedItem as Category == Category.MOVIE) {
            item {
                MultiSelectionFilterCarousel(
                    header = stringResource(id = R.string.regions),
                    selectedItems = screenState.selectedRegions,
                    items = screenState.regions,
                    onClick = { positions -> screenState.onRegionSelected(positions) }
                )
            }
        }
        item {
            MultiSelectionFilterCarousel(
                header = stringResource(id = R.string.genres),
                selectedItems = screenState.selectedGenres,
                items = screenState.genres,
                listState = genreListState,
                onClick = { positions -> screenState.onGenreSelected(positions) }
            )
        }
        item {
            MultiSelectionFilterCarousel(
                header = stringResource(id = R.string.sort),
                selectedItems = screenState.selectedSortOptions,
                items = screenState.sortOptions,
                onClick = { positions -> screenState.onSortingCriteriaSelected(positions) }
            )
        }
        item {
            Divider(
                modifier = Modifier.padding(horizontal = AppTheme.dimens.screenPadding),
                color = if (isDark) Color.DarkGray else Color.LightGray
            )
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.screenPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding)
            ) {
                ResetButton(
                    onClick = screenState::onResetButtonClicked,
                    isDark = isDark,
                    modifier = Modifier.weight(weight = 1f)
                )
                ApplyButton(
                    onClick = {
                        screenState.onApplyButtonClicked()
                        scope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
                    },
                    modifier = Modifier.weight(weight = 1f)
                )
            }
        }
    }
}

@Composable
private fun ApplyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) = MovaButton(
    text = stringResource(id = R.string.apply),
    onClick = onClick,
    modifier = modifier
)

@Composable
private fun ResetButton(
    modifier: Modifier = Modifier,
    isDark: Boolean,
    onClick: () -> Unit
) = MovaButton(
    text = stringResource(id = R.string.reset),
    onClick = onClick,
    contentColor = if (isDark) Color.White else AppTheme.colors.secondary,
    backgroundColor = if (isDark) Color.DarkGray else AppTheme.colors.secondary.copy(alpha = .2f),
    modifier = modifier
)

@Composable
private fun SingleSelectionFilterCarousel(
    modifier: Modifier = Modifier,
    header: String,
    selectedItem: FilterItem,
    items: List<FilterItem>,
    listState: LazyListState = rememberLazyListState(),
    onClick: (FilterItem) -> Unit
) = BaseFilterCarousel(
    modifier = modifier,
    header = header,
    items = items,
    listState = listState,
    rowContent = { _, item ->
        FilterItem(
            text = item.name,
            isSelected = item == selectedItem,
            onClick = {
                if (selectedItem != item) {
                    onClick(item)
                }
            }
        )
    }
)

@Composable
private fun MultiSelectionFilterCarousel(
    modifier: Modifier = Modifier,
    header: String,
    selectedItems: List<FilterItem>,
    items: List<FilterItem>,
    listState: LazyListState = rememberLazyListState(),
    onClick: (List<FilterItem>) -> Unit
) = BaseFilterCarousel(
    modifier = modifier,
    header = header,
    items = items,
    listState = listState,
    rowContent = { index, item ->
        FilterItem(
            text = item.name,
            isSelected = selectedItems.contains(element = item),
            onClick = {
                val oldItems = selectedItems.toMutableList()
                if (oldItems.contains(element = item)) {
                    oldItems.remove(element = item)
                    if (oldItems.isEmpty()) {
                        oldItems.add(element = items[0])
                    }
                } else {
                    when {
                        oldItems.contains(element = items[0]) -> {
                            oldItems.remove(element = items[0])
                            oldItems.add(element = item)
                        }
                        index == 0 -> {
                            oldItems.clear()
                            oldItems.add(element = items[0])
                        }
                        else -> {
                            oldItems.add(element = item)
                        }
                    }
                }
                onClick(oldItems)
            }
        )
    }
)

@Composable
private fun BaseFilterCarousel(
    modifier: Modifier = Modifier,
    header: String,
    items: List<FilterItem>,
    listState: LazyListState,
    rowContent: @Composable (Int, FilterItem) -> Unit
) = Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding * 2)
) {
    Text(
        text = header,
        style = AppTheme.typography.subtitle1,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = AppTheme.dimens.screenPadding)
    )
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = AppTheme.dimens.screenPadding),
        horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        state = listState
    ) {
        itemsIndexed(items = items) { index, item ->
            rowContent(index, item)
        }
    }
}

@Composable
private fun FilterItem(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) = Box(
    modifier = modifier
        .clip(shape = CircleShape)
        .background(color = if (isSelected) AppTheme.colors.secondary else Color.Transparent)
        .border(
            width = 1.dp,
            color = AppTheme.colors.secondary,
            shape = CircleShape
        )
        .clickable { onClick() }
) {
    Text(
        text = text,
        style = AppTheme.typography.subtitle2,
        color = if (isSelected) AppTheme.colors.onSecondary else AppTheme.colors.secondary,
        modifier = Modifier.padding(
            horizontal = AppTheme.dimens.contentPadding * 2,
            vertical = AppTheme.dimens.contentPadding
        )
    )
}