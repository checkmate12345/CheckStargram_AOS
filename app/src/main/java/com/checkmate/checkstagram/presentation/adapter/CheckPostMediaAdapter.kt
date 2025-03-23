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
import com.checkmate.checkstagram.domain.model.MediaInfo
import com.checkmate.checkstagram.presentation.model.CheckMediaResult

class CheckPostMediaAdapter(
    private val onImageClick: (CheckMediaResult) -> Unit,
    private val onVideoClick: (CheckMediaResult) -> Unit
) : ListAdapter<CheckMediaResult, CheckPostMediaAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemCpMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.releasePlayer()
        super.onViewRecycled(holder)
    }

    inner class ViewHolder(private val binding: ItemCpMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var player: ExoPlayer? = null

        fun bind(item: CheckMediaResult) {
            if (item.mediaType == "image") {
                // 이미지 표시
                binding.itemIvCreatePost.visibility = View.VISIBLE
                binding.itemPvCreatePost.visibility = View.GONE

                Glide.with(binding.root.context)
                    .load(Uri.parse(item.resultUrl))
                    .into(binding.itemIvCreatePost)

            } else if (item.mediaType == "video") {
                // 동영상 표시
                binding.itemIvCreatePost.visibility = View.GONE
                binding.itemPvCreatePost.visibility = View.VISIBLE

                player?.release()
                player = ExoPlayer.Builder(binding.root.context).build().apply {
                    setMediaItem(MediaItem.fromUri(Uri.parse(item.resultUrl)))
                    prepare()
                    playWhenReady = true
                    repeatMode = ExoPlayer.REPEAT_MODE_ONE
                    volume = 0f
                }

                binding.itemPvCreatePost.player = player
            }


            binding.root.setOnClickListener {
                if (item.mediaType == "image") {
                    ( onImageClick(currentList[absoluteAdapterPosition]))
                } else {
                    onVideoClick(currentList[absoluteAdapterPosition])
                }
            }
        }

        fun releasePlayer() {
            player?.release()
            player = null
            binding.itemPvCreatePost.player = null
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<CheckMediaResult>() {
            override fun areItemsTheSame(oldItem: CheckMediaResult, newItem: CheckMediaResult): Boolean =
                oldItem.resultUrl == newItem.resultUrl

            override fun areContentsTheSame(oldItem: CheckMediaResult, newItem: CheckMediaResult): Boolean =
                oldItem == newItem
        }
    }
}
