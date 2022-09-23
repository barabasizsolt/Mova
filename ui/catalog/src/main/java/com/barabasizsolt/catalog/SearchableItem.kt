package com.barabasizsolt.catalog

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
import com.barabasizsolt.domain.model.WatchableItem
import com.barabasizsolt.theme.attributes.AppTheme

@Composable
fun SearchableItem(
    modifier: Modifier = Modifier,
    item: WatchableItem,
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
        onClick = { },
        modifier = Modifier.height(height = AppTheme.dimens.watchableCardHeight)
    )
    Column {
        Text(
            text = item.title,
            style = AppTheme.typography.body1,
            color = AppTheme.colors.onBackground,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = item.releaseDate,
            style = AppTheme.typography.body2,
            color = AppTheme.colors.onBackground,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )
    }
}