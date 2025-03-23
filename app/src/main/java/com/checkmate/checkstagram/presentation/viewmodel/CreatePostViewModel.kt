package com.checkmate.checkstagram.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.checkmate.checkstagram.data.model.request.FeedCheckResponseDto
import com.checkmate.checkstagram.data.model.response.CheckResponseDto
import com.checkmate.checkstagram.domain.model.MediaInfo
import com.checkmate.checkstagram.domain.usecase.CheckFeedUseCase
import com.checkmate.checkstagram.domain.usecase.GetCheckSettingUseCase
import com.checkmate.checkstagram.presentation.model.CensuredSentence
import com.checkmate.checkstagram.presentation.model.CheckMediaResult
import com.checkmate.checkstagram.presentation.model.CheckResultResponse
import com.checkmate.checkstagram.presentation.model.Timeline
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val checkFeedUseCase: CheckFeedUseCase,
    private val getCheckSettingUseCase: GetCheckSettingUseCase
) : ViewModel() {

    val description = MutableStateFlow("")
    val isCheckAI = MutableStateFlow(false)

    private val _selectedMediaList = MutableStateFlow<List<MediaInfo>>(emptyList())
    val selectedList: StateFlow<List<MediaInfo>> get() = _selectedMediaList

    private val _checkSetting = MutableStateFlow<CheckResponseDto?>(null)
    val checkSetting: StateFlow<CheckResponseDto?> get() = _checkSetting

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        initSetting()
    }

    fun initSelectedMediaList(list: List<MediaInfo>) {
        viewModelScope.launch {
            _selectedMediaList.emit(list)
        }
    }

    private fun initSetting() {
        viewModelScope.launch {
            val result = getCheckSettingUseCase("wonyoung")
            if (result.isSuccess) {
                _checkSetting.emit(result.getOrThrow())
            } else {
                Log.d("jomi", "getCheckSetting fail : ${result.exceptionOrNull()}")
            }
        }
    }

    fun checkFeed(
        onSuccess: (CheckResultResponse) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.emit(true) // ✅ 로딩 시작

            val testMediaInfos = selectedList.value
            val des = description.value
            val categoriesMap = checkSetting.value
            val categoriesJson = categoriesMap?.toJsonString()

            val result = categoriesJson?.let {
                checkFeedUseCase(
                    mediaInfos = testMediaInfos,
                    description = des,
                    categoriesJson = it
                )
            }

            _isLoading.emit(false) // ✅ 로딩 종료

            result?.onSuccess {
                Log.d("FeedCheck ✅", "성공: ${it.message}")
                val checkResult = it.toParcelModel()  // ✅ 여기서 변환
                onSuccess(checkResult)                // ✅ 결과 전달
            }?.onFailure { e ->
                Log.e("FeedCheck ❌", "실패: ${e.message}")
                onFailure(e)
            }
        }
    }


    private fun CheckResponseDto.toJsonString(): String {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(CheckResponseDto::class.java)
        return adapter.toJson(this)
    }

    private fun FeedCheckResponseDto.toParcelModel(): CheckResultResponse {
        return CheckResultResponse(
            message = message,
            censuredSentences = censuredSentences.map {
                CensuredSentence(it.text, it.censured)
            },
            results = results.map { dto ->
                CheckMediaResult(
                    resultUrl = dto.resultUrl,
                    mediaType = dto.mediaType,
                    resultObject = dto.resultObject,
                    timeline = dto.timeline.mapValues { entry ->
                        entry.value.map { Timeline(it.start, it.end) }
                    }
                )
            }
        )
    }

}
