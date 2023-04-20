package com.barabasizsolt.mova.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.ComposeUIViewController
import com.barabasizsolt.mova.ui.theme.MovaTheme

@Composable
fun MainViewController() = ComposeUIViewController {
    MovaTheme {
        Box {
            Text(
                text = "Hello IOS, from Compose",
                color = Color.Black,
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        }
    }
}