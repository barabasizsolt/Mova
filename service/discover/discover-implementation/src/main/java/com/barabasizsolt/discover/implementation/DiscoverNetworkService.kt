package com.barabasizsolt.discover.implementation

import com.barabasizsolt.movie.dto.MovieDiscoverResponse
import com.barabasizsolt.tv.dto.TvSeriesDiscoverResponse
import retrofit2.http.GET

interface DiscoverNetworkService {

    @GET("discover/movie")
    suspend fun getMovies() : MovieDiscoverResponse

    @GET("discover/tv")
    suspend fun getTvSeries() : TvSeriesDiscoverResponse
}