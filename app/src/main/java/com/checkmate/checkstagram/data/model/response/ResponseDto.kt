package com.checkmate.checkstagram.data.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseResultDto(
    @Json(name="success") val success: Boolean,
    @Json(name="message") val message: String,
)

@JsonClass(generateAdapter = true)
data class ResponseDto<T>(
    @Json(name="success") val success: Boolean,
    @Json(name="message") val message: String = "",
    @Json(name="data") val data: T?
)

@JsonClass(generateAdapter = true)
data class ResponseListDto<T>(
    @Json(name="success") val success: Boolean,
    @Json(name="message") val message: String = "",
    @Json(name="data") val data: List<T>
)