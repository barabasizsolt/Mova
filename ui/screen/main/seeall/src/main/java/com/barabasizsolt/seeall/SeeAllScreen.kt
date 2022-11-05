package com.barabasizsolt.seeall

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.barabasizsolt.theme.attributes.AppTheme

@Composable
fun SeeAllScreen(screenState: SeeAllScreenState) {
    Box(Modifier.fillMaxSize()) {
        Text(
            text = screenState.contentType,
            style = AppTheme.typography.h4,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}