package com.barabasizsolt.mova.video.implementation

import category.Category
import com.barabasizsolt.mova.video.api.VideoService
import video.model.Video

class VideoServiceImpl(private val remoteSource: VideoRemoteSource) : VideoService {

    override suspend fun getVideos(id: Int, category: Category): List<Video> = when (category) {
        Category.MOVIE -> remoteSource.getVideosForMovie(id = id)
        Category.TV -> remoteSource.getVideosForTv(id = id)
    }
}