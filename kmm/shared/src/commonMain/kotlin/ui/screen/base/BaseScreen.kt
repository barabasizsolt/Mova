package ui.screen.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.CoroutineScope
import ui.catalog.ErrorContent
import ui.catalog.LoadingContent
import ui.catalog.MovaSnackBar
import ui.catalog.ScrollUpWrapper
import ui.theme.AppTheme

@OptIn(ExperimentalMaterialApi::class)
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
    val swipeRefreshState: PullRefreshState = rememberPullRefreshState(
        refreshing = screenState.state is BaseScreenState.State.SwipeRefresh,
        onRefresh = { screenState.getScreenData(userAction = UserAction.SwipeRefresh) }
    )
    val scope: CoroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.background(color = AppTheme.colors.primary)) {
        when (screenState.state) {
            is BaseScreenState.State.Error -> ErrorContent(onRetry = { screenState.getScreenData(userAction = UserAction.Error) })
            is BaseScreenState.State.Loading -> LoadingContent()
            else -> Box(modifier = Modifier.pullRefresh(state = swipeRefreshState)) {
                ScrollUpWrapper(
                    gridState = gridState,
                    scrollUpTopPadding = scrollUpTopPadding,
                    shouldShow = shouldShowScrollUp,
                    content = { content(gridState, scope) }
                )

                PullRefreshIndicator(
                    refreshing = screenState.state is BaseScreenState.State.SwipeRefresh,
                    state = swipeRefreshState,
                    modifier = Modifier.align(alignment = Alignment.TopCenter)
                )
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
                        message = "Oops, something went wrong.",
                        actionLabel = "Try again"
                    )
                }
            }
        )
    }
}