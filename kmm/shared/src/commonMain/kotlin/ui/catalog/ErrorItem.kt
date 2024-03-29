package ui.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.theme.AppTheme

@Composable
internal fun ErrorItem(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onRetryClick: () -> Unit
) = Card(
    modifier = modifier.fillMaxWidth(),
    backgroundColor = AppTheme.colors.background,
    elevation = 16.dp,
    shape = AppTheme.shapes.medium
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(all = AppTheme.dimens.screenPadding * 2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding * 2)
    ) {
        Text(
            text = AppTheme.strings.snackBarErrorMessage,
            textAlign = TextAlign.Center,
            style = AppTheme.typography.body1
        )
        MovaButton(
            text = AppTheme.strings.snackBarActionLabel,

            onClick = onRetryClick,
            isLoading = isLoading
        ) 
    }
}