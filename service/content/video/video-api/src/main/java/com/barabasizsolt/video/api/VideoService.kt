package com.barabasizsolt.video.api

import com.barabasizsolt.category.Category
import com.barabasizsolt.video.model.Video

interface VideoService {

    suspend fun getVideos(id: Int, category: Category): List<Video>
}