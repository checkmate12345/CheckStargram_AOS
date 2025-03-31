package com.checkmate.checkstagram.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.checkmate.checkstagram.databinding.ItemFeedImageBinding
import com.checkmate.checkstagram.databinding.ItemFeedVideoBinding
import com.checkmate.checkstagram.domain.model.MediaInfo

class FeedMediaAdapter : ListAdapter<MediaInfo, RecyclerView.ViewHolder>(diffUtil){

    override fun getItemViewType(position: Int): Int =
        when(getItem((position)).mediaType) {
            "video" -> TYPE_VIDEO
            else -> TYPE_IMAGE
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_IMAGE) {
            val binding = ItemFeedImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ImageViewHolder(binding)
        } else {
            val binding = ItemFeedVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            VideoViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val media = getItem(position)
        when (holder) {
            is ImageViewHolder -> holder.bind(media)
            is VideoViewHolder -> holder.bind(media)
        }
    }

    inner class ImageViewHolder(private val binding: ItemFeedImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(media: MediaInfo) {
            Glide.with(binding.root)
                .load(media.mediaUrl)
                .into(binding.itemFeedImage)
        }
    }

    inner class VideoViewHolder(private val binding: ItemFeedVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var player: ExoPlayer? = null

        fun bind(media: MediaInfo) {
            releasePlayer()

            val context = binding.root.context
            player = ExoPlayer.Builder(context).build().also { exoPlayer ->
                binding.itemFeedVideo.player = exoPlayer

                val mediaItem = MediaItem.fromUri(media.mediaUrl)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
                exoPlayer.volume = 0f
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
            }
        }

        private fun releasePlayer() {
            player?.release()
            player = null
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<MediaInfo>() {
            override fun areItemsTheSame(oldItem: MediaInfo, newItem: MediaInfo): Boolean =
                oldItem.mediaUrl == newItem.mediaUrl


            override fun areContentsTheSame(oldItem: MediaInfo, newItem: MediaInfo): Boolean =
                oldItem == newItem
        }
        private const val TYPE_IMAGE = 0
        private const val TYPE_VIDEO = 1
    }
}
