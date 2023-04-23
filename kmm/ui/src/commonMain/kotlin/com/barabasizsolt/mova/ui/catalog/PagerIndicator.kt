package com.barabasizsolt.mova.ui.catalog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.barabasizsolt.mova.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    indicatorCount: Int,
    itemCount: Int,
    indicatorSize: Dp = 8.dp,
    indicatorShape: Shape = CircleShape,
    space: Dp = AppTheme.dimens.contentPadding,
    activeColor: Color = AppTheme.colors.secondary,
    inActiveColor: Color = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray,
    onClick: ((Int) -> Unit)? = null
) {

    val listState = rememberLazyListState()

    val totalWidth: Dp = indicatorSize * indicatorCount + space * (indicatorCount - 1)
    val widthInPx = LocalDensity.current.run { indicatorSize.toPx() }

    val currentItem by remember { derivedStateOf { pagerState.currentPage } }

    LaunchedEffect(key1 = currentItem) {
        val viewportSize = listState.layoutInfo.viewportSize
        listState.animateScrollToItem(
            index = currentItem,
            scrollOffset = (widthInPx / 2 - viewportSize.width / 2).toInt()
        )
    }


    LazyRow(
        modifier = modifier.width(width = totalWidth),
        state = listState,
        contentPadding = PaddingValues(vertical = space),
        horizontalArrangement = Arrangement.spacedBy(space = space),
        userScrollEnabled = false
    ) {
        items(count = itemCount) { index ->
            val isSelected = (index == currentItem)
            Box(
                modifier = Modifier
                    .clip(shape = indicatorShape)
                    .size(size = indicatorSize)
                    .background(
                        color = if (isSelected) activeColor else inActiveColor,
                        shape = indicatorShape
                    )
                    .then(if (onClick != null) { Modifier.clickable { onClick.invoke(index) } } else Modifier)
            )
        }
    }
}
