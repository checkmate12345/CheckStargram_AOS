package com.checkmate.checkstagram.domain.usecase

import android.content.Context
import android.net.Uri
import com.checkmate.checkstagram.data.respository.MediaRepository
import javax.inject.Inject

class PickImageUseCase @Inject constructor(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(context: Context): List<Uri> {
        return mediaRepository.getMediaList(context)
    }
}