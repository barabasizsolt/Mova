package com.barabasizsolt.discover.api

import com.barabasizsolt.discover.api.model.MovieDiscover
import com.barabasizsolt.discover.api.model.TvSeriesDiscover
import kotlinx.coroutines.flow.Flow

interface DiscoverService {

    val movies: Flow<MovieDiscover?>

    val tvSeries: Flow<TvSeriesDiscover?>

    suspend fun getMovies() : MovieDiscover

    suspend fun getTvSeries() : TvSeriesDiscover
}