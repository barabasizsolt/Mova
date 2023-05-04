package com.barabasizsolt.cast.crew.api

import cast_crew.model.CastCrew
import category.Category

interface CastCrewService {

    suspend fun getCastCrew(id: Int, category: Category): CastCrew
}