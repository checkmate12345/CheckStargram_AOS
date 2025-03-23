package com.checkmate.checkstagram.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaInfo(
    val mediaUrl: String,
    val mediaType: String
) : Parcelable
