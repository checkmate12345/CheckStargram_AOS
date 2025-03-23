package com.checkmate.checkstagram.data.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CheckResponseDto(
    @Json(name = "risk") val risk: List<String>,
    @Json(name = "bag")  val bag: List<String>,
    @Json(name = "phone") val phone: List<String>,
    @Json(name = "coke")val coke: List<String>
)
