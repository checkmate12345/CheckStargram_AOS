package com.checkmate.checkstagram.domain.model

data class FeedInfo(
    val username: String,
    val profileImage : String,
    val medias : List<MediaInfo>,
    val description : String,
    val likes : Int,
    val comments : Int,
    val createdAt : String,
    val updatedAt : String
)
