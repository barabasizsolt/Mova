package com.barabasizsolt.mova.domain.usecase.helper.review

import category.Category
import com.barabasizsolt.mova.domain.util.wrapToResult
import com.barabasizsolt.mova.pager.RefreshType
import com.barabasizsolt.review.api.ReviewService

class GetReviewsUseCase(private val reviewService: ReviewService) {

    suspend operator fun invoke(id: Int, category: Category, refreshType: RefreshType) = wrapToResult {
        reviewService.getReviews(id = id, category = category, refreshType = refreshType)
    }
}