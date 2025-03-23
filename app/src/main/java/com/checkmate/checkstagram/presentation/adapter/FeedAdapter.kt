package com.checkmate.checkstagram.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.checkmate.checkstagram.R
import com.checkmate.checkstagram.databinding.ItemFeedBinding
import com.checkmate.checkstagram.domain.model.FeedInfo

class FeedAdapter(): ListAdapter<FeedInfo, FeedAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedAdapter.ViewHolder =
        ViewHolder(ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: FeedAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemFeedBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FeedInfo) {

            Glide.with(binding.root)
                .load(item.profileImage)
                .placeholder(R.drawable.emotion_icon)
                .into(binding.ivItemFeedProfile)

            binding.tvItemFeedUser.text = item.username

            binding.feed = item

            val feedMediaAdapter = FeedMediaAdapter()
            feedMediaAdapter.submitList(item.medias)
            binding.vpItemFeed.adapter = feedMediaAdapter
            binding.diItemFeed.attachTo(binding.vpItemFeed)

            binding.tvItemFeedLikeCount.text = item.likes
            binding.tvItemFeedMessageCount.text = item.comments
            binding.tvItemFeedText.text = item.username + " " + item.description
            binding.tvItemFeedTime.text = item.createdAt
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<FeedInfo>() {
            override fun areItemsTheSame(oldItem: FeedInfo, newItem: FeedInfo): Boolean =
                oldItem.createdAt == newItem.createdAt


            override fun areContentsTheSame(oldItem: FeedInfo, newItem: FeedInfo): Boolean =
                oldItem == newItem
        }
    }
}