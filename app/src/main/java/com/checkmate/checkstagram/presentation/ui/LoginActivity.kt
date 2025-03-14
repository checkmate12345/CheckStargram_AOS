package com.checkmate.checkstagram.presentation.ui

import android.os.Bundle
import com.checkmate.checkstagram.databinding.ActivityLoginBinding
import com.checkmate.checkstagram.presentation.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(
    ActivityLoginBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}