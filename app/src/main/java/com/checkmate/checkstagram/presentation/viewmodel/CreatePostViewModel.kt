package com.checkmate.checkstagram.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.checkmate.checkstagram.domain.model.MediaInfo
import com.checkmate.checkstagram.domain.usecase.CheckFeedUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val checkFeedUseCase: CheckFeedUseCase
): ViewModel() {
    val description = MutableStateFlow("")

    val isCheckAI = MutableStateFlow(false)

    init {
        testCheckFeed()
    }

    fun testCheckFeed() {
        viewModelScope.launch {
            // ✅ 1. 테스트용 mediaInfos (Uri는 임시, 실제 앱에선 갤러리 선택 값 사용)
            val testMediaInfos = listOf(
                MediaInfo("content://media/external/video/media/1000000043", "video"),
                MediaInfo("content://media/external/images/media/1000000024", "img")
            )

            // ✅ 2. 테스트용 설명
            val description = "죽여버리고 싶다\n 오늘은 날씨가 좋다"

            // ✅ 3. 테스트용 카테고리 (예: 담배, 술, 브랜드)
            val categoriesMap = mapOf(
                "risk" to listOf("담배", "술"),
                "bag" to listOf("샤넬", "루이비통"),
                "phone" to listOf("애플"),
                "coke" to listOf("코카콜라")
            )

            // ✅ 4. JSON 변환
            val categoriesJson = categoriesMap.toJsonString()

            // ✅ 5. UseCase 호출
            val result = checkFeedUseCase(
                mediaInfos = testMediaInfos,
                description = description,
                categoriesJson = categoriesJson
            )

            result
                .onSuccess { response ->
                    Log.d("FeedCheck ✅", "성공: ${response.message}")
                    response.censuredSentences.forEach {
                        Log.d("검열문장", "${it.text} - ${it.censured}")
                    }
                    response.results.forEach {
                        Log.d("미디어", "${it.resultUrl} ▶️ ${it.resultObject}")
                    }
                }
                .onFailure {
                    Log.e("FeedCheck ❌", "실패: ${it.message}")
                }
        }
    }
    fun Map<String, List<String>>.toJsonString(): String {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val type = Types.newParameterizedType(Map::class.java, String::class.java, List::class.java)
        val adapter = moshi.adapter<Map<String, List<String>>>(type)
        return adapter.toJson(this)
    }
}