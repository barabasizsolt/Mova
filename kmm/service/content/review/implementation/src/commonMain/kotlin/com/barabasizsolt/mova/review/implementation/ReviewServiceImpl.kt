package com.barabasizsolt.mova.review.implementation

import category.Category
import com.barabasizsolt.mova.pager.Pager
import com.barabasizsolt.mova.pager.PagerItem
import com.barabasizsolt.mova.pager.RefreshType
import com.barabasizsolt.review.api.ReviewService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ReviewServiceImpl(
    private val remoteSource: ReviewRemoteSource
) : ReviewService {

    private val _reviews = MutableStateFlow<List<PagerItem>>(value = emptyList())
    override val reviews: Flow<List<PagerItem>> = _reviews

    private val pager: Pager = Pager()

    override suspend fun getReviews(id: Int, category: Category, refreshType: RefreshType): List<PagerItem> = pager.paginate(
        refreshType = refreshType,
        flow = _reviews,
        getRemoteContent = { page ->
            when (category) {
                Category.MOVIE -> remoteSource.getReviewsForMovie(id = id, page = page)
                Category.TV -> remoteSource.getReviewsForTv(id = id, page = page)
            }
        },
        cacheWithError = false
    )

    override fun clearReviews() {
        _reviews.value = emptyList()
    }
}