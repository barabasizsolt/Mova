package com.barabasizsolt.movie.implementation

import com.barabasizsolt.movie.dto.MovieListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieNetworkService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int) : MovieListDTO

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int) : MovieListDTO

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int) : MovieListDTO

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int) : MovieListDTO

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ) : MovieListDTO
}