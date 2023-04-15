package com.barabasizsolt.castCrew.implementation

import com.barabasizsolt.castCrew.api.CastCrewService
import com.barabasizsolt.cast_crew.model.CastCrew
import com.barabasizsolt.category.Category

class CastCrewServiceImpl(private val remoteSource: CastCrewRemoteSource) : CastCrewService {

    override suspend fun getCastCrew(id: Int, category: Category): CastCrew = when (category) {
        Category.MOVIE -> remoteSource.getCastCrewForMovies(id = id)
        Category.TV -> remoteSource.getCastCrewForTv(id = id)
    }
}