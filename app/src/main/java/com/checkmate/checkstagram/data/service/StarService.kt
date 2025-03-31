package com.checkmate.checkstagram.data.service

import com.checkmate.checkstagram.data.model.request.FeedCheckResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface StarService {
    @Multipart
    @POST("/predict")
    suspend fun checkFeed(
        @Part medias: List<MultipartBody.Part>,
        @Part("mediasMeta") mediasMeta: RequestBody,
        @Part("categories") categories: RequestBody,
        @Part("description") description: RequestBody?
    ): FeedCheckResponseDto
}