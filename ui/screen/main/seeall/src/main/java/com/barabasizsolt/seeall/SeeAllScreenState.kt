package com.barabasizsolt.seeall

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.barabasizsolt.domain.model.WatchableItem
import com.barabasizsolt.domain.model.toWatchableItem
import com.barabasizsolt.domain.usecase.screen.seeall.GetSeeAllScreenFlowUseCase
import com.barabasizsolt.domain.usecase.screen.seeall.GetSeeAllScreenUseCase
import com.barabasizsolt.domain.usecase.screen.seeall.SeeAllContentType
import com.barabasizsolt.domain.util.Result
import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.people.model.People
import com.barabasizsolt.util.RefreshType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun rememberSeeAllScreenState(
    contentType: String,
    scope: CoroutineScope = rememberCoroutineScope(),
    getSeeAllScreenUseCase: GetSeeAllScreenUseCase = get(),
    getSeeAllScreenFlowUseCase: GetSeeAllScreenFlowUseCase = get()
) = remember {
    SeeAllScreenState(
        contentType = contentType,
        scope = scope,
        getSeeAllScreenUseCase = getSeeAllScreenUseCase,
        getSeeAllScreenFlowUseCase = getSeeAllScreenFlowUseCase
    )
}

class SeeAllScreenState(
    val contentType: String,
    private val scope: CoroutineScope,
    private val getSeeAllScreenUseCase: GetSeeAllScreenUseCase,
    private val getSeeAllScreenFlowUseCase: GetSeeAllScreenFlowUseCase
) {

    var state by mutableStateOf<State>(value = State.Normal)
        private set
    var items by mutableStateOf<List<SeeAllListItem>>(value = emptyList())
        private set
    private var watchableItems by mutableStateOf<List<WatchableItem>>(value = emptyList())

    init {
        getSeeAllScreenFlowUseCase(contentType = contentType).onEach { content ->
            watchableItems = when (contentType) {
                SeeAllContentType.POPULAR_MOVIES.name, SeeAllContentType.NOW_PLAYING_MOVIES.name, SeeAllContentType.NOW_PLAYING_MOVIES.name ->
                   (content as List<*>).map { (it as Movie).toWatchableItem() }
                SeeAllContentType.POPULAR_PEOPLE.name ->
                    (content as List<*>).map { (it as People).toWatchableItem() }
                else -> emptyList()
            }
            items = watchableItems.map { SeeAllListItem.Item(watchableItem = it) } + SeeAllListItem.LoadMore()
        }.launchIn(scope = scope)
        getScreenData(swipeRefresh = false)
    }

    fun getScreenData(swipeRefresh: Boolean) {
        if (state !is State.Loading) {
            scope.launch {
                state = if (swipeRefresh) State.SwipeRefresh else State.Loading
                state = when (
                    val result = getSeeAllScreenUseCase(
                        contentType = contentType,
                        refreshType = when {
                            swipeRefresh -> RefreshType.FORCE_REFRESH
                            watchableItems.isEmpty() -> RefreshType.CACHE_IF_POSSIBLE
                            else -> RefreshType.NEXT_PAGE
                        }
                    )
                ) {
                    is Result.Failure -> if (!swipeRefresh) State.Error(message = result.exception.message.orEmpty()) else State.ShowSnackBar
                    is Result.Success -> State.Normal
                }
            }
        }
    }

    sealed class State {
        object Normal : State()
        object Loading : State()
        object SwipeRefresh : State()
        data class Error(val message: String) : State()
        object ShowSnackBar : State()
    }
}