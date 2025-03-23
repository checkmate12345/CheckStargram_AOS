package com.checkmate.checkstagram.presentation.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.checkmate.checkstagram.R
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

    private var loadingDialog: Dialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initRV()
        viewModel.initSelectedMediaList(navArgs.MediaInfoList?.toList() ?: emptyList())
    }

    override fun initListener() {
        binding.tbCp.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnCpNext.setOnClickListener {
            viewModel.checkFeed(
                onSuccess = {
                    findNavController().navigate(R.id.action_createPostFragment_to_feedFragment)
                },
                onFailure = {
                    Toast.makeText(requireContext(), "검사 실패: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }

    }

    override fun initCollector() {
        repeatOnStarted(viewLifecycleOwner) {
            viewModel.isLoading.collectLatest { isLoading ->
                if (isLoading) showLoading() else hideLoading()
            }
        }
    }

    private fun initRV() {
        binding.rvCpMedia.adapter = createPostMediaAdapter
        createPostMediaAdapter.submitList(navArgs.MediaInfoList?.toList() ?: emptyList())
    }

    private fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = Dialog(requireContext()).apply {
                setContentView(R.layout.dialog_loading_lottie)
                setCancelable(false)
                window?.setBackgroundDrawableResource(android.R.color.transparent)
            }
        }
        loadingDialog?.show()
    }

    private fun hideLoading() {
        loadingDialog?.dismiss()
    }
}