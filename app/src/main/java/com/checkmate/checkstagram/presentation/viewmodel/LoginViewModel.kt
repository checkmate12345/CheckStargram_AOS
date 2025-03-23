package com.checkmate.checkstagram.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.checkmate.checkstagram.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginUseCase: LoginUseCase
): ViewModel() {
    val userName = MutableStateFlow("")
    val password = MutableStateFlow("")

    private val _isLogin = MutableStateFlow<Boolean>(false)
    val isLogin : StateFlow<Boolean> get() = _isLogin

    fun login() {
        viewModelScope.launch {
            val name = userName.value
            val pw = password.value
            val result = loginUseCase(name, pw)
            if (result.isSuccess) {
                _isLogin.emit(true)
            } else {
                Log.d("JOMI", "로그인 실페 : ${result.exceptionOrNull()}")
                _isLogin.emit(false)
            }
        }
    }
}