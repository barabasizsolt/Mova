package com.barabasizsolt.review.implementation

import com.barabasizsolt.review.dto.ReviewListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewNetworkService {

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviewForMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ) : ReviewListDTO

    @GET("tv/{tv_id}/reviews")
    suspend fun getReviewsForTv(
        @Path("tv_id") tvId: Int,
        @Query("page") page: Int
    ) : ReviewListDTO
}