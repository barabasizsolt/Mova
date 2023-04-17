package com.barabasizsolt.mova.domain.usecase.helper.tvSeries.discover

import com.barabasizsolt.mova.domain.model.ContentItem
import com.barabasizsolt.mova.domain.model.toContentItem
import com.barabasizsolt.mova.explore.api.ExploreService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class DiscoverTvSeriesFlowUseCase(private val exploreService: ExploreService) {

    operator fun invoke(): Flow<List<ContentItem>> = exploreService.discoverTvSeries.filterNotNull().map { list ->
        list.distinctBy { it.id }.map { item -> item.toContentItem() }
    }
}