package com.checkmate.checkstagram.domain.usecase

import com.checkmate.checkstagram.domain.model.FeedInfo
import com.checkmate.checkstagram.domain.repository.CheckRepository
import javax.inject.Inject

class GetFeedsUseCase @Inject constructor(
    private val repository: CheckRepository
) {
    suspend operator fun invoke(): Result<List<FeedInfo>> {
        return repository.getFeeds()
    }
}