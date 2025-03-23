package com.checkmate.checkstagram.presentation.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.checkmate.checkstagram.databinding.ItemCpMediaBinding
import com.checkmate.checkstagram.databinding.ItemCvLogBinding
import com.checkmate.checkstagram.domain.model.MediaInfo
import com.checkmate.checkstagram.presentation.model.CheckMediaResult
import com.checkmate.checkstagram.presentation.model.TimeLineLabel
import com.checkmate.checkstagram.presentation.model.Timeline

class VideoLogAdapter : ListAdapter<TimeLineLabel, VideoLogAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCvLogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemCvLogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TimeLineLabel) {
            val start = formatTime(item.start)
            val end = formatTime(item.end)

            val timeText = if (item.start == item.end) {
                start
            } else {
                "$start ~ $end"
            }

            binding.itemTvTime.text = timeText
            binding.itemTvLog.text = item.label
        }

        private fun formatTime(seconds: Double): String {
            val totalSeconds = seconds.toInt()
            val minutes = totalSeconds / 60
            val secs = totalSeconds % 60
            return String.format("%02d:%02d", minutes, secs)
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<TimeLineLabel>() {
            override fun areItemsTheSame(oldItem: TimeLineLabel, newItem: TimeLineLabel): Boolean =
                oldItem === newItem

            override fun areContentsTheSame(oldItem: TimeLineLabel, newItem: TimeLineLabel): Boolean =
                oldItem == newItem
        }
    }
}
