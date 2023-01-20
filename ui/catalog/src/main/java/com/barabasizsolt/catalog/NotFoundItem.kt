package com.barabasizsolt.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.barabasizsolt.theme.AppTheme

@Composable
fun NotFoundItem(modifier: Modifier = Modifier) = Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding * 2)
) {
    Image(
        painter = painterResource(id = R.drawable.not_found),
        contentDescription = "Content not found",
        contentScale = ContentScale.Crop,
        modifier = Modifier.height(height = 200.dp)
    )
    Text(
        text = stringResource(id = R.string.not_found_header),
        style = AppTheme.typography.h5,
        color = AppTheme.colors.secondary,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = stringResource(id = R.string.not_found_text),
        style = AppTheme.typography.body2,
        textAlign = TextAlign.Center
    )
}