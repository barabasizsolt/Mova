package com.barabasizsolt.domain.usecase.helper.review

import com.barabasizsolt.review.api.ReviewService

class DeleteReviewsUseCase(private val reviewService: ReviewService) {

    operator fun invoke() = reviewService.clearReviews()
}