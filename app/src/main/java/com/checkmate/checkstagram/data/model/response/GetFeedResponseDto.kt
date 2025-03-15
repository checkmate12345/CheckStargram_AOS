package com.checkmate.checkstagram.data.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class GetFeedResponseDto(
    @Json(name = "username") val userName : String,
    @Json(name = "description") val description : String,
    @Json(name = "image") val image : List<String>,
    @Json(name = "createdAt") val createdAt : Date
)
