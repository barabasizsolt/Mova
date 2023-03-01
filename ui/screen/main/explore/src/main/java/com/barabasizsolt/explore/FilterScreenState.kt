package com.barabasizsolt.explore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.barabasizsolt.base.BaseScreenState
import com.barabasizsolt.base.UserAction
import com.barabasizsolt.domain.usecase.helper.genre.GetGenresFlowUseCase
import com.barabasizsolt.domain.usecase.screen.explore.Category
import com.barabasizsolt.domain.util.FilterType
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.get
import java.util.Locale

@Composable
fun rememberFilterScreenState(
    getGenresFlowUseCase: GetGenresFlowUseCase = get()
): FilterScreenState = remember {
    FilterScreenState(getGenresFlowUseCase = getGenresFlowUseCase)
}

class FilterScreenState(private val getGenresFlowUseCase: GetGenresFlowUseCase) : BaseScreenState() {

    val categories: List<FilterItem> = listOf(
        FilterItem(name = "All Categories", value = ""),
        FilterItem(name = "Movie", value = Category.MOVIE.name.lowercase(Locale.getDefault())),
        FilterItem(name = "Tv Series", value = Category.TV.name.lowercase(Locale.getDefault()))
    )

    val regions: List<FilterItem> = listOf(
        FilterItem(name = "All Regions", value = "")
    ) + Locale.getISOCountries().map { locale -> locale.convertToFilterItem() }

    var genres by mutableStateOf<List<FilterItem>>(value = emptyList())
        private set

    val sortOptions: List<FilterItem> = listOf(
        FilterItem(name = "Popularity [Default]", value = FilterType.DEFAULT.value),
        FilterItem(name = "Latest Release", value = FilterType.LATEST_RELEASE.value),
        FilterItem(name = "Vote Average", value = FilterType.VOTE_AVERAGE.value)
    )

    var event by mutableStateOf<Event?>(value = null)
        private set

    init {
        getScreenData(userAction = UserAction.Normal)
    }

    override fun getScreenData(userAction: UserAction, delay: Long) {
        getGenresFlowUseCase().onEach {
            genres = buildList {
                add(FilterItem(name = "All Genres", value = ""))
                addAll(it.entries.map { FilterItem(name = it.value, value = it.key.toString()) })
            }
        }.launchIn(scope = scope)
    }

    fun onApplyButtonClicked() {
        event = Event.ApplyButtonClicked(key = System.currentTimeMillis())
    }

    private fun String.convertToFilterItem(): FilterItem {
        val locale = Locale("", this)
        return FilterItem(name = locale.displayName, value = locale.country)
    }

    sealed class Event {
        data class ApplyButtonClicked(val key: Long) : Event()
    }
}