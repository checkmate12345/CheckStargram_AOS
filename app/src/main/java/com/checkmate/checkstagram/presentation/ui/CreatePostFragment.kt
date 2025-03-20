package com.checkmate.checkstagram.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.checkmate.checkstagram.databinding.FragmentCreatePostBinding
import com.checkmate.checkstagram.presentation.adapter.CreatePostMediaAdapter
import com.checkmate.checkstagram.presentation.viewmodel.CreatePostViewModel
import com.checkmate.checkstagram.util.repeatOnStarted
import com.greener.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CreatePostFragment: BaseFragment<FragmentCreatePostBinding> (
    FragmentCreatePostBinding::inflate
) {
    private val navArgs : CreatePostFragmentArgs by navArgs()
    private val viewModel: CreatePostViewModel by viewModels()

    private val createPostMediaAdapter = CreatePostMediaAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initRV()
    }

    override fun initListener() {
        binding.tbCp.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initCollector() {
    }

    private fun initRV() {
        binding.rvCpMedia.adapter = createPostMediaAdapter
        createPostMediaAdapter.submitList(navArgs.selectedMediaList.toList())
    }
}