package com.checkmate.checkstagram.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.checkmate.checkstagram.domain.model.FeedInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(

): ViewModel() {
    private val _feedInfo = MutableStateFlow<List<FeedInfo>>(emptyList())
    val feedInfo : StateFlow<List<FeedInfo>> get() = _feedInfo

    init {
        getFeedInfo()
    }

    private fun getFeedInfo() {
        viewModelScope.launch {
            val tempList = listOf( FeedInfo(
                username = "for_everyoung10",
                profileImage = "",
                mediaType = emptyList(),
                description = "",
                likes = 1234,
                comments = 4321,
                createdAt = "2025년 10월 12일",
                updatedAt = ""
            ),
                FeedInfo(
                    username = "for_everyoung10",
                    profileImage = "",
                    mediaType = emptyList(),
                    description = "",
                    likes = 1234,
                    comments = 4321,
                    createdAt = "2025년 10월 12일",
                    updatedAt = ""
                ),
                FeedInfo(
                    username = "for_everyoung10",
                    profileImage = "",
                    mediaType = emptyList(),
                    description = "",
                    likes = 1234,
                    comments = 4321,
                    createdAt = "2025년 10월 12일",
                    updatedAt = ""
                )
            )

            _feedInfo.value = tempList
        }
    }
}