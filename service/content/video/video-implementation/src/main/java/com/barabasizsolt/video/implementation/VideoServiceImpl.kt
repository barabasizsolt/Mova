package com.barabasizsolt.video.implementation

import com.barabasizsolt.category.Category
import com.barabasizsolt.video.api.VideoService
import com.barabasizsolt.video.model.Video

class VideoServiceImpl(private val remoteSource: VideoRemoteSource) : VideoService {

    override suspend fun getVideos(id: Int, category: Category): List<Video> = when (category) {
        Category.MOVIE -> remoteSource.getVideosForMovie(id = id)
        Category.TV -> remoteSource.getVideosForTv(id = id)
    }
}