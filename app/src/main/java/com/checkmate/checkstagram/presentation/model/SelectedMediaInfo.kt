package com.checkmate.checkstagram.presentation.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class SelectedMediaInfo(val uri: Uri) : Parcelable
