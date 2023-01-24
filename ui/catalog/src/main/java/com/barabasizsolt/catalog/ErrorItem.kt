package com.barabasizsolt.catalog

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.barabasizsolt.theme.AppTheme

@Composable
fun ErrorItem(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onRetryClick: () -> Unit
) = Card(
    modifier = modifier.fillMaxWidth(),
    backgroundColor = if (isSystemInDarkTheme()) Color(color = 0xFF121212) else Color.White,
    elevation = 16.dp,
    shape = AppTheme.shapes.medium,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(all = AppTheme.dimens.screenPadding * 2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding * 2)
    ) {
        Text(
            text = stringResource(id = R.string.something_went_wrong),
            textAlign = TextAlign.Center,
            style = AppTheme.typography.body1
        )
        MovaButton(
            text = stringResource(id = R.string.try_again),
            onClick = onRetryClick,
            isLoading = isLoading
        ) 
    }
}