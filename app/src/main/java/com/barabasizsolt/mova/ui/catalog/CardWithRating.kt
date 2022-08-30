package com.barabasizsolt.mova.ui.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import com.barabasizsolt.mova.ui.theme.AppTheme
import com.barabasizsolt.mova.util.getImageKey

@Composable
fun CardWithRating(
    modifier: Modifier = Modifier,
    imageKey: String,
    imageSize: Int? = null,
    rating: String,
    aspectRatio: Float,
    onClick: () -> Unit
) = Box {
    MovaImage(
        imageUrl = imageKey.getImageKey(size = imageSize),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(shape = AppTheme.shapes.medium)
            .aspectRatio(ratio = aspectRatio)
            .clickable { onClick() }
    )
    RatingHolder(
        rating = rating,
        modifier = Modifier
            .align(alignment = Alignment.TopStart)
            .padding(all = AppTheme.dimens.contentPadding + AppTheme.dimens.smallPadding)
    )
}

@Composable
private fun RatingHolder(
    modifier: Modifier = Modifier,
    rating: String
) {
    Box(
        modifier = modifier
            .clip(shape = AppTheme.shapes.small)
            .background(color = AppTheme.colors.secondary)
    ) {
        Text(
            text = rating,
            style = AppTheme.typography.overLine,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colors.onSecondary,
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(
                    vertical = AppTheme.dimens.smallPadding,
                    horizontal = AppTheme.dimens.contentPadding
                )
        )
    }
}