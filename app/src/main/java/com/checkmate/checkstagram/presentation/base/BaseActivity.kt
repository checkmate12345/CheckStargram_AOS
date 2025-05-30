package com.checkmate.checkstagram.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding>(
    private val bindingFactory: (LayoutInflater) -> VB
): AppCompatActivity() {

    protected  lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        initListener()
        initCollector()
    }

    protected open fun initListener() {
    }

    protected open fun initCollector() {
    }
}