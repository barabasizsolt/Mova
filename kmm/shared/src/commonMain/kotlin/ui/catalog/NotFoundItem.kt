package ui.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.theme.AppTheme

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun NotFoundItem(modifier: Modifier = Modifier) = Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding * 2)
) {
    Image(
        painter = painterResource(res = "drawable/not_found.png"),
        contentDescription = "Content not found",
        contentScale = ContentScale.Crop,
        modifier = Modifier.height(height = 200.dp)
    )
    Text(
        text = AppTheme.strings.notFound,
        style = AppTheme.typography.h5,
        color = AppTheme.colors.secondary,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = AppTheme.strings.notFoundText,
        style = AppTheme.typography.body2,
        textAlign = TextAlign.Center
    )
}