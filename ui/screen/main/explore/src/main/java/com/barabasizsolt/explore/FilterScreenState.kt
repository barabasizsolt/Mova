package com.barabasizsolt.explore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.model.ScreenModel
import com.barabasizsolt.domain.usecase.helper.discover.movie.GetMovieDiscoverUseCase
import com.barabasizsolt.domain.usecase.helper.discover.tv.GetTvDiscoverUseCase
import com.barabasizsolt.domain.usecase.screen.explore.Category
import com.barabasizsolt.domain.util.FilterType
import com.barabasizsolt.util.movieGenres
import org.koin.androidx.compose.get
import java.util.Locale

@Composable
fun rememberFilterScreenState(
    getMovieDiscoverUseCase: GetMovieDiscoverUseCase = get(),
    getTvDiscoverUseCase: GetTvDiscoverUseCase = get(),
): FilterScreenState = remember {
    FilterScreenState(
        getMovieDiscoverUseCase = getMovieDiscoverUseCase,
        getTvDiscoverUseCase = getTvDiscoverUseCase
    )
}
class FilterScreenState(
    private val getMovieDiscoverUseCase: GetMovieDiscoverUseCase,
    private val getTvDiscoverUseCase: GetTvDiscoverUseCase,
) : ScreenModel {

    val categories: List<FilterItem> = listOf(
        FilterItem(name = "All Categories", value = ""),
        FilterItem(name = "Movie", value = Category.MOVIE.name.lowercase(Locale.getDefault())),
        FilterItem(name = "Tv Series", value = Category.TV.name.lowercase(Locale.getDefault()))
    )

    val regions: List<FilterItem> =
        listOf(FilterItem(name = "All Regions", value = "")) + Locale.getISOCountries().map { locale -> locale.convertToFilterItem() }

    val genres: List<FilterItem> =
        listOf(FilterItem(name = "All Genres", value = "")) + movieGenres.entries.map { FilterItem(name = it.value, value = it.key.toString()) }

    val sort: List<FilterItem> = listOf(
        FilterItem(name = "Default", value = FilterType.DEFAULT.value),
        FilterItem(name = "Latest Release", value = FilterType.LATEST_RELEASE.value),
        FilterItem(name = "Vote Average", value = FilterType.VOTE_AVERAGE.value)
    )

    private fun String.convertToFilterItem(): FilterItem {
        val locale = Locale("", this)
        return FilterItem(name = locale.displayName, value = locale.country)
    }
}