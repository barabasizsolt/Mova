package com.barabasizsolt.video.implementation

import com.barabasizsolt.video.dto.VideoListDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface VideoNetworkService {

    @GET("movie/{movie_id}/videos")
    suspend fun getVideosForMovies(@Path("movie_id") movieId: Int) : VideoListDTO

    @GET("tv/{tv_id}/videos")
    suspend fun getVideosForTv(@Path("tv_id") tvId: Int) : VideoListDTO
}