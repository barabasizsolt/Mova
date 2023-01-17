package com.barabasizsolt.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.barabasizsolt.catalog.ErrorContent
import com.barabasizsolt.catalog.LoadingContent
import com.barabasizsolt.catalog.MovaSnackBar
import com.barabasizsolt.theme.AppTheme
import com.barabasizsolt.util.R

@Composable
fun BaseScreen(
    screenState: BaseScreenState,
    snackBarModifier: Modifier = Modifier,
    onSnackBarDismissed: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val snackBarErrorMessage = stringResource(id = R.string.snackbar_error_message)
    val snackBarErrorActionLabel = stringResource(id = R.string.snackbar_action_label)

    Box(modifier = Modifier.background(color = AppTheme.colors.primary)) {
        when (screenState.state) {
            is BaseScreenState.State.Error -> ErrorContent(onRetry = { screenState.getScreenData(swipeRefresh = false) })
            is BaseScreenState.State.Loading -> LoadingContent()
            else -> content()
        }

        MovaSnackBar(
            snackBarHostState = snackBarHostState,
            onDismiss = {
                snackBarHostState.currentSnackbarData?.dismiss()
                if (onSnackBarDismissed != null) {
                    onSnackBarDismissed()
                }
            },
            modifier = snackBarModifier
        )

        LaunchedEffect(
            key1 = screenState.state,
            block = {
                if (screenState.state is BaseScreenState.State.ShowSnackBar) {
                    snackBarHostState.showSnackbar(
                        message = snackBarErrorMessage,
                        actionLabel = snackBarErrorActionLabel
                    )
                }
            }
        )

        DisposableEffect(
            key1 = Unit,
            effect = { onDispose { screenState.onClear() } }
        )
    }
}