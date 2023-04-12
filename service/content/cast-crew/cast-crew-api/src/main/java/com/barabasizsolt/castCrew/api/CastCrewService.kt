package com.barabasizsolt.castCrew.api

import com.barabasizsolt.cast_crew.model.CastCrew
import com.barabasizsolt.category.Category

interface CastCrewService {

    suspend fun getCastCrew(id: Int, category: Category): CastCrew
}