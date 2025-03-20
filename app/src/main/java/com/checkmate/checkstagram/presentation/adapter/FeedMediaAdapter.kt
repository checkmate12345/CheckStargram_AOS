package com.checkmate.checkstagram.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.checkmate.checkstagram.databinding.ItemFeedImageBinding
import com.checkmate.checkstagram.domain.model.MediaInfo

class FeedMediaAdapter : ListAdapter<MediaInfo, FeedMediaAdapter.ViewHolder>(diffUtil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedMediaAdapter.ViewHolder =
        ViewHolder(ItemFeedImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: FeedMediaAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemFeedImageBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MediaInfo){
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<MediaInfo>() {
            override fun areItemsTheSame(oldItem: MediaInfo, newItem: MediaInfo): Boolean =
                oldItem === newItem


            override fun areContentsTheSame(oldItem: MediaInfo, newItem: MediaInfo): Boolean =
                oldItem == newItem
        }
    }
}
