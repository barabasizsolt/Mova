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

    var details by mutableStateOf<DetailScreenContent>(value = DetailScreenContent.MovieDetails.createEmptyMovieDetailContent())
        private set

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
                    State.Normal
                }
            }
        }
    }
}