package com.checkmate.checkstagram.data.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class PostFeedResponseDto(
    @Json(name = "username") val username : String,
    @Json(name = "profileImage") val profileImage : String,
    @Json(name = "medias") val medias: List<MediasDto>,
    @Json(name = "description") val description: String,
    @Json(name = "likes") val likes : Int = 0,
    @Json(name = "comments") val comments : Int = 0,
    @Json(name = "createdAt") val createdAt : Date,
    @Json(name = "updatedAt") val updatedAt : Date
)
