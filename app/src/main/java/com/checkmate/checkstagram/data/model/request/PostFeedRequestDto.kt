package com.checkmate.checkstagram.data.model.request

import com.checkmate.checkstagram.data.model.response.MediaDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostFeedRequestDto(
    @Json(name = "medias") val image : List<MediaDto>,
    @Json(name = "description") val description : String
)
