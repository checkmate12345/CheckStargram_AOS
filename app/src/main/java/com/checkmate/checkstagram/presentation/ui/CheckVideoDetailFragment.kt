package com.checkmate.checkstagram.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.checkmate.checkstagram.data.model.request.TimelineDto
import com.checkmate.checkstagram.databinding.FragmentCheckVideoDetailBinding
import com.checkmate.checkstagram.presentation.adapter.VideoLogAdapter
import com.checkmate.checkstagram.presentation.model.TimeLineLabel
import com.checkmate.checkstagram.presentation.model.Timeline
import com.greener.presentation.ui.base.BaseFragment

class CheckVideoDetailFragment: BaseFragment<FragmentCheckVideoDetailBinding> (
    FragmentCheckVideoDetailBinding::inflate
) {

    private val navArgs : CheckVideoDetailFragmentArgs by navArgs()
    private val videoLogAdapter = VideoLogAdapter()
    private var player: ExoPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result = navArgs.CheckMediaResult

        if (result != null) {
            releasePlayer()
            player = ExoPlayer.Builder(requireContext()).build().also { exoPlayer ->
                binding.pvCv.player = exoPlayer

                val mediaItem = MediaItem.fromUri(result.resultUrl)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
                exoPlayer.volume = 0f
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
            }

            binding.tbCv.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            val logList : List<TimeLineLabel> = result.timeline.toTimelineLabelList()

            binding.rvCi.adapter = videoLogAdapter
            videoLogAdapter.submitList(logList)

        }
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }

    fun Map<String, List<Timeline>>.toTimelineLabelList(): List<TimeLineLabel> {
        return this.flatMap { (label, timeList) ->
            timeList.map { timelineDto ->
                TimeLineLabel(
                    label = label,
                    start = timelineDto.start,
                    end = timelineDto.end
                )
            }
        }
    }
}