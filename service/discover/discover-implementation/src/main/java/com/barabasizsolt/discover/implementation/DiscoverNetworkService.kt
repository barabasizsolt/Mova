package com.barabasizsolt.discover.implementation

import com.barabasizsolt.movie.dto.MovieListResponse
import com.barabasizsolt.tv.dto.TvSeriesDiscoverResponse
import retrofit2.http.GET

interface DiscoverNetworkService {

    @GET("discover/movie")
    suspend fun getMovies() : MovieListResponse

    @GET("discover/tv")
    suspend fun getTvSeries() : TvSeriesDiscoverResponse
}