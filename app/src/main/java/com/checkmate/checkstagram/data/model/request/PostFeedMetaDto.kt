package com.checkmate.checkstagram.data.model.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostFeedMetaDto(
    val mediaType: String
)