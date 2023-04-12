package com.barabasizsolt.domain.usecase.helper.`cast-crew`

import com.barabasizsolt.castCrew.api.CastCrewService
import com.barabasizsolt.category.Category
import com.barabasizsolt.domain.util.result.wrapToResult

class GetCastCrewUseCase(private val castCrewService: CastCrewService) {

    suspend operator fun invoke(id: Int, category: Category) = wrapToResult {
        castCrewService.getCastCrew(id = id, category = category)
    }
}