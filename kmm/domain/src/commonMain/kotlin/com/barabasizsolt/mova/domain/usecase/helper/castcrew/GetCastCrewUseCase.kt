package com.barabasizsolt.mova.domain.usecase.helper.castcrew

import category.Category
import com.barabasizsolt.cast.crew.api.CastCrewService
import com.barabasizsolt.mova.domain.util.wrapToResult

class GetCastCrewUseCase(private val castCrewService: CastCrewService) {

    suspend operator fun invoke(id: Int, category: Category) = wrapToResult {
        castCrewService.getCastCrew(id = id, category = category)
    }
}