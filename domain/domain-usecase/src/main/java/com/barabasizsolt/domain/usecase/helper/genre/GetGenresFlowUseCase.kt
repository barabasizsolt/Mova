package com.barabasizsolt.domain.usecase.helper.genre

import com.barabasizsolt.genre.api.GenreService
import com.barabasizsolt.genre.api.GenreType
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull

class GetGenresFlowUseCase(private val genreService: GenreService) {

    operator fun invoke(genreType: GenreType = GenreType.JOINT) = when (genreType) {
        GenreType.MOVIE -> genreService.movieGenres.filterNotNull()
        GenreType.TV -> genreService.tvGenres.filterNotNull()
        GenreType.JOINT -> genreService.movieGenres.combine(genreService.tvGenres) { movieGenres, tvGenres ->
            movieGenres + tvGenres
        }
    }
}