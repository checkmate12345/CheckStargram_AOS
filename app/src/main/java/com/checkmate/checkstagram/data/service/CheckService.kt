package com.checkmate.checkstagram.data.service

import com.checkmate.checkstagram.data.model.request.PostFeedRequestDto
import com.checkmate.checkstagram.data.model.request.LoginRequestDto
import com.checkmate.checkstagram.data.model.request.SetCheckRequestDto
import com.checkmate.checkstagram.data.model.response.CheckResponseDto
import com.checkmate.checkstagram.data.model.response.FeedResponseDto
import com.checkmate.checkstagram.data.model.response.LoginResponseDto
import com.checkmate.checkstagram.data.model.response.PostFeedResponseDto
import com.checkmate.checkstagram.data.model.response.ResponseDto
import com.checkmate.checkstagram.data.model.response.ResponseListDto
import com.checkmate.checkstagram.data.model.response.ResponseResultDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface CheckService {
    @POST("/auth/login")
    suspend fun login(
        @Body loginRequestDto: LoginRequestDto
    ): ResponseDto<LoginResponseDto>

    @GET("feeds")
    suspend fun getFeeds(): ResponseListDto<FeedResponseDto>

    @Multipart
    @POST("feeds/{username}")
    suspend fun postFeed(
        @Path("username") username: String,
        @Part medias: List<MultipartBody.Part>,
        @Part("mediasMeta") mediasMeta: RequestBody,
        @Part("description") description: RequestBody?
    ): ResponseResultDto

    @POST("feeds/check/{username}")
    suspend fun checkFeed(
        @Path("username") username: String,
        @Body postFeedRequestDto: PostFeedRequestDto
    ): ResponseListDto<String> // todo 내려 주는 데이터 확정 되면 수정

    @POST("setcheck/{username}")
    suspend fun setCheckSetting(
        @Path("username") username: String,
        @Body setCheckRequestDto: SetCheckRequestDto
    ): ResponseResultDto

    @GET("setcheck/{username}")
    suspend fun getCheckSetting(
        @Path("username") username: String,
    ): ResponseDto<CheckResponseDto>
}