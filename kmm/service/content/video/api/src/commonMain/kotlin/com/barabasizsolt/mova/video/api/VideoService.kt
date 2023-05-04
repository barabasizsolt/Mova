package com.barabasizsolt.mova.video.api

import category.Category
import video.model.Video

interface VideoService {

    suspend fun getVideos(id: Int, category: Category): List<Video>
}