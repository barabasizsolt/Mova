package com.barabasizsolt.domain.usecase.screen.explore

import com.barabasizsolt.domain.usecase.helper.discover.movie.GetMovieDiscoverUseCase
import com.barabasizsolt.domain.usecase.helper.discover.tv.GetTvDiscoverUseCase
import com.barabasizsolt.util.RefreshType

class GetExploreScreenUseCase(
    private val getMovieDiscoverUseCase: GetMovieDiscoverUseCase,
    private val getTvDiscoverUseCase: GetTvDiscoverUseCase,
) {

    suspend operator fun invoke(
        category: Category = Category.MOVIE,
        query: String,
        refreshType: RefreshType
    ) = when (category) {
        Category.MOVIE -> getMovieDiscoverUseCase(query = query, refreshType = refreshType)
        Category.TV -> getTvDiscoverUseCase(query = query, refreshType = refreshType)
    }
}