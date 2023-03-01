package com.barabasizsolt.domain.usecase.helper.genre

import com.barabasizsolt.domain.util.result.wrapToResult
import com.barabasizsolt.genre.api.GenreService
import kotlinx.coroutines.CoroutineScope

class GetGenresUseCase(private val genreService: GenreService) {

    suspend operator fun invoke() = wrapToResult { genreService.getGenres() }
}