package com.checkmate.checkstagram.domain.usecase

import com.checkmate.checkstagram.domain.repository.CheckRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class PostFeedUseCase @Inject constructor(
    private val repository: CheckRepository
) {
    suspend operator fun invoke(
        username: String,
        medias: List<MultipartBody.Part>,
        mediasMeta: String,
        description: String?
    ): Result<Unit> {
        return repository.postFeed(username, medias, mediasMeta, description)
    }
}