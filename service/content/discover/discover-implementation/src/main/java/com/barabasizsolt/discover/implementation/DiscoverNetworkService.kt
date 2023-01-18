package com.barabasizsolt.discover.implementation

import com.barabasizsolt.movie.dto.MovieListResponse
import com.barabasizsolt.tv.dto.TvSeriesDiscoverResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverNetworkService {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("region") region: List<String>,
        @Query("with_genres") withGenres: List<Int>,
        @Query("sort_by") sortBy: List<String>,
        @Query("page") page: Int
    ) : MovieListResponse

    @GET("discover/tv")
    suspend fun getTvSeries(
        @Query("region") region: List<String>,
        @Query("with_genres") withGenres: List<Int>,
        @Query("sort_by") sortBy: List<String>,
        @Query("page") page: Int
    ) : TvSeriesDiscoverResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ) : MovieListResponse

    @GET("search/tv")
    suspend fun searchTvSeries(
        @Query("query") query: String,
        @Query("page") page: Int
    ) : TvSeriesDiscoverResponse
}