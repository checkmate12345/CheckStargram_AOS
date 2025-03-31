package com.checkmate.checkstagram.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CheckResultResponse(
    val message: String,
    val censuredSentences: List<CensuredSentence>,
    val results: List<CheckMediaResult>
) : Parcelable

@Parcelize
data class CensuredSentence(
    val text: String,
    val censured: Boolean
) : Parcelable

@Parcelize
data class CheckMediaResult(
    val resultUrl: String,
    val mediaType: String,
    val resultObject: String,
    val timeline: Map<String, List<Timeline>> = emptyMap()
) : Parcelable

@Parcelize
data class Timeline(
    val start: Double,
    val end: Double
) : Parcelable

data class TimeLineLabel(
    val label: String,   // 위험 요소 이름 (예: "coke", "bag")
    val start: Double,
    val end: Double
)
