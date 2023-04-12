package com.barabasizsolt.domain.usecase.helper.detail

import com.barabasizsolt.detail.api.DetailService
import com.barabasizsolt.domain.util.result.wrapToResult

class GetMovieDetailUseCase(private val detailService: DetailService) {

    suspend operator fun invoke(id: Int) = wrapToResult {
        detailService.getMovieDetails(id = id)
    }
}