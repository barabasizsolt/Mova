package com.barabasizsolt.mova.domain.usecase.helper.review

import com.barabasizsolt.review.api.ReviewService
import kotlinx.coroutines.flow.filterNotNull

class GetReviewsFlowUseCase(private val reviewService: ReviewService) {

    operator fun invoke() = reviewService.reviews.filterNotNull()
}