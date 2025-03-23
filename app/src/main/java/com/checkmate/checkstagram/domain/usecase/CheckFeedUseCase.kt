package com.checkmate.checkstagram.domain.usecase

import com.checkmate.checkstagram.data.model.request.FeedCheckResponseDto
import com.checkmate.checkstagram.domain.model.MediaInfo
import com.checkmate.checkstagram.domain.repository.StarRepository
import javax.inject.Inject

class CheckFeedUseCase @Inject constructor(
    private val repository: StarRepository
) {
    suspend operator fun invoke(
        mediaInfos: List<MediaInfo>,
        description: String?,
        categoriesJson: String
    ): Result<FeedCheckResponseDto> {
        return repository.checkFeed(
            mediaInfos = mediaInfos,
            description = description,
            categoriesJson = categoriesJson
        )
    }
}