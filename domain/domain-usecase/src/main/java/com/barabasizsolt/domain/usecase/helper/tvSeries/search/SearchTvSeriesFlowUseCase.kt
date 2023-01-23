package com.barabasizsolt.domain.usecase.helper.tvSeries.search

import com.barabasizsolt.discover.api.ExploreService
import com.barabasizsolt.domain.model.ContentItem
import com.barabasizsolt.domain.model.toContentItem
import com.barabasizsolt.pagination.api.ErrorItem
import com.barabasizsolt.pagination.api.TailItem
import com.barabasizsolt.tv.modell.TvSeries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class SearchTvSeriesFlowUseCase(private val exploreService: ExploreService) {

    operator fun invoke(): Flow<List<ContentItem>> = exploreService.searchTvSeries.filterNotNull().filterNotNull().map { list ->
        list.map { item ->
            when (item) {
                is TailItem -> item.toContentItem()
                is ErrorItem -> item.toContentItem()
                else -> (item as TvSeries).toContentItem()
            }
        }
    }
}