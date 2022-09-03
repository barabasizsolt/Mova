package com.barabasizsolt.discover.implementation

import com.barabasizsolt.movie.dto.MovieListResponse
import com.barabasizsolt.tv.dto.TvSeriesDiscoverResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverNetworkService {

    @GET("discover/movie")
    suspend fun getMovies() : MovieListResponse

    @GET("discover/tv")
    suspend fun getTvSeries() : TvSeriesDiscoverResponse

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String) : MovieListResponse

    @GET("search/tv")
    suspend fun searchTvSeries(@Query("query") query: String) : TvSeriesDiscoverResponse
}