package com.barabasizsolt.mova.domain.usecase.helper.genre

import com.barabasizsolt.mova.domain.util.wrapToResult
import com.barabasizsolt.mova.genre.api.GenreService

class GetGenresUseCase(private val genreService: GenreService) {

    suspend operator fun invoke() = wrapToResult {
        genreService.getGenres()
    }
}