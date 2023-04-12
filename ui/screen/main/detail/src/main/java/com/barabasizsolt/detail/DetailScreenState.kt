package com.barabasizsolt.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.barabasizsolt.base.BaseScreenState
import com.barabasizsolt.base.UserAction
import com.barabasizsolt.domain.model.DetailScreenContent
import com.barabasizsolt.domain.usecase.screen.detail.GetMovieDetailsUseCase
import com.barabasizsolt.domain.util.result.Result
import com.barabasizsolt.util.Event
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun rememberDetailScreenState(
    id: Int,
    getMovieDetailsUseCase: GetMovieDetailsUseCase = get()
) = remember {
    DetailScreenState(
        id = id,
        getMovieDetailsUseCase = getMovieDetailsUseCase
    )
}

class DetailScreenState(
    val id: Int,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : BaseScreenState() {

    var action by mutableStateOf<Event<Action>?>(value = null)
        private set
    var screenDetailList by mutableStateOf<List<DetailScreenListItem>>(value = emptyList())
        private set
    private var details by mutableStateOf(value = DetailScreenContent.MovieDetails.createEmptyMovieDetailContent())
    var tabIndex by mutableStateOf(value = 0)
        private set
    private val tabs: List<String> = listOf("Similar", "Trailers", "Reviews")

    init {
        getScreenData(userAction = UserAction.Normal)
    }

    override fun getScreenData(userAction: UserAction, delay: Long) {
        state = when (userAction) {
            UserAction.SwipeRefresh -> State.SwipeRefresh
            else -> State.Loading
        }
        scope.launch {
            state = when (val result = getMovieDetailsUseCase(id = id)) {
                is Result.Failure -> {
                    State.Error(message = result.exception.message.toString())
                }
                is Result.Success -> {
                    details = result.data
                    screenDetailList = buildList {
                        add(element = details.toHeaderItem())
                        add(element = DetailScreenListItem.TabsItem(tabs = tabs))
                        addAll(elements = details.toSimilarMoviesItem())
                    }
                    State.Normal
                }
            }
        }
    }

    fun onMovieClicked(id: Int) {
        action = Event(data = Action.OnMovieClicked(id = id))
    }

    fun onTabIndexChange(index: Int) {
        screenDetailList = buildList {
            add(element = details.toHeaderItem())
            add(element = DetailScreenListItem.TabsItem(tabs = tabs))
            when (index) {
                0 -> addAll(elements = details.toSimilarMoviesItem())
                1 -> addAll(elements = details.toVideosItem())
                2 ->  addAll(elements = details.toReviewsItem())
            }
        }
        tabIndex = index
    }

    sealed class Action {
        data class OnMovieClicked(val id: Int) : Action()
    }
}