package com.checkmate.checkstagram.domain.model

data class FeedInfo(
    val username: String,
    val profileImage : String,
    val medias : List<MediaInfo>,
    val description : String? = "",
    val likes : String? = "0",
    val comments : String? = "0",
    val createdAt : String,
    val updatedAt : String
)
