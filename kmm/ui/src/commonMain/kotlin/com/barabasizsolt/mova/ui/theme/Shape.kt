package com.barabasizsolt.mova.ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

val LocalShapes = staticCompositionLocalOf { AppShape() }

data class AppShape(
    val small: CornerBasedShape = RoundedCornerShape(size = 8.dp),
    val medium: CornerBasedShape = RoundedCornerShape(size = 16.dp),
    val large: CornerBasedShape = RoundedCornerShape(size = 24.dp)
)