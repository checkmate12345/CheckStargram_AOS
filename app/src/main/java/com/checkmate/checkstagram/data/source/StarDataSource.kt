package com.checkmate.checkstagram.data.source

import com.checkmate.checkstagram.data.model.request.FeedCheckResponseDto
import com.checkmate.checkstagram.data.service.StarService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class StarDataSource @Inject constructor(
    private val service : StarService
) {
    suspend fun checkFeed(
        medias: List<MultipartBody.Part>,
        mediasMeta: String,
        categories: String,
        description: String?
    ): Result<FeedCheckResponseDto> {
        return try {
            val mediasMetaPart = mediasMeta.toRequestBody("application/json".toMediaType())
            val categoriesPart = categories.toRequestBody("application/json".toMediaType())
            val descriptionPart = description?.toRequestBody("text/plain".toMediaType())

            val response = service.checkFeed(medias, mediasMetaPart, categoriesPart, descriptionPart)
            if (response.success) Result.success(response)
            else Result.failure(Exception(response.message))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}