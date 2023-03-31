package com.barabasizsolt.detail.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barabasizsolt.catalog.PeopleCarousel
import com.barabasizsolt.catalog.PersonCardSize
import com.barabasizsolt.domain.model.ContentItem
import com.barabasizsolt.theme.AppTheme
import com.barabasizsolt.util.ExpandingText

@Composable
fun CastCrewContent(
    castCrew: List<ContentItem.Person>,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit
) = PeopleCarousel(
    modifier = modifier.fillMaxWidth(),
    showDivider = false,
    personCardSize = PersonCardSize.MEDIUM,
    items = castCrew,
    onItemClick = onItemClick,
    onHeaderClick = {}
)

@Composable
fun ContentTabs(
    modifier: Modifier = Modifier,
    tabIndex: Int,
    onTabIndexChange: (Int) -> Unit
) {
    val tabTitles = listOf("Similar", "Videos", "Reviews") /*TODO: move to res*/
    val isDark = isSystemInDarkTheme()

    TabRow(
        selectedTabIndex = tabIndex,
        indicator = { tabPositions -> TabRowIndicator(tabPosition = tabPositions[tabIndex]) },
        backgroundColor = Color.Transparent,
        divider = { TabRowDivider(isDark = isDark) },
        tabs = {
            tabTitles.forEachIndexed { index, title ->
                ContentTab(
                    isSelected = tabIndex == index,
                    onClick = { onTabIndexChange(index) },
                    isDark = isDark,
                    title = title
                )
            }
        },
        modifier = modifier.padding(horizontal = AppTheme.dimens.screenPadding)
    )
}

@Composable
private fun ContentTab(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    title: String,
    isDark: Boolean,
    onClick: () -> Unit
) = Tab(
    selected = isSelected,
    onClick = onClick,
    text = {
        Text(
            text = title,
            fontSize = 14.sp,
            letterSpacing = 0.25.sp,
            fontWeight = FontWeight.Bold
        )
    },
    selectedContentColor = AppTheme.colors.secondary,
    unselectedContentColor = if (isDark) Color.Gray else Color.DarkGray,
    modifier = modifier.height(height = 36.dp)
)

@Composable
private fun TabRowIndicator(
    modifier: Modifier = Modifier,
    tabPosition: TabPosition
) = Box(
    modifier = modifier
        .tabIndicatorOffset(currentTabPosition = tabPosition)
        .fillMaxWidth()
        .height(height = 6.dp)
        .background(
            color = AppTheme.colors.secondary,
            shape = CircleShape
        )
)

@Composable
private fun TabRowDivider(
    modifier: Modifier = Modifier,
    isDark: Boolean
) = Box(
    modifier = modifier
        .fillMaxWidth()
        .height(height = 2.dp)
        .offset(y = (-2).dp)
        .background(
            color = if (isDark) Color.Gray else Color.DarkGray,
            shape = CircleShape
        )
)