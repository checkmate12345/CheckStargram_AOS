package com.checkmate.checkstagram.presentation.adapter

import android.net.Uri
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

class CreatePostMediaAdapter :
    ListAdapter<MediaInfo, CreatePostMediaAdapter.ViewHolder>(diffUtil) {

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

        fun bind(item: MediaInfo) {
            if (item.mediaType == "image") {
                // 이미지 표시
                binding.itemIvCreatePost.visibility = View.VISIBLE
                binding.itemPvCreatePost.visibility = View.GONE

                Glide.with(binding.root.context)
                    .load(Uri.parse(item.mediaUrl))
                    .into(binding.itemIvCreatePost)

            } else if (item.mediaType == "video") {
                // 동영상 표시
                binding.itemIvCreatePost.visibility = View.GONE
                binding.itemPvCreatePost.visibility = View.VISIBLE

                player?.release()
                player = ExoPlayer.Builder(binding.root.context).build().apply {
                    setMediaItem(MediaItem.fromUri(Uri.parse(item.mediaUrl)))
                    prepare()
                    playWhenReady = true
                    repeatMode = ExoPlayer.REPEAT_MODE_ONE
                    volume = 0f
                }

                binding.itemPvCreatePost.player = player
            }
        }

        fun releasePlayer() {
            player?.release()
            player = null
            binding.itemPvCreatePost.player = null
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<MediaInfo>() {
            override fun areItemsTheSame(oldItem: MediaInfo, newItem: MediaInfo): Boolean =
                oldItem.mediaUrl == newItem.mediaUrl

            override fun areContentsTheSame(oldItem: MediaInfo, newItem: MediaInfo): Boolean =
                oldItem == newItem
        }
    }
}
