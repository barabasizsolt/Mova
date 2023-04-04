package com.barabasizsolt.domain.usecase.helper.movie.discover

import com.barabasizsolt.discover.api.ExploreService
import com.barabasizsolt.domain.model.ContentItem
import com.barabasizsolt.domain.model.toContentItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class DiscoverMoviesFlowUseCase(private val exploreService: ExploreService) {

    operator fun invoke(): Flow<List<ContentItem>> = exploreService.discoverMovies.filterNotNull().map { list ->
        list.distinctBy { it.id }.map { item -> item.toContentItem() }
    }
}