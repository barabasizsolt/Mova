package com.barabasizsolt.favourites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FavouritesScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Favourites",
            style = MaterialTheme.typography.h3,
            modifier = Modifier.align(alignment = Alignment.Center)
        )
    }
}