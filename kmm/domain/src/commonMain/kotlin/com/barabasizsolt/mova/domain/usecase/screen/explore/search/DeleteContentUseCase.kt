package com.barabasizsolt.mova.domain.usecase.screen.explore.search

import category.Category
import com.barabasizsolt.mova.domain.usecase.helper.movie.search.DeleteSearchMovieUseCase
import com.barabasizsolt.mova.domain.usecase.helper.tvSeries.search.DeleteSearchTvSeriesUseCase

class DeleteContentUseCase(
    private val deleteSearchMovieUseCase: DeleteSearchMovieUseCase,
    private val deleteSearchTvSeriesUseCase: DeleteSearchTvSeriesUseCase
) {

    operator fun invoke(category: Category) = when (category) {
        Category.MOVIE -> deleteSearchMovieUseCase()
        Category.TV -> deleteSearchTvSeriesUseCase()
    }
}