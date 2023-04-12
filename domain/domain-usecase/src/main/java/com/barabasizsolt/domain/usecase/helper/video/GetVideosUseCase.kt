package com.barabasizsolt.domain.usecase.helper.video

import com.barabasizsolt.category.Category
import com.barabasizsolt.domain.util.result.wrapToResult
import com.barabasizsolt.video.api.VideoService

class GetVideosUseCase(private val videoService: VideoService) {

    suspend operator fun invoke(id: Int, category: Category) = wrapToResult {
        videoService.getVideos(id = id, category = category)
    }
}