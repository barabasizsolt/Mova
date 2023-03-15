package com.barabasizsolt.video.api

import com.barabasizsolt.video.model.Video

interface VideoService {

    suspend fun getVideosForMovie(id: Int): List<Video>

    suspend fun getVideosForTV(id: Int): List<Video>
}