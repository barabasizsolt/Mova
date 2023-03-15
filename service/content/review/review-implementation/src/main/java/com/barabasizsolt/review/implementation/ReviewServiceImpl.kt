package com.barabasizsolt.review.implementation

import com.barabasizsolt.category.Category
import com.barabasizsolt.pagination.api.Pager
import com.barabasizsolt.pagination.api.PagerItem
import com.barabasizsolt.pagination.api.RefreshType
import com.barabasizsolt.review.api.ReviewService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ReviewServiceImpl(
    private val remoteSource: ReviewRemoteSource,
    private val pager: Pager
) : ReviewService {

    private val _reviews = MutableStateFlow<List<PagerItem>>(value = emptyList())
    override val reviews: Flow<List<PagerItem>> = _reviews

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