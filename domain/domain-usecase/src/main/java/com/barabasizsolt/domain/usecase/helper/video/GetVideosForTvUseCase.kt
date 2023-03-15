package com.barabasizsolt.domain.usecase.helper.video

import com.barabasizsolt.domain.util.result.wrapToResult
import com.barabasizsolt.video.api.VideoService

class GetVideosForTvUseCase(private val videoService: VideoService) {

    suspend operator fun invoke(id: Int) = wrapToResult {
        videoService.getVideosForTV(id = id)
    }
}