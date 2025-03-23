package com.checkmate.checkstagram.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.checkmate.checkstagram.R
import com.checkmate.checkstagram.databinding.ItemCpMediaBinding
import com.checkmate.checkstagram.presentation.model.SelectedMediaInfo

class CreatePostMediaAdapter : ListAdapter<SelectedMediaInfo, CreatePostMediaAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemCpMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding : ItemCpMediaBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SelectedMediaInfo){
            Glide.with(binding.root)
                .load(item.uri)
                .placeholder(R.drawable.tag_icon)
                .into(binding.itemIvCreatePost)
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<SelectedMediaInfo>() {
            override fun areItemsTheSame(
                oldItem: SelectedMediaInfo,
                newItem: SelectedMediaInfo
            ): Boolean = oldItem === newItem

            override fun areContentsTheSame(
                oldItem: SelectedMediaInfo,
                newItem: SelectedMediaInfo
            ): Boolean = oldItem == newItem
        }
    }
}