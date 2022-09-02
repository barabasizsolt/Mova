package com.barabasizsolt.domain.useCase.screen.explore

import com.barabasizsolt.domain.useCase.helper.discover.movie.GetMovieDiscoverUseCase
import com.barabasizsolt.domain.useCase.helper.discover.tv.GetTvDiscoverUseCase
import com.barabasizsolt.domain.util.wrapToResult

class GetExploreScreenUseCase(
    private val getMovieDiscoverUseCase: GetMovieDiscoverUseCase,
    private val getTvDiscoverUseCase: GetTvDiscoverUseCase,
) {

    suspend operator fun invoke(category: Category = Category.MOVIE) = wrapToResult {
        when (category) {
            Category.MOVIE -> getMovieDiscoverUseCase()
            Category.TV -> getTvDiscoverUseCase()
        }
    }
}