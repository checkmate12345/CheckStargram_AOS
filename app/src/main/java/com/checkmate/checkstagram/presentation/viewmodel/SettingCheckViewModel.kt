package com.checkmate.checkstagram.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.checkmate.checkstagram.data.model.request.SetCheckRequestDto
import com.checkmate.checkstagram.domain.usecase.GetCheckSettingUseCase
import com.checkmate.checkstagram.domain.usecase.SetCheckSettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingCheckViewModel @Inject constructor(
    private val setCheckSettingUseCase: SetCheckSettingUseCase,
) : ViewModel() {

    private val _selectedRisk = MutableStateFlow<List<String>>(emptyList())
    val selectedRisk: StateFlow<List<String>> = _selectedRisk

    private val _selectedBag = MutableStateFlow<List<String>>(emptyList())
    val selectedBag: StateFlow<List<String>> = _selectedBag

    private val _selectedPhone = MutableStateFlow<List<String>>(emptyList())
    val selectedPhone: StateFlow<List<String>> = _selectedPhone

    private val _selectedCoke = MutableStateFlow<List<String>>(emptyList())
    val selectedCoke: StateFlow<List<String>> = _selectedCoke

    init {
        getCheckSetting()
    }

    private fun getCheckSetting() {
        viewModelScope.launch {
            _selectedRisk.emit(emptyList())
            _selectedBag.emit(emptyList())
            _selectedPhone.emit(emptyList())
            _selectedCoke.emit(emptyList())
        }
    }

    fun saveCheckSetting() {
        viewModelScope.launch {
            val request = SetCheckRequestDto(
                selectedRisk.value,
                selectedBag.value,
                selectedPhone.value,
                selectedCoke.value,
            )
            Log.d("jomi", "seleted : $request")
            val result = setCheckSettingUseCase("wonyoung", request)
            if (result.isSuccess) {
                Log.d("Jomi", "✅ 저장 성공: ${result.getOrThrow()}")
            } else {
                Log.e("Jomi", "❌ 저장 실패: ${result.exceptionOrNull()}")
            }
        }
    }


    fun toggleItem(category: String, item: String) {
        when (category) {
            "risk" -> _selectedRisk.value = toggleList(_selectedRisk.value, item)
            "bag" -> _selectedBag.value = toggleList(_selectedBag.value, item)
            "phone" -> _selectedPhone.value = toggleList(_selectedPhone.value, item)
            "coke" -> _selectedCoke.value = toggleList(_selectedCoke.value, item)
        }
    }

    private fun toggleList(list: List<String>, item: String): List<String> {
        return if (list.contains(item)) {
            list - item
        } else {
            list + item
        }
    }


}