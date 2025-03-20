package com.checkmate.checkstagram.data.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CheckResponseDto(
    @Json(name = "risk") val risk : List<String>,
    @Json(name = "brand") val brand : List<String>,
    @Json(name = "it") val it : List<String>,
    @Json(name = "foodDrink") val foodDrink : List<String>,
)
