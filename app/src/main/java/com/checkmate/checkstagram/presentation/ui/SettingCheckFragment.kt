package com.checkmate.checkstagram.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.checkmate.checkstagram.databinding.FragmentSettingCheckBinding
import com.checkmate.checkstagram.presentation.viewmodel.SettingCheckViewModel
import com.greener.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingCheckFragment : BaseFragment<FragmentSettingCheckBinding> (
    FragmentSettingCheckBinding::inflate
){
    private val viewmodel : SettingCheckViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewmodel
    }
}