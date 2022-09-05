package com.barabasizsolt.mova.ui.screen.main.explore

import cafe.adriel.voyager.core.model.ScreenModel
import com.barabasizsolt.domain.useCase.helper.discover.movie.GetMovieDiscoverUseCase
import com.barabasizsolt.domain.useCase.helper.discover.tv.GetTvDiscoverUseCase

class FilterScreenModel(
    private val getMovieDiscoverUseCase: GetMovieDiscoverUseCase,
    private val getTvDiscoverUseCase: GetTvDiscoverUseCase,
) : ScreenModel {

}