package com.barabasizsolt.mova.domain.usecase.helper.detail

import com.barabasizsolt.mova.detail.api.DetailService
import com.barabasizsolt.mova.domain.util.wrapToResult

class GetMovieDetailUseCase(private val detailService: DetailService) {

    suspend operator fun invoke(id: Int) = wrapToResult {
        detailService.getMovieDetails(id = id)
    }
}