package ui.catalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.barabasizsolt.mova.domain.model.ContentItem
import ui.theme.AppTheme

@Composable
internal fun SearchableItem(
    modifier: Modifier = Modifier,
    item: ContentItem.Watchable,
    onClick: () -> Unit
) = Row(
    modifier = modifier
        .fillMaxWidth()
        .clickable { onClick() },
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.screenPadding)
) {
    WatchableWithRating(
        item = item,
        onClick = { onClick() },
        modifier = Modifier.height(height = AppTheme.dimens.watchableCardHeight)
    )
    Column {
        Text(
            text = item.title,
            style = AppTheme.typography.body1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = item.releaseDate,
            style = AppTheme.typography.body2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )
    }
}