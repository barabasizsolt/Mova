package com.barabasizsolt.mova.ui.screen.main.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

object HomeScreen  : Screen {

    @Composable
    override fun Content() {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Home",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        }
    }
}