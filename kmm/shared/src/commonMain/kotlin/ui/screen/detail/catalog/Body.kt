package ui.screen.detail.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import review.model.Review
import ui.catalog.MovaImage
import ui.catalog.PersonCardSize
import ui.theme.AppTheme
import ui.util.ExpandingText
import ui.util.ImageType
import ui.util.getImageKey
import ui.util.isValidUrl

@Composable
internal fun ContentTabs(
    initTabIndex: Int = 0,
    tabs: List<String>,
    onTabIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    var selectedTab by rememberSaveable { mutableStateOf(value = initTabIndex)  }

    TabRow(
        selectedTabIndex = selectedTab,
        indicator = { tabPositions -> TabRowIndicator(tabPosition = tabPositions[selectedTab]) },
        backgroundColor = AppTheme.colors.primary,
        divider = { TabRowDivider(isDark = isDark) },
        tabs = {
            tabs.forEachIndexed { index, title ->
                ContentTab(
                    isSelected = selectedTab == index,
                    onClick = {
                       if (selectedTab != index) {
                           selectedTab = index
                           onTabIndexChange(index)
                       }
                    },
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

@Composable
internal fun Review(
    modifier: Modifier = Modifier,
    review: Review
) = Column(
    modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = AppTheme.dimens.screenPadding)
) {
    ReviewAuthor(
        author = review.author,
        authorUsername = review.authorUsername,
        authorAvatarPath = review.authorAvatarPath,
        modifier = Modifier.padding(bottom = AppTheme.dimens.smallPadding)
    )

    ExpandingText(
        text = review.content,
        color = AppTheme.colors.onSurface,
        tailTextColor = AppTheme.colors.secondary,
        style = AppTheme.typography.body2,
        modifier = modifier.fillMaxWidth()
    )

    val createdAt = AppTheme.strings.createdAt
    Text(
        text = "$createdAt: ${review.createdAt.substringBefore(delimiter = "T")}",
        color = Color.Gray,
        style = AppTheme.typography.body2,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun ReviewAuthor(
    modifier: Modifier = Modifier,
    author: String,
    authorUsername: String?,
    authorAvatarPath: String?
) = Row(
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding)
) {
    MovaImage(
        imageUrl = if (authorAvatarPath?.substring(startIndex = 1)?.isValidUrl() == true)
            authorAvatarPath.substring(startIndex = 1)
        else
            authorAvatarPath?.getImageKey(imageType = ImageType.ORIGINAL),
        modifier = Modifier
            .size(size = PersonCardSize.SMALL.size)
            .clip(shape = CircleShape),
        contentScale = ContentScale.Crop,
        disableShimmerOnError = true,
        shouldShowFallbackOnError = true
    )
    Text(
        text = "$author ($authorUsername)",
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = AppTheme.typography.body2,
        fontWeight = FontWeight.Bold,
        color = AppTheme.colors.onPrimary
    )
}