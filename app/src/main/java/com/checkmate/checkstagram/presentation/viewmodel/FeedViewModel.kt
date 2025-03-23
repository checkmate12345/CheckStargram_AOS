package com.checkmate.checkstagram.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.checkmate.checkstagram.domain.model.FeedInfo
import com.checkmate.checkstagram.domain.usecase.GetFeedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getFeedsUseCase: GetFeedsUseCase
): ViewModel() {

    private val _feedInfo = MutableStateFlow<List<FeedInfo>>(emptyList())
    val feedInfo : StateFlow<List<FeedInfo>> get() = _feedInfo

    init {
        initFeedInfo()
    }

    private fun initFeedInfo() {
        viewModelScope.launch {
            val result = getFeedsUseCase()
            if (result.isSuccess) {
                _feedInfo.emit(result.getOrThrow())
            } else {
                Log.d("JOMI", "피드 불러오기 실페 : ${result.exceptionOrNull()}")
            }
        }
    }
}