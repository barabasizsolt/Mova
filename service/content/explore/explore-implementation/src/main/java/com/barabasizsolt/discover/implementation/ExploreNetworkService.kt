package com.barabasizsolt.discover.implementation

import com.barabasizsolt.movie.dto.MovieListDTO
import com.barabasizsolt.tv.dto.TvSeriesDiscoverDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ExploreNetworkService {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("region") region: List<String>,
        @Query("with_genres") withGenres: List<Int>,
        @Query("sort_by") sortBy: List<String>,
        @Query("page") page: Int
    ) : MovieListDTO

    @GET("discover/tv")
    suspend fun getTvSeries(
        @Query("region") region: List<String>,
        @Query("with_genres") withGenres: List<Int>,
        @Query("sort_by") sortBy: List<String>,
        @Query("page") page: Int
    ) : TvSeriesDiscoverDTO

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ) : MovieListDTO

    @GET("search/tv")
    suspend fun searchTvSeries(
        @Query("query") query: String,
        @Query("page") page: Int
    ) : TvSeriesDiscoverDTO
}