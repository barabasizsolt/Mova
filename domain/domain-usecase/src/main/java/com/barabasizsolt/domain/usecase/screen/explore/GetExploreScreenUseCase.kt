package com.barabasizsolt.domain.usecase.screen.explore

import com.barabasizsolt.domain.usecase.helper.discover.movie.GetMovieDiscoverUseCase
import com.barabasizsolt.domain.usecase.helper.discover.tv.GetTvDiscoverUseCase
import com.barabasizsolt.domain.usecase.screen.explore.Category
import com.barabasizsolt.domain.util.wrapToResult

class GetExploreScreenUseCase(
    private val getMovieDiscoverUseCase: GetMovieDiscoverUseCase,
    private val getTvDiscoverUseCase: GetTvDiscoverUseCase,
) {

    suspend operator fun invoke(
        category: Category = Category.MOVIE,
        query: String
    ) = wrapToResult {
        when (category) {
            Category.MOVIE -> getMovieDiscoverUseCase(query = query)
            Category.TV -> getTvDiscoverUseCase(query = query)
        }
    }
}