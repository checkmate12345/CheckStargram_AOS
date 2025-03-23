package com.checkmate.checkstagram.data.model.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CensuredSentenceDto(
    val text: String,
    val censured: Boolean
)

@JsonClass(generateAdapter = true)
data class TimelineDto(
    val start: Double,
    val end: Double
)

@JsonClass(generateAdapter = true)
data class CheckResultDto(
    val mediaType: String,
    val resultUrl: String,
    val resultObject: String,
    val timeline: Map<String, List<TimelineDto>> = emptyMap()
)

@JsonClass(generateAdapter = true)
data class FeedCheckResponseDto(
    val success: Boolean,
    val message: String,
    val description: String,
    val censuredSentences: List<CensuredSentenceDto>,
    val results: List<CheckResultDto>
)
