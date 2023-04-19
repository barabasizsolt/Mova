package com.barabasizsolt.mova.domain.usecase.helper.video

import category.Category
import com.barabasizsolt.mova.domain.util.wrapToResult
import com.barabasizsolt.mova.video.api.VideoService

class GetVideosUseCase(private val videoService: VideoService) {

    suspend operator fun invoke(id: Int, category: Category) = wrapToResult {
        videoService.getVideos(id = id, category = category)
    }
}