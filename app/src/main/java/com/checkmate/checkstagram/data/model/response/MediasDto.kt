package com.checkmate.checkstagram.data.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediasDto(
    @Json(name = "mediaUrl") val mediaUrl : String,
    @Json(name = "mediaType") val mediaType : String,
)
