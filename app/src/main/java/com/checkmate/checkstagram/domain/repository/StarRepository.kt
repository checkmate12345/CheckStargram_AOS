package com.checkmate.checkstagram.domain.repository

import com.checkmate.checkstagram.data.model.request.FeedCheckResponseDto
import com.checkmate.checkstagram.domain.model.MediaInfo

interface StarRepository {
    suspend fun checkFeed(
        mediaInfos: List<MediaInfo>,
        description: String?,
        categoriesJson: String
    ): Result<FeedCheckResponseDto>

}