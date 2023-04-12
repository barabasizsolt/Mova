package com.barabasizsolt.video.implementation

import com.barabasizsolt.video.dto.toModel

class VideoRemoteSource(private val networkService: VideoNetworkService) {

    suspend fun getVideosForMovie(id: Int) = networkService.getVideosForMovies(movieId = id).toModel()

    suspend fun getVideosForTv(id: Int) = networkService.getVideosForTv(tvId = id).toModel()
}