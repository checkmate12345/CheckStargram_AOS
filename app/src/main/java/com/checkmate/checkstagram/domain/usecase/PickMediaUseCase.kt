package com.checkmate.checkstagram.domain.usecase

import android.content.Context
import com.checkmate.checkstagram.data.respository.MediaRepository
import com.checkmate.checkstagram.domain.model.MediaInfo
import javax.inject.Inject

class PickMediaUseCase @Inject constructor(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(context: Context): List<MediaInfo> {
        return mediaRepository.getMediaList(context)
    }
}
