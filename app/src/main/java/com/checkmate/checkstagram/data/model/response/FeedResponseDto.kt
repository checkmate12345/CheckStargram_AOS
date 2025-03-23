package com.checkmate.checkstagram.data.model.response

import com.checkmate.checkstagram.domain.model.FeedInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class FeedResponseDto(
    @Json(name = "username") val username: String,
    @Json(name = "profileImage") val profileImage: String,
    @Json(name = "medias") val medias: List<MediaDto>,
    @Json(name = "description") val description: String?,
    @Json(name = "likes") val likes: String?,
    @Json(name = "comments") val comments: String?,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "updatedAt") val updatedAt: String
) {
    fun toDomain(): FeedInfo =
        FeedInfo(
            username,
            profileImage,
            medias.map {
                it.toDomain()
            },
            description,
            likes,
            comments,
            createdAt,
            updatedAt
        )
}
