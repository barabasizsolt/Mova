package com.barabasizsolt.cast.crew.implementation

import cast_crew.model.CastCrew
import category.Category
import com.barabasizsolt.cast.crew.api.CastCrewService

class CastCrewServiceImpl(private val remoteSource: CastCrewRemoteSource) : CastCrewService {

    override suspend fun getCastCrew(id: Int, category: Category): CastCrew = when (category) {
        Category.MOVIE -> remoteSource.getCastCrewForMovies(id = id)
        Category.TV -> remoteSource.getCastCrewForTv(id = id)
    }
}