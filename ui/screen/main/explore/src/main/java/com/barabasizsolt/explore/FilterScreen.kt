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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.barabasizsolt.catalog.MovaButton
import com.barabasizsolt.domain.usecase.screen.explore.Category
import com.barabasizsolt.theme.AppTheme
import com.barabasizsolt.theme.MovaTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/*TODO: Refactor*/

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
                text = "Sort & Filter",
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
                header = "Categories",
                selectedItemPosition = screenState.selectedCategoryPosition,
                items = screenState.categories,
                onClick = { position -> screenState.onCategorySelected(position) }
            )
        }
        item {
            MultiSelectionFilterCarousel(
                header = "Regions",
                selectedItemPositions = screenState.selectedRegionPositions,
                items = screenState.regions,
                onClick = { positions -> screenState.onRegionSelected(positions) }
            )
        }
        item {
            MultiSelectionFilterCarousel(
                header = "Genres",
                selectedItemPositions = screenState.selectedGenrePositions,
                items = screenState.genres,
                onClick = { positions -> screenState.onGenreSelected(positions) }
            )
        }
        item {
            MultiSelectionFilterCarousel(
                header = "Sort",
                selectedItemPositions = screenState.selectedSortOptionPositions,
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
    text = "Apply",
    onClick = onClick,
    modifier = modifier
)

@Composable
private fun ResetButton(
    modifier: Modifier = Modifier,
    isDark: Boolean,
    onClick: () -> Unit
) = MovaButton(
    text = "Reset",
    onClick = onClick,
    contentColor = if (isDark) Color.White else AppTheme.colors.secondary,
    backgroundColor = if (isDark) Color.DarkGray else AppTheme.colors.secondary.copy(alpha = .2f),
    modifier = modifier
)

@Composable
private fun SingleSelectionFilterCarousel(
    modifier: Modifier = Modifier,
    header: String,
    selectedItemPosition: Int,
    items: List<FilterItem>,
    onClick: (Int) -> Unit
) = BaseFilterCarousel(
    modifier = modifier,
    header = header,
    items = items,
    rowContent = { index, item ->
        FilterItem(
            text = item.name,
            isSelected = index == selectedItemPosition,
            onClick = {
                if (selectedItemPosition != index) {
                    onClick(index)
                }
            }
        )
    }
)

@Composable
private fun MultiSelectionFilterCarousel(
    modifier: Modifier = Modifier,
    header: String,
    selectedItemPositions: List<Int>,
    items: List<FilterItem>,
    onClick: (List<Int>) -> Unit
) = BaseFilterCarousel(
    modifier = modifier,
    header = header,
    items = items,
    rowContent = { index, item ->
        FilterItem(
            text = item.name,
            isSelected = selectedItemPositions.contains(element = index),
            onClick = {
                val oldPositions = selectedItemPositions.toMutableList()
                if (oldPositions.contains(element = index)) {
                    oldPositions.remove(element = index)
                    if (oldPositions.isEmpty()) {
                        oldPositions.add(element = 0)
                    }
                } else {
                    when {
                        oldPositions.contains(element = 0) -> {
                            oldPositions.remove(element = 0)
                            oldPositions.add(element = index)
                        }
                        index == 0 -> {
                            oldPositions.clear()
                            oldPositions.add(element = 0)
                        }
                        else -> {
                            oldPositions.add(element = index)
                        }
                    }
                }
                onClick(oldPositions)
            }
        )
    }
)

@Composable
private fun BaseFilterCarousel(
    modifier: Modifier = Modifier,
    header: String,
    items: List<FilterItem>,
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
        verticalAlignment = Alignment.CenterVertically
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