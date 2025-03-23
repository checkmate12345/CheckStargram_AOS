package com.checkmate.checkstagram.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.checkmate.checkstagram.domain.model.MediaInfo
import com.checkmate.checkstagram.domain.usecase.PickMediaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectMediaViewModel @Inject constructor(
    val pickMediaUseCase: PickMediaUseCase
): ViewModel() {

    private val _mediaList = MutableStateFlow<List<MediaInfo>>(emptyList())
    val mediaList : StateFlow<List<MediaInfo>> get() = _mediaList

    private val _selectedMediaList = MutableStateFlow<List<MediaInfo>>(emptyList())
    val selectedMediaList : StateFlow<List<MediaInfo>> get() = _selectedMediaList

    private val _preview = MutableStateFlow<MediaInfo?>(null)
    val preview : StateFlow<MediaInfo?> get() = _preview

    fun loadMedia(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val media = pickMediaUseCase(context)
            Log.d("jomi","media : $media")
            _mediaList.value = media
            setPreview()
        }
    }


    fun toggleSelection(mediaInfo: MediaInfo) {
        val currentList = _selectedMediaList.value.toMutableList()

        if (currentList.contains(mediaInfo)) {
            currentList.remove(mediaInfo)
        } else {
            if (currentList.size < 10) {
                currentList.add(mediaInfo)
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
                _preview.emit(all.firstOrNull())
            } else {
                _preview.emit(list.lastOrNull())
            }
        }
    }
}