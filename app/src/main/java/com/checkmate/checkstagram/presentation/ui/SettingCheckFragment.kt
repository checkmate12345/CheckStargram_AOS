package com.checkmate.checkstagram.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.checkmate.checkstagram.R
import com.checkmate.checkstagram.databinding.FragmentSettingCheckBinding
import com.checkmate.checkstagram.presentation.viewmodel.SettingCheckViewModel
import com.checkmate.checkstagram.util.repeatOnStarted
import com.google.android.material.button.MaterialButton
import com.greener.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SettingCheckFragment : BaseFragment<FragmentSettingCheckBinding> (
    FragmentSettingCheckBinding::inflate
){
    private val viewmodel : SettingCheckViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewmodel
    }

    override fun initListener() {
        // 위험 요소 버튼 클릭 리스너
        binding.btnDanger1.setOnClickListener {
            toggleButton(it as View, "risk", getString(R.string.cigarette))
        }
        binding.btnDanger2.setOnClickListener {
            toggleButton(it as View, "risk", getString(R.string.alcohol))
        }

        // 브랜드 버튼 클릭 리스너
        binding.btnBrand1.setOnClickListener {
            toggleButton(it as View, "bag", getString(R.string.louisvuitton))
        }
        binding.btnBrand2.setOnClickListener {
            toggleButton(it as View, "bag", getString(R.string.chanel))
        }
        binding.btnBrand3.setOnClickListener {
            toggleButton(it as View, "bag", getString(R.string.hermes))
        }
        binding.btnBrand4.setOnClickListener {
            toggleButton(it as View, "bag", getString(R.string.gucci))
        }
        binding.btnBrand5.setOnClickListener {
            toggleButton(it as View, "bag", getString(R.string.dior))
        }

        // IT 제품 버튼 클릭 리스너
        binding.btnIt1.setOnClickListener {
            toggleButton(it as View, "phone", getString(R.string.apple))
        }
        binding.btnIt2.setOnClickListener {
            toggleButton(it as View, "phone", getString(R.string.samsung))
        }

        // 식음료 버튼 클릭 리스너
        binding.btnFood1.setOnClickListener {
            toggleButton(it as View, "coke", getString(R.string.cocacola))
        }
        binding.btnFood2.setOnClickListener {
            toggleButton(it as View, "coke", getString(R.string.pepsi))
        }

        binding.btnScSetting.setOnClickListener {
            viewmodel.saveCheckSetting()
        }
    }

    private fun toggleButton(view: View, category: String, value: String) {
        view.isSelected = !view.isSelected
        viewmodel.toggleItem(category, value)
    }
}