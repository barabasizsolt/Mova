package com.barabasizsolt.discover.implementation

import com.barabasizsolt.discover.implementation.model.response.MovieDiscoverResponse
import com.barabasizsolt.discover.implementation.model.response.TvSeriesDiscoverResponse
import retrofit2.http.GET

interface DiscoverNetworkService {

    @GET("discover/movie")
    suspend fun getMovies() : MovieDiscoverResponse

    @GET("discover/tv")
    suspend fun getTvSeries() : TvSeriesDiscoverResponse
}