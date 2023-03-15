package com.barabasizsolt.review.api

import com.barabasizsolt.category.Category
import com.barabasizsolt.pagination.api.PagerItem
import com.barabasizsolt.pagination.api.RefreshType
import kotlinx.coroutines.flow.Flow

interface ReviewService {

    val reviews: Flow<List<PagerItem>>

    suspend fun getReviews(id: Int, category: Category, refreshType: RefreshType): List<PagerItem>

    fun clearReviews()
}