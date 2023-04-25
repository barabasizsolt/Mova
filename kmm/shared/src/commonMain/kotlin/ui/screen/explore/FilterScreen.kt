package ui.screen.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import category.Category
import com.barabasizsolt.mova.filter.api.FilterItem
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.catalog.BackButton
import ui.catalog.MovaButton
import ui.getPlatform
import ui.theme.AppTheme

internal object FilterScreen : Screen, KoinComponent {

    private val screenState: FilterScreenState by inject()

    @Composable
    override fun Content() {
        val isDark: Boolean = isSystemInDarkTheme()
        val genreListState = rememberLazyListState()
        val navigator = LocalNavigator.currentOrThrow

        Box(modifier = Modifier.fillMaxSize().background(color = AppTheme.colors.primary)) {
            BackButton(
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .padding(
                        start = AppTheme.dimens.screenPadding,
                        top = AppTheme.dimens.screenPadding + getPlatform().statusBarInsetDp
                    )
            ) {
                navigator.pop()
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(
                    space = AppTheme.dimens.screenPadding,
                    alignment = Alignment.Bottom
                ),
                contentPadding = PaddingValues(
                    top = AppTheme.dimens.screenPadding + getPlatform().statusBarInsetDp,
                    bottom = AppTheme.dimens.screenPadding
                ),
                modifier = Modifier.align(alignment = Alignment.BottomCenter)
            ) {
                item {
                    Text(
                        text = "Sort & Filter",
                        color = AppTheme.colors.secondary,
                        style = AppTheme.typography.h6,
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
                if (screenState.selectedCategory.wrappedItem as Category == Category.MOVIE) {
                    item {
                        MultiSelectionFilterCarousel(
                            header = "Regions",
                            selectedItems = screenState.selectedRegions,
                            items = screenState.regions,
                            onClick = { positions -> screenState.onRegionSelected(positions) }
                        )
                    }
                }
                item {
                    MultiSelectionFilterCarousel(
                        header = "Genres",
                        selectedItems = screenState.selectedGenres,
                        items = screenState.genres,
                        listState = genreListState,
                        onClick = { positions -> screenState.onGenreSelected(positions) }
                    )
                }
                item {
                    MultiSelectionFilterCarousel(
                        header = "Sort",
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
                                navigator.pop()
                            },
                            modifier = Modifier.weight(weight = 1f)
                        )
                    }
                }
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
    backgroundColor = if (isDark) Color.DarkGray else Color.LightGray,
    modifier = modifier
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
        style = AppTheme.typography.caption,
        color = if (isSelected) AppTheme.colors.onSecondary else AppTheme.colors.secondary,
        modifier = Modifier.padding(
            horizontal = AppTheme.dimens.contentPadding * 2,
            vertical = AppTheme.dimens.contentPadding
        )
    )
}