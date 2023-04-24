package ui.screen.detail

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import ui.screen.base.BaseScreen

actual class DetailScreen actual constructor(private val id: Int) : Screen, KoinComponent {

    private val screenState: DetailScreenState by inject { parametersOf(id) }

    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow

        BaseScreen(
            screenState = screenState,
            content = { gridState, _ ->
                ScreenContent(
                    items = screenState.screenDetailList,
                    gridState = gridState,
                    tabs = screenState.tabs,
                    onTabIndexChange = screenState::onTabIndexChange,
                    onMovieClicked = { id -> navigator.push(item = DetailScreen(id = id)) },
                    onAddToFavouriteButtonClicked = { /*TODO: Implement it*/ },
                    onPlayButtonClicked = { /*TODO: Implement it*/ }
                )
            }
        )
    }
}