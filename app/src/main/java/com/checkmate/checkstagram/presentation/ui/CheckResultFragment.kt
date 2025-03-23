package com.checkmate.checkstagram.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.checkmate.checkstagram.databinding.FragmentCheckResultBinding
import com.checkmate.checkstagram.presentation.adapter.CensuredSentenceAdapter
import com.checkmate.checkstagram.presentation.adapter.CheckPostMediaAdapter
import com.checkmate.checkstagram.presentation.adapter.CreatePostMediaAdapter
import com.checkmate.checkstagram.presentation.viewmodel.CheckResultViewModel
import com.greener.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckResultFragment: BaseFragment<FragmentCheckResultBinding> (
    FragmentCheckResultBinding::inflate
) {
    private val navArgs : CheckResultFragmentArgs by navArgs()
    private val viewModel : CheckResultViewModel by viewModels()

    private val createPostMediaAdapter = CreatePostMediaAdapter()
    private val checkVideoAdapter = CheckPostMediaAdapter(
        onImageClick = { _ ->  },
        onVideoClick = { mediaList ->
            val action = CheckResultFragmentDirections.actionCheckResultFragmentToCheckVideoDetailFragment(mediaList)
            findNavController().navigate(action)
        }
    )
    private val checkImageAdapter = CheckPostMediaAdapter(
        onImageClick = { media ->
            val action = CheckResultFragmentDirections.actionCheckResultFragmentToCheckImageDetailFragment(media)
            findNavController().navigate(action)
        },
        onVideoClick = { _-> }
    )
    private val censuredSentenceAdapter = CensuredSentenceAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRV()
        binding.tvCrText.text = navArgs.description
    }

    private fun initRV() {
        binding.rvCrMedia.adapter = createPostMediaAdapter
        createPostMediaAdapter.submitList(navArgs.MediaInfoList?.toList() ?: emptyList())

        val checkResult = navArgs.CheckResult
        val censuredSentences = checkResult?.censuredSentences ?: emptyList()
        val videoList = checkResult?.results?.filter { it.mediaType == "video" } ?: emptyList()
        val imageList = checkResult?.results?.filter { it.mediaType == "image" } ?: emptyList()

        if (videoList.isEmpty()) {
            binding.cvCrVideo.isGone = true
            binding.rvCrVideo.isGone = true
        }

        if (imageList.isEmpty()) {
            binding.cvCrImage.isGone = true
            binding.rvCrImage.isGone = true
        }

        binding.rvCrVideo.adapter = checkVideoAdapter
        checkVideoAdapter.submitList(videoList)

        binding.rvCrImage.adapter = checkImageAdapter
        checkImageAdapter.submitList(imageList)

        binding.rvCrDescription.adapter = censuredSentenceAdapter
        censuredSentenceAdapter.submitList(censuredSentences)
    }

}