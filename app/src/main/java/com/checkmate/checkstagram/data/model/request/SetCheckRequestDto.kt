package com.checkmate.checkstagram.data.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SetCheckRequestDto(
    @Json(name = "risk") val risk: List<String>? = null,
    @Json(name = "bag") val bag: List<String>? = null,
    @Json(name = "phone") val phone: List<String>? = null,
    @Json(name = "coke") val coke: List<String>? = null
)
