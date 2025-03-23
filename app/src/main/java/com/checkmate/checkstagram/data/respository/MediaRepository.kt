package com.checkmate.checkstagram.data.respository

import android.content.Context
import android.net.Uri
import com.checkmate.checkstagram.domain.model.MediaInfo

interface MediaRepository {
    suspend fun getMediaList(context: Context): List<MediaInfo>
}