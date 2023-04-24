package ui.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun BoxScope.GradientOverlay(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.BottomCenter,
    maxHeightFraction: Float = 1f,
    colors: List<Color>
) = Box(modifier = modifier.matchParentSize()) {
    Spacer(
        modifier = Modifier
            .align(alignment = alignment)
            .fillMaxWidth()
            .fillMaxHeight(fraction = maxHeightFraction)
            .background(brush = Brush.verticalGradient(colors = colors))
    )
}