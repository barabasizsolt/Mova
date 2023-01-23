package com.barabasizsolt.domain.usecase.screen.explore.search

import com.barabasizsolt.domain.usecase.helper.movie.search.DeleteSearchMovieUseCase
import com.barabasizsolt.domain.usecase.helper.tvSeries.search.DeleteSearchTvSeriesUseCase
import com.barabasizsolt.domain.usecase.screen.explore.Category

class DeleteContentUseCase(
    private val deleteSearchMovieUseCase: DeleteSearchMovieUseCase,
    private val deleteSearchTvSeriesUseCase: DeleteSearchTvSeriesUseCase
) {

    operator fun invoke(category: Category) = when (category) {
        Category.MOVIE -> deleteSearchMovieUseCase()
        Category.TV -> deleteSearchTvSeriesUseCase()
    }
}