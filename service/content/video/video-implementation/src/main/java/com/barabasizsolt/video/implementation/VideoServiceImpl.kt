package com.barabasizsolt.video.implementation

import com.barabasizsolt.video.api.VideoService
import com.barabasizsolt.video.model.Video

class VideoServiceImpl(private val remoteSource: VideoRemoteSource) : VideoService {

    override suspend fun getVideosForMovie(id: Int): List<Video> = remoteSource.getVideosForMovie(id = id)

    override suspend fun getVideosForTV(id: Int): List<Video> = remoteSource.getVideosForTv(id = id)
}