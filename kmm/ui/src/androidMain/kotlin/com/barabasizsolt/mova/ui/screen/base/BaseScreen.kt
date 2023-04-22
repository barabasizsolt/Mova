package com.barabasizsolt.mova.ui.screen.base

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.barabasizsolt.mova.ui.R
import com.barabasizsolt.mova.ui.catalog.ErrorContent
import com.barabasizsolt.mova.ui.catalog.LoadingContent
import com.barabasizsolt.mova.ui.catalog.MovaSnackBar
import com.barabasizsolt.mova.ui.catalog.ScrollUpWrapper
import com.barabasizsolt.mova.ui.theme.AppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BaseScreen(
    snackBarModifier: Modifier = Modifier,
    screenState: BaseScreenState,
    gridState: LazyGridState = rememberLazyGridState(),
    onSnackBarDismissed: (() -> Unit)? = null,
    scrollUpTopPadding: Dp = AppTheme.dimens.screenPadding,
    shouldShowScrollUp: Boolean = true,
    content: @Composable (LazyGridState, CoroutineScope) -> Unit
) {
    val snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val snackBarErrorMessage = stringResource(id = R.string.snackbar_error_message)
    val snackBarErrorActionLabel = stringResource(id = R.string.snackbar_action_label)
    val swipeRefreshState: SwipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = screenState.state is BaseScreenState.State.SwipeRefresh
    )
    val scope: CoroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.background(color = AppTheme.colors.primary)) {
        when (screenState.state) {
            is BaseScreenState.State.Error -> ErrorContent(onRetry = { screenState.getScreenData(userAction = UserAction.Error) })
            is BaseScreenState.State.Loading -> LoadingContent()
            else -> SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { screenState.getScreenData(userAction = UserAction.SwipeRefresh) },
                content = {
                    ScrollUpWrapper(
                        gridState = gridState,
                        scrollUpTopPadding = scrollUpTopPadding,
                        shouldShow = shouldShowScrollUp,
                        content = { content(gridState, scope) }
                    )
                }
            )
        }

        BackHandler(
            enabled = remember {
                derivedStateOf { gridState.firstVisibleItemScrollOffset }
            }.value > 0
        ) {
            scope.launch {
                gridState.scrollToItem(index = 0, scrollOffset = 0)
            }
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
    }
}