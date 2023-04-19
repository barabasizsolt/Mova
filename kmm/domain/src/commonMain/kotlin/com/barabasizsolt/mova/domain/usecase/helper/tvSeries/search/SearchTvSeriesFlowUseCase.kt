package com.barabasizsolt.mova.domain.usecase.helper.tvSeries.search

import com.barabasizsolt.mova.domain.model.ContentItem
import com.barabasizsolt.mova.domain.model.toContentItem
import com.barabasizsolt.mova.explore.api.ExploreService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class SearchTvSeriesFlowUseCase(private val exploreService: ExploreService) {

    operator fun invoke(): Flow<List<ContentItem>> = exploreService.searchTvSeries.filterNotNull().filterNotNull().map { list ->
        list.map { item -> item.toContentItem() }
    }
}