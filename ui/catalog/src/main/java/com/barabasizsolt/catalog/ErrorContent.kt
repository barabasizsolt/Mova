package com.barabasizsolt.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.barabasizsolt.theme.attributes.AppTheme

@Composable
fun ErrorContent(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.primary)
    ) {
        Column(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(horizontal = AppTheme.dimens.screenPadding),
            verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.screenPadding * 2),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_sad_face),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = AppTheme.colors.onBackground)
            )
            Text(
                text = "Oops, something went wrong. Please try again later.",
                style = AppTheme.typography.h6,
                color = AppTheme.colors.onPrimary,
                textAlign = TextAlign.Center
            )
            MovaButton(
                text = "Try again",
                onClick = onRetry
            )
        }
    }
}