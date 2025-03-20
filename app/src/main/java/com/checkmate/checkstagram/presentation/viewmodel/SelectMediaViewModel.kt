package com.checkmate.checkstagram.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.checkmate.checkstagram.domain.usecase.PickImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectMediaViewModel @Inject constructor(
    val pickImageUseCase: PickImageUseCase
): ViewModel() {

    private val _mediaList = MutableStateFlow<List<Uri>>(emptyList())
    val mediaList : StateFlow<List<Uri>> get() = _mediaList

    private val _selectedMediaList = MutableStateFlow<List<Uri>>(emptyList())
    val selectedMediaList : StateFlow<List<Uri>> get() = _selectedMediaList

    private val _preview = MutableStateFlow<Uri?>(null)
    val preview : StateFlow<Uri?> get() = _preview

    fun loadMedia(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val mediaUris = pickImageUseCase(context)
            Log.d("jomi", mediaUris.toString())
            _mediaList.value = mediaUris
            setPreview()
        }
    }


    fun toggleSelection(uri: Uri) {
        val currentList = _selectedMediaList.value.toMutableList()

        if (currentList.contains(uri)) {
            currentList.remove(uri)
        } else {
            if (currentList.size < 10) { // 최대 10개 선택 가능
                currentList.add(uri)
            }
        }
        _selectedMediaList.value = currentList
        setPreview()
    }

    private fun setPreview() {
        viewModelScope.launch {
            val all = mediaList.value
            val list = selectedMediaList.value

            if (list.isEmpty()) {
                _preview.emit(all.first())
            } else {
                _preview.emit(list.last())
            }
        }
    }
}