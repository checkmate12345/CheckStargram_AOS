package com.checkmate.checkstagram.presentation.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.checkmate.checkstagram.R
import com.checkmate.checkstagram.databinding.ItemPickMediaBinding

class PickMediaAdapter(
    private val onItemSelected: (Uri) -> Unit
): ListAdapter<Uri, PickMediaAdapter.ViewHolder>(diffUtil) {

    private val selectedItems = mutableListOf<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickMediaAdapter.ViewHolder =
        ViewHolder(ItemPickMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemPickMediaBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind (uri: Uri) {
            Glide.with(binding.root)
                .load(uri)
                .placeholder(R.drawable.tag_icon)
                .into(binding.itemIvFeedImage)

            val index = selectedItems.indexOf(uri)

            if (index != -1) {
                binding.itemTvFeedIndex.text = (index + 1).toString() // 1부터 시작
                binding.itemTvFeedIndex.isSelected = true // ✅ 선택되었을 때 파란색 배경
            } else {
                binding.itemTvFeedIndex.text = "" // 선택되지 않으면 텍스트 제거
                binding.itemTvFeedIndex.isSelected = false // ✅ 선택되지 않았을 때 반투명 배경
            }

            // ✅ 이미지 클릭 시 선택/해제 로직 추가
            binding.itemIvFeedImage.setOnClickListener {
                if (selectedItems.contains(uri)) {
                    selectedItems.remove(uri)
                } else {
                    selectedItems.add(uri)
                }
                notifyDataSetChanged() // ✅ RecyclerView 갱신
                onItemSelected(uri) // ✅ 콜백 호출 (선택된 리스트 업데이트)
            }
        }

    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Uri>() {
            override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean =
                oldItem === newItem

            override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean =
                oldItem == newItem

        }
    }
}