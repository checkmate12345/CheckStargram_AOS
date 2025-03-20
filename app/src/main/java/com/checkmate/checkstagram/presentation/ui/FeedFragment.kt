package com.checkmate.checkstagram.presentation.ui


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.checkmate.checkstagram.databinding.FragmentFeedBinding
import com.checkmate.checkstagram.presentation.adapter.FeedAdapter
import com.checkmate.checkstagram.presentation.viewmodel.FeedViewModel
import com.checkmate.checkstagram.util.repeatOnStarted
import com.greener.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FeedFragment: BaseFragment<FragmentFeedBinding> (
    FragmentFeedBinding::inflate
) {
    private val viewModel : FeedViewModel by viewModels()

    private val feedAdapter = FeedAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        initRV()
    }

    override fun initCollector() {
        repeatOnStarted(viewLifecycleOwner) {
            viewModel.feedInfo.collectLatest { info ->
                feedAdapter.submitList(info)
            }
        }
    }

    override fun onDestroyView() {
        binding.rvFeed.adapter = null
        super.onDestroyView()
    }

    private fun initRV() {
        binding.rvFeed.adapter = feedAdapter
    }
}