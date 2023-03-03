package com.barabasizsolt.domain.usecase.helper.genre

import com.barabasizsolt.genre.api.GenreService
import com.barabasizsolt.genre.api.GenreType
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.merge

class GetGenresFlowUseCase(private val genreService: GenreService) {

    operator fun invoke(genreType: GenreType = GenreType.JOINT) = when (genreType) {
        GenreType.MOVIE -> genreService.movieGenres.filterNotNull()
        GenreType.TV -> genreService.tvGenres.filterNotNull()
        GenreType.JOINT -> merge(genreService.movieGenres, genreService.tvGenres).filterNotNull()
    }
}