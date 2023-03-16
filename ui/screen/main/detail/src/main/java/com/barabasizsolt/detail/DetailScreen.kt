package com.barabasizsolt.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.barabasizsolt.theme.AppTheme

@Composable
fun DetailScreen(screenState: DetailScreenState) {
    Box {
        Text(
            text = "Detail",
            style = AppTheme.typography.h6,
            modifier = Modifier.align(alignment = Alignment.Center)
        )
    }
}