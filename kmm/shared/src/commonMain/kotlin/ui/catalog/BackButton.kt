package ui.catalog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.AppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun BackButton(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) = Card(
    modifier = modifier.size(size = 40.dp),
    shape = CircleShape,
    backgroundColor = AppTheme.colors.secondary,
    onClick = onBackPressed,
    border = BorderStroke(2.dp, Color.White)
) {
    Icon(
        imageVector = Icons.Default.ArrowBackIosNew,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier.padding(all = 10.dp)
    )
}