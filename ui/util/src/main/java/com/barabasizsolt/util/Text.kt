package com.barabasizsolt.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle

fun TextStyle.withShadow() = this.copy(
    shadow = Shadow(
        color = Color.Black,
        offset = Offset(x = 4f, y = 4f),
        blurRadius = 8f
    )
)