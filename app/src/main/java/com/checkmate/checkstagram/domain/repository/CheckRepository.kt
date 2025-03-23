package com.checkmate.checkstagram.domain.repository

import com.checkmate.checkstagram.data.model.request.SetCheckRequestDto
import com.checkmate.checkstagram.data.model.response.CheckResponseDto

import com.checkmate.checkstagram.domain.model.FeedInfo
import okhttp3.MultipartBody

interface CheckRepository {
    suspend fun login(
        username : String,
        password : String
    ): Result<String>

    suspend fun getFeeds(): Result<List<FeedInfo>>

    suspend fun postFeed(
        username: String,
        medias: List<MultipartBody.Part>,
        mediasMeta: String,
        description: String?
    ): Result<Unit>

    suspend fun setCheckSetting(username: String, dto: SetCheckRequestDto): Result<String>

    suspend fun getCheckSetting(username: String): Result<CheckResponseDto>
}