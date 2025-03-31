package com.checkmate.checkstagram.domain.repository

import android.content.Context
import com.checkmate.checkstagram.domain.model.MediaInfo

interface MediaRepository {
    suspend fun getMediaList(context: Context): List<MediaInfo>
}