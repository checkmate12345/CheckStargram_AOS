package com.checkmate.checkstagram.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.checkmate.checkstagram.R
import com.checkmate.checkstagram.databinding.FragmentCheckImageDetailBinding
import com.greener.presentation.ui.base.BaseFragment

class CheckImageDetailFragment: BaseFragment<FragmentCheckImageDetailBinding> (
    FragmentCheckImageDetailBinding::inflate
) {

    private val navArgs : CheckImageDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result = navArgs.CheckMediaResult

        binding.tbCi.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        if (result != null) {
            Glide.with(requireContext())
                .load(result.resultUrl)
                .into(binding.ivCi)

            binding.itemTvCp.text = result.resultObject
        }
    }
}