package com.barabasizsolt.domain.usecase.helper.review

import com.barabasizsolt.category.Category
import com.barabasizsolt.domain.util.result.wrapToResult
import com.barabasizsolt.pagination.RefreshType
import com.barabasizsolt.review.api.ReviewService

class GetReviewsUseCase(private val reviewService: ReviewService) {

    suspend operator fun invoke(id: Int, category: Category, refreshType: RefreshType) = wrapToResult {
        reviewService.getReviews(id = id, category = category, refreshType = refreshType)
    }
}