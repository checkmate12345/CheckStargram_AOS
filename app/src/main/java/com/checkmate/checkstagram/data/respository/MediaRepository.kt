package com.checkmate.checkstagram.data.respository

import android.content.Context
import android.net.Uri

interface MediaRepository {
    suspend fun getMediaList(context: Context): List<Uri>
}