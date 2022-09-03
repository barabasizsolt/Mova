package com.barabasizsolt.mova.ui.catalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.barabasizsolt.domain.model.WatchableItem
import com.barabasizsolt.mova.ui.theme.AppTheme
import com.barabasizsolt.mova.util.ImageType
import com.barabasizsolt.mova.util.getImageKey

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
    MovaImage(
        imageUrl = item.posterPath.getImageKey(imageType = ImageType.ORIGINAL),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(height = AppTheme.dimens.watchableCardHeight)
            .clip(shape = AppTheme.shapes.medium)
            .aspectRatio(ratio = .7f)
    )
    Text(
        text = item.primaryInfo,
        style = AppTheme.typography.body1,
        color = AppTheme.colors.onBackground,
        overflow = TextOverflow.Ellipsis,
        fontWeight = FontWeight.Bold
    )
}