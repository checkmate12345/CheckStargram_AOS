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

}