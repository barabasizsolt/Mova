package com.barabasizsolt.explore

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
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.barabasizsolt.theme.AppTheme
import com.barabasizsolt.theme.MovaTheme
import com.barabasizsolt.util.movieGenres
import java.util.Locale

/*TODO: Refactor*/

@Preview
@Composable
fun FilterScreenPreview() = MovaTheme(isDarkTheme = true) {
    FilterScreen()
}

@Composable
fun FilterScreen() {
    val isoCountries = Locale.getISOCountries().map { locale -> Locale("", locale) }
    val isDark: Boolean = isSystemInDarkTheme()
    var invalidateFlag by rememberSaveable { mutableStateOf(value = false) }

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
            FilterCarousel(
                header = "Categories",
                selectedItemPositions = listOf(0),
                items = listOf(
                    FilterItem(name = "All Categories", value = ""),
                    FilterItem(name = "Movie", value = "movie"),
                    FilterItem(name = "Tv Series", value = "tv")
                ),
                onClick = { },
                invalidateFlag = invalidateFlag
            )
        }
        item {
            FilterCarousel(
                header = "Regions",
                selectedItemPositions = listOf(0),
                items = listOf(FilterItem(name = "All Regions", value = "")) + isoCountries.map { FilterItem(name = it.displayName, value = it.country) },
                onClick = { },
                invalidateFlag = invalidateFlag
            )
        }
        item {
            FilterCarousel(
                header = "Genres",
                selectedItemPositions = listOf(0),
                items = listOf(FilterItem(name = "All Genres", value = "")) + movieGenres.entries.map { FilterItem(name = it.value, value = it.key.toString()) },
                onClick = { },
                invalidateFlag = invalidateFlag
            )
        }
        item {
            FilterCarousel(
                header = "Sort",
                selectedItemPositions = listOf(0),
                items = listOf(
                    FilterItem(name = "None", value = "none"),
                    FilterItem(name = "Popularity", value = "popularity"),
                    FilterItem(name = "Latest Release", value = "release_date")
                ),
                onClick = { },
                invalidateFlag = invalidateFlag
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
                    onClick = { invalidateFlag = !invalidateFlag },
                    isDark = isDark,
                    modifier = Modifier.weight(weight = 1f)
                )
                ApplyButton(
                    onClick = {},
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
private fun FilterCarousel(
    modifier: Modifier = Modifier,
    header: String,
    selectedItemPositions: List<Int>,
    items: List<FilterItem>,
    invalidateFlag: Boolean,
    onClick: () -> Unit
) = Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding * 2)
) {
    var selectedPositions by rememberSaveable { mutableStateOf(value = selectedItemPositions) }

    LaunchedEffect(
        key1 = invalidateFlag,
        block = { selectedPositions = listOf(0) }
    )

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
            FilterItem(
                text = item.name,
                isSelected = selectedPositions.contains(element = index),
                onClick = {
                    val oldPositions = selectedPositions.toMutableList()
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
                            else -> oldPositions.add(element = index)
                        }
                    }
                    selectedPositions = oldPositions
                    onClick()
                }
            )
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