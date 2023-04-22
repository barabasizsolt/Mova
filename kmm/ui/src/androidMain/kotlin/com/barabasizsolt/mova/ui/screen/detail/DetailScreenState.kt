package com.barabasizsolt.mova.ui.screen.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.barabasizsolt.mova.domain.model.DetailScreenContent
import com.barabasizsolt.mova.domain.usecase.screen.detail.GetMovieDetailsUseCase
import com.barabasizsolt.mova.ui.screen.base.BaseScreenState
import com.barabasizsolt.mova.ui.screen.base.UserAction
import kotlinx.coroutines.launch
import com.barabasizsolt.mova.domain.util.Result

class DetailScreenState(
    val id: Int,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : BaseScreenState(), ScreenModel {
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
        coroutineScope.launch {
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
}