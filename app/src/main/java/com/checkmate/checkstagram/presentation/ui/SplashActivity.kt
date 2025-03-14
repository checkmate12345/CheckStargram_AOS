package com.checkmate.checkstagram.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.checkmate.checkstagram.databinding.ActivitySplashBinding
import com.checkmate.checkstagram.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity: BaseActivity<ActivitySplashBinding>(
    ActivitySplashBinding::inflate
){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            moveToLoginActivity()
        }, 500) // 300ms
    }

    private fun moveToLoginActivity() {
        val intent = Intent(this, LoginFragment::class.java)
        startActivity(intent)
        finish()
    }
}