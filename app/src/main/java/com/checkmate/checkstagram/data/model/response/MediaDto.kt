package com.checkmate.checkstagram.data.model.response

import com.checkmate.checkstagram.domain.model.MediaInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediaDto(
    @Json(name = "mediaUrl") val mediaUrl : String,
    @Json(name = "mediaType") val mediaType : String,
) {
    fun toDomain() : MediaInfo =
        MediaInfo(
            mediaUrl,
            mediaType
        )
}
