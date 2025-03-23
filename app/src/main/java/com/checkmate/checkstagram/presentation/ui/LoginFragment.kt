package com.checkmate.checkstagram.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.checkmate.checkstagram.databinding.FragmentLoginBinding
import com.checkmate.checkstagram.presentation.viewmodel.LoginViewModel
import com.checkmate.checkstagram.util.repeatOnStarted
import com.greener.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {
    private val viewModel : LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
    }

    override fun initListener() {
        binding.btnLogin.setOnClickListener{
            viewModel.login()
        }
    }

    override fun initCollector() {
        repeatOnStarted(viewLifecycleOwner) {
            viewModel.isLogin.collectLatest { isLogin ->
                if (isLogin) {
                    moveToFeed()
                }
            }
        }
    }

    private fun moveToFeed() {
        val action = LoginFragmentDirections.actionLoginFragmentToFeedFragment()
        findNavController().navigate(action)
    }
}