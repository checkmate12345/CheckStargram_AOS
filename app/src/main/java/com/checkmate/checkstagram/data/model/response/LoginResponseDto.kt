package com.checkmate.checkstagram.data.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponseDto(
    val username : String
)
