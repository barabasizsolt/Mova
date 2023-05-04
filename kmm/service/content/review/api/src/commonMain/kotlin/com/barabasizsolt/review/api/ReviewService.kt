package com.barabasizsolt.review.api

import category.Category
import com.barabasizsolt.mova.pager.PagerItem
import com.barabasizsolt.mova.pager.RefreshType
import kotlinx.coroutines.flow.Flow

interface ReviewService {

    val reviews: Flow<List<PagerItem>>

    suspend fun getReviews(id: Int, category: Category, refreshType: RefreshType): List<PagerItem>

    fun clearReviews()
}