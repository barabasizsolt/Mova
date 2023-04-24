package ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import ui.screen.base.BaseScreen
import ui.theme.AppTheme

/*TODO: remember the scroll position*/
actual class DetailScreen actual constructor(private val id: Int) : Screen, KoinComponent {

    private val screenState: DetailScreenState by inject { parametersOf(id) }

    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow

        val detailScreenAdapter = rememberDetailScreenAdapter(
            onPlayButtonClicked = { /* TODO: Implement it */ },
            onAddToFavouriteButtonClicked = { /* TODO: Implement it */ },
            onTabIndexChange = screenState::onTabIndexChange,
            onMovieClicked = { id -> navigator.push(item = DetailScreen(id = id)) },
            isDark = isSystemInDarkTheme()
        )

        LaunchedEffect(
            key1 = screenState.tabIndex,
            block = { detailScreenAdapter.submitList(screenState.screenDetailList) }
        )

        BaseScreen(screenState = screenState) { _, _ ->
            AndroidView(
                factory = { context ->
                    val recyclerView = RecyclerView(context).apply {
                        layoutManager = GridLayoutManager(context, 2).apply {
                            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                                override fun getSpanSize(position: Int): Int = when (detailScreenAdapter.getItemViewType(position)) {
                                    ItemViewType.SIMILAR_MOVIE.ordinal -> 1
                                    else -> 2
                                }
                            }
                        }
                        adapter = detailScreenAdapter
                    }
                    detailScreenAdapter.submitList(screenState.screenDetailList)
                    recyclerView
                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = AppTheme.colors.primary)
            )
        }
    }
}