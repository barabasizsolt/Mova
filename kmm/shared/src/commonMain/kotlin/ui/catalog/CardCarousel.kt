package ui.catalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.barabasizsolt.mova.domain.model.ContentItem
import ui.theme.AppTheme

@Composable
internal fun WatchableWithRatingCarousel(
    modifier: Modifier = Modifier,
    header: String,
    buttonText: String? = null,
    showDivider: Boolean = true,
    items: List<ContentItem.Watchable>,
    onItemClick: (Int) -> Unit,
    onHeaderClick: () -> Unit,
) = CardCarousel(
    header = header,
    onHeaderClick = onHeaderClick,
    buttonText = buttonText,
    showDivider = showDivider,
    content = {
        items(
            items = items,
            key = { item -> item.id }
        ) { item ->
            WatchableWithRating(
                item = item,
                onClick = { onItemClick(item.id.toInt()) },
                modifier = Modifier.height(height = AppTheme.dimens.watchableCardHeight)
            )
        }
    },
    modifier = modifier
)

@Composable
internal fun PeopleCarousel(
    modifier: Modifier = Modifier,
    header: String? = null,
    buttonText: String? = null,
    showDivider: Boolean = true,
    personCardSize: PersonCardSize = PersonCardSize.LARGE,
    items: List<ContentItem>,
    onItemClick: (String) -> Unit,
    onHeaderClick: () -> Unit,
) = CardCarousel(
    header = header,
    onHeaderClick = onHeaderClick,
    buttonText = buttonText,
    itemSpacing = AppTheme.dimens.screenPadding,
    showDivider = showDivider,
    content = {
        itemsIndexed(
            items = items,
            key = { index, item -> "${item.id}-$index" }
        ) { _, item ->
            PersonCard(
                person = item as ContentItem.Person,
                onClick = { onItemClick(item.id) },
                personCardSize = personCardSize
            )
        }
    },
    modifier = modifier
)

@Composable
private fun CardCarousel(
    modifier: Modifier = Modifier,
    header: String?,
    buttonText: String? = null,
    showDivider: Boolean,
    onHeaderClick: () -> Unit,
    itemSpacing: Dp = AppTheme.dimens.contentPadding,
    content: LazyListScope.() -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.screenPadding)
    ) {
        if (header != null) {
            CardCarouselHeader(
                header = header,
                onHeaderClick = onHeaderClick,
                modifier = Modifier.padding(horizontal = AppTheme.dimens.screenPadding)
            )
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = AppTheme.dimens.screenPadding),
            horizontalArrangement = Arrangement.spacedBy(space = itemSpacing)
        ) {
            content()
        }
        if (buttonText != null) {
            MovaButton(
                text = buttonText,
                onClick = onHeaderClick,
                modifier = Modifier.padding(horizontal = AppTheme.dimens.screenPadding)
            )
        }
        if (showDivider) {
            Divider(
                color = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray,
                modifier = Modifier.padding(horizontal = AppTheme.dimens.screenPadding)
            )
        }
    }
}

@Composable
private fun CardCarouselHeader(
    modifier: Modifier = Modifier,
    header: String,
    onHeaderClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = header,
            style = AppTheme.typography.h6,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(weight = 1f)
        )
        Box(modifier = Modifier.clickable { onHeaderClick() }) {
            Text(
                text = "See all",
                color = AppTheme.colors.secondary,
                style = AppTheme.typography.body2,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(all = AppTheme.dimens.smallPadding)
            )
        }
    }
}