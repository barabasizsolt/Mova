package com.barabasizsolt.mova.ui.catalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
fun PersonCard(
    modifier: Modifier = Modifier,
    person: WatchableItem,
    onClick: () -> Unit
) = Row(
    modifier = modifier.clickable { onClick() },
    horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
    verticalAlignment = Alignment.CenterVertically
) {
    val names = person.primaryInfo.split(" ", limit = 2)

    MovaImage(
        imageUrl = person.posterPath.getImageKey(imageType = ImageType.ORIGINAL),
        modifier = Modifier
            .size(size = 80.dp)
            .clip(shape = CircleShape),
        contentScale = ContentScale.Crop
    )
    Column {
        NameText(name = names[0])
        NameText(name = names[1])
    }
}

@Composable
private fun NameText(
    modifier: Modifier = Modifier,
    name: String
) = Text(
    modifier = modifier,
    text = name,
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
    style = AppTheme.typography.body1,
    fontWeight = FontWeight.Bold,
    color = AppTheme.colors.onPrimary
)