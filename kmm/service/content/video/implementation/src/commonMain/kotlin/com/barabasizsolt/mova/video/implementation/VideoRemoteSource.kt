package com.barabasizsolt.mova.video.implementation

import com.barabasizsolt.network.api.BaseHttpClient
import com.barabasizsolt.network.api.get
import video.dto.VideoListDTO
import video.dto.toModel

class VideoRemoteSource(private val baseHttpClient: BaseHttpClient) {

    suspend fun getVideosForMovie(id: Int) =
        baseHttpClient.get<VideoListDTO>(urlString = "movie/$id/videos").toModel()

    suspend fun getVideosForTv(id: Int) =
        baseHttpClient.get<VideoListDTO>(urlString = "tv/$id/videos").toModel()
}