package com.barabasizsolt.domain.usecase.helper.genre

import com.barabasizsolt.genre.api.GenreService
import kotlinx.coroutines.flow.filterNotNull

class GetGenresFlowUseCase(private val genreService: GenreService) {

    operator fun invoke() = genreService.genres.filterNotNull()
}