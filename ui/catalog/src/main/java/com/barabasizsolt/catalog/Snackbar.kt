package com.barabasizsolt.catalog

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.barabasizsolt.theme.attributes.AppTheme

@Composable
fun BoxScope.MovaSnackBar(
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = { }
) = SnackbarHost(
    hostState = snackBarHostState,
    snackbar = { data ->
        Snackbar(
            modifier = Modifier.padding(all = AppTheme.dimens.contentPadding * 2),
            content = {
                Text(
                    text = data.message,
                    style = AppTheme.typography.body2
                )
            },
            action = {
                data.actionLabel?.let { actionLabel ->
                    TextButton(onClick = onDismiss) {
                        Text(
                            text = actionLabel,
                            color= AppTheme.colors.secondary,
                            style = AppTheme.typography.body2
                        )
                    }
                }
            }
        )
    },
    modifier = modifier
        .fillMaxWidth()
        .align(alignment = Alignment.BottomCenter)
        .wrapContentHeight(align = Alignment.Bottom)
)


