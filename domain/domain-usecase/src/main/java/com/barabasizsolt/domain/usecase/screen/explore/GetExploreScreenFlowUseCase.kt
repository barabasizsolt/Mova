package com.barabasizsolt.domain.usecase.screen.explore

import com.barabasizsolt.domain.model.ContentItem
import com.barabasizsolt.domain.model.toContentItem
import com.barabasizsolt.domain.usecase.helper.discover.movie.GetMovieDiscoverFlowUseCase
import com.barabasizsolt.domain.usecase.helper.discover.tv.GetTvDiscoverFlowUseCase
import com.barabasizsolt.movie.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import com.barabasizsolt.tv.modell.TvSeries
import com.barabasizsolt.util.pagination.ErrorItem
import com.barabasizsolt.util.pagination.TailItem

class GetExploreScreenFlowUseCase(
    private val getMovieDiscoverFlowUseCase: GetMovieDiscoverFlowUseCase,
    private val getTvDiscoverFlowUseCase: GetTvDiscoverFlowUseCase
) {

    operator fun invoke(category: Category): Flow<List<ContentItem>> = when (category) {
        Category.MOVIE -> getMovieDiscoverFlowUseCase().map { item ->
            item.map {
                when (it) {
                    is TailItem -> it.toContentItem()
                    is ErrorItem -> it.toContentItem()
                    else -> (it as Movie).toContentItem()
                }
                //if (it is TailItem) it.toContentItem() else (it as Movie).toContentItem()
            }
        }.filterNotNull()
        Category.TV -> getTvDiscoverFlowUseCase().map { item ->
            item.map {
                when (it) {
                    is TailItem -> it.toContentItem()
                    is ErrorItem -> it.toContentItem()
                    else -> (it as TvSeries).toContentItem()
                }
                //if (it is TailItem) it.toContentItem() else (it as TvSeries).toContentItem()
            }
        }.filterNotNull()
    }
}