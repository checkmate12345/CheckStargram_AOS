package com.checkmate.checkstagram.data.source

import com.checkmate.checkstagram.data.model.request.LoginRequestDto
import com.checkmate.checkstagram.data.model.response.FeedResponseDto
import com.checkmate.checkstagram.data.model.response.LoginResponseDto
import com.checkmate.checkstagram.data.model.response.ResponseDto
import com.checkmate.checkstagram.data.model.response.ResponseListDto
import com.checkmate.checkstagram.data.model.response.ResponseResultDto
import com.checkmate.checkstagram.data.service.CheckService
import com.checkmate.checkstagram.domain.model.FeedInfo
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class CheckDataSource @Inject constructor(
    private val service : CheckService
) {
    suspend fun login(
        username: String,
        password: String
    ): Result<ResponseDto<LoginResponseDto>> {
        return try {
            val response = service.login(LoginRequestDto(username, password))
            if (response.success && response.data != null) {
                Result.success(response)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFeeds(): Result<List<FeedInfo>> {
        return try {
            val response = service.getFeeds()
            if (response.success) {
                Result.success(response.data.map { it.toDomain() })
            } else {
                Result.failure(Exception(response.message ?: "피드 로딩 실패"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun postFeed(
        username: String,
        medias: List<MultipartBody.Part>,
        mediasMeta: String,
        description: String?
    ): Result<ResponseResultDto> {
        return try {
            val mediasMetaPart = mediasMeta.toRequestBody("application/json".toMediaType())
            val descriptionPart = description?.toRequestBody("text/plain".toMediaType())

            val response = service.postFeed(
                username = username,
                medias = medias,
                mediasMeta = mediasMetaPart,
                description = descriptionPart
            )
            if (response.success) {
                Result.success(response)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}