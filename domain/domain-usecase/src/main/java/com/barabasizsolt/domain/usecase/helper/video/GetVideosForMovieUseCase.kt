package com.barabasizsolt.domain.usecase.helper.video

import com.barabasizsolt.domain.util.result.wrapToResult
import com.barabasizsolt.video.api.VideoService

class GetVideosForMovieUseCase(private val videoService: VideoService) {

    suspend operator fun invoke(id: Int) = wrapToResult {
        videoService.getVideosForMovie(id = id)
    }
}