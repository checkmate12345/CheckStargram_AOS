package com.checkmate.checkstagram.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(

): ViewModel() {
    val description = MutableStateFlow("")

    val isCheckAI = MutableStateFlow(false)
}