package com.barabasizsolt.mova.ui.catalog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.barabasizsolt.mova.ui.theme.AppTheme

@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(alignment = Alignment.Center),
            color = AppTheme.colors.secondary
        )
    }
}