package com.checkmate.checkstagram.domain.repositoryImpl

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.checkmate.checkstagram.data.respository.MediaRepository
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(): MediaRepository {

    override suspend fun getMediaList(context: Context): List<Uri> {
        val mediaList = mutableListOf<Uri>()

        val projection = arrayOf(
            MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns.MIME_TYPE
        )

        val sortOrder = "${MediaStore.MediaColumns.DATE_ADDED} DESC" // 최신 순 정렬

        val imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        // 이미지 & 동영상 가져오기
        context.contentResolver.query(imageUri, projection, null, null, sortOrder)?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val uri = ContentUris.withAppendedId(imageUri, id)
                mediaList.add(uri)
            }
        }

        context.contentResolver.query(videoUri, projection, null, null, sortOrder)?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val uri = ContentUris.withAppendedId(videoUri, id)
                mediaList.add(uri)
            }
        }

        return mediaList
    }
}